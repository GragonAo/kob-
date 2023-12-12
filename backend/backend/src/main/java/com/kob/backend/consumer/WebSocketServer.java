package com.kob.backend.consumer;
import com.kob.backend.assets.scripts.Game;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.utils.JwtUtil;
import com.sun.tools.jconsole.JConsoleContext;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {
    public final static ConcurrentMap<Integer,WebSocketServer> users = new ConcurrentHashMap<>();
    public final static ConcurrentMap<String, Game> games = new ConcurrentHashMap<>();
    private Session session;
    private Integer userId;
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        this.session = session;
        Map<String, Object> parseToken = JwtUtil.parseToken(token); //JWT会判断合法的UserID
        if(parseToken == null){
            session.close();
            return;
        }
        userId =(Integer)parseToken.get("id");
        System.out.println("建立连接");
        users.put(userId,this);
    }
    @OnClose
    public void onClose() {
        if(userId!=null){
            users.remove(userId);
        }
        // 关闭链接
        System.out.println("关闭连接");
    }
    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
    }
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
    public void sendMessage(String message){
        //给客户端发送消息
        synchronized (session){
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}