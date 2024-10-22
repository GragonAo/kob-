package com.kob.backend.controller;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.request.Result;
import com.kob.backend.pojo.User;
import com.kob.backend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/matching")
public class MatchingController {
    public final static String matchingAddPlayerUrl="http://127.0.0.1:3301/matching/player/add/";
    public final static String matchingRemovePlayerUrl ="http://127.0.0.1:3301/matching/player/remove/";
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/player/add")
    public Result addPlayer(){
        try{
            Map<String,Object> data = ThreadLocalUtil.get();
            Integer id = (Integer) data.get("id");
            WebSocketServer socket = WebSocketServer.users.get(id);
            if(socket==null){
                return Result.error("未进行Socket连接");
            }
            Map<String,String> toData = new HashMap<>();
            toData.put("user_id",id.toString());
            User user = userMapper.selectById(id);
            toData.put("rating",user.getRating().toString());
            String res = WebSocketServer.sendHTTPMessage(matchingAddPlayerUrl,toData);
            System.out.println(res);
            if(!"success".equals(res)){
                return Result.error(res);
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("添加用户到匹配池失败");
        }
        return Result.success();
    }

    @GetMapping("/player/remove")
    public Result removePlayer(){
        try{
            Map<String,Object> data = ThreadLocalUtil.get();
            Integer id = (Integer) data.get("id");
            WebSocketServer socket = WebSocketServer.users.get(id);
            if(socket==null){
                return Result.error("未进行Socket连接");
            }
            Map<String,String> toData = new HashMap<>();
            toData.put("user_id",id.toString());
            String res = WebSocketServer.sendHTTPMessage(matchingRemovePlayerUrl,toData);
            if(!"success".equals(res)){
                return Result.error(res);
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("从匹配池移除用户失败");
        }
        return Result.success();
    }
}
