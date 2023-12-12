package com.kob.backend.controller;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.pojo.Result;
import com.kob.backend.utils.ThreadLocalUtil;
import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameController {
    @PostMapping("/setNextStep")
    public Result setNextStep(@RequestBody Map<String,String>data){
        System.out.println("SetNextStep "+data);
        try {
            String gameId = data.get("game_id");
            Map<String,Object> info = ThreadLocalUtil.get();
            Integer playerId = (Integer)info.get("id");
            Integer operate = Integer.parseInt(data.get("operate"));
            WebSocketServer.games.get(gameId).setNextStep(playerId,operate);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("数据格式错误");
        }
        return Result.success();
    }
}
