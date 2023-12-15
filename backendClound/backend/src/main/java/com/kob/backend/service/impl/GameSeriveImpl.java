package com.kob.backend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.assets.scripts.Game;
import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.controller.MatchingController;
import com.kob.backend.mapper.BattleRecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.BattleRecord;
import com.kob.backend.pojo.User;
import com.kob.backend.service.GameSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameSeriveImpl implements GameSerive {
    @Autowired
    private UserMapper userMapper;

    private BattleRecordMapper battleRecordMapper;

    @Override
    public BattleRecord startGame(List<Integer> list,Integer count)throws RuntimeException {
        if(list.size()!=count || count<=0){
            for(Integer id : list){
                User user = userMapper.selectById(id);
                Map<String,String> sendData = new HashMap<>();
                sendData.put("user_id",id.toString());
                sendData.put("rating",user.getRating().toString());
                WebSocketServer.sendHTTPMessage(MatchingController.matchingAddPlayerUrl,sendData);
            }
            return null;
        }
        BattleRecord battleRecord = new BattleRecord();
        JSONObject resp = new JSONObject();
        JSONObject info = new JSONObject();
        resp.put("event","users-in-game");
        List<JSONObject> itemList = new ArrayList<>();
        Integer num = 0,avgRating = 0;
        for(Integer id:list){
            User user = userMapper.selectById(id);
            JSONObject item = new JSONObject();
            item.put("photo",user.getPhoto());
            item.put("rating",user.getRating());
            item.put("username",user.getUsername());
            item.put("id",id);
            itemList.add(item);
            if(num == 0){
                battleRecord.setUserId1(user.getId());
            }
            else if(num ==1){
                battleRecord.setUserId2(user.getId());
            }
            avgRating+=user.getRating();
            num++;
        }
        info.put("user_list",itemList);
        resp.put("data",info.toJSONString());
        for (Integer id:list){
            WebSocketServer.users.get(id).sendMessage(resp.toJSONString());
        }
        Game game = new Game(list);
        game.start();
        avgRating/=2;
        battleRecord.setGameId(game.getGameId());
        battleRecord.setAvgRating(avgRating);
        WebSocketServer.games.put(game.getGameId(),game);
        return battleRecord;
    }
}
