package com.kob.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.kob.backend.assets.scripts.Game;
import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.BattleRecord;
import com.kob.backend.pojo.Result;
import com.kob.backend.pojo.User;
import com.kob.backend.service.BattleRecordService;
import com.kob.backend.service.GameSerive;
import com.kob.backend.utils.ThreadLocalUtil;
import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameSerive gameSerive;
    @Autowired
    private BattleRecordService battleRecordService;
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
    @PostMapping("/startGame")
    public String startGame(@RequestBody Map<String,String> data){
        try{
            System.out.println("本局游戏玩家："+data);
            List<Integer> list = new ArrayList<>();
            for(String id : data.values()){
                Integer playerId = Integer.parseInt(id);
                if(WebSocketServer.users.get(playerId) != null){
                    list.add(playerId);
                }
            }
            BattleRecord battleRecord = gameSerive.startGame(list,data.size());
            battleRecordService.addBattleRecord(battleRecord);
            if(battleRecord == null){
                return "匹配中断";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "匹配中断";
        }
        return "匹配成功";
    }
}
