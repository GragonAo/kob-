package com.kob.backend.assets.scripts;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.mapper.BattleRecordMapper;
import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.BattleRecord;
import com.kob.backend.service.BattleRecordService;
import com.kob.backend.service.impl.BattleRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends Thread {
    private enum STATUS{//游戏状态
        NULL,//空
        PLYING,//游戏中
        FINISHED,//结束
    }   //游戏状态
    private enum RESULT{
        ALL,//所有
        WIN,//胜利
        LOSE,//失败
    }   //游戏结果
    private String gameId;  //游戏ID
    private Integer step;   //当前游戏进度
    private GameMap gameMap;    //游戏地图
    private Date createTime;    //游戏创建时间
    private STATUS status = STATUS.NULL;    //游戏状态
    private Integer gameCountDown = 10; //游戏倒计时
    final private static ReentrantLock lock = new ReentrantLock();  //锁
    private Integer r = 13,c=14,inerWallsCount = 20;    //地图参数
    private Cell[] playersPosArray = new Cell[]{    //玩家初始化位置信息
            new Cell(r-2, 1),
            new Cell(1, c-2)
    };
    private BattleRecordMapper battleRecordMapper;
    //游戏内所有玩家
    private Map<Integer,Player> players = new HashMap<>();
    public Game(List<Integer>playerIdList){
        for (Integer playerId: playerIdList ){
            players.put(playerId,new Player(playerId));
        }
        this.createTime = new Date();
        this.step = 0;
        this.gameId = createTime.hashCode()+"";
        this.gameMap = new GameMap(r,c,inerWallsCount);
        gameMap.createMap();
        for(int i = 0;i<players.size();i++){
            players.get(playerIdList.get(i)).setPlayerPos(playersPosArray[i]);
        }
    }   //构造函数

    public String getGameId(){
        return gameId;
    }   //获取游戏Id

    public void setNextStep(Integer playerId,Integer op){
        lock.lock();
        System.out.println(playerId+"的操作是："+op);
        for (Player player: players.values()){
            if(player.getId() == playerId){
                player.setNextStepOp(op);
                break;
            }
        }
        lock.unlock();
    }   //设置玩家输入

    private boolean nextStep(){
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(int i = 0;i<gameCountDown*10;i++)
        {
            try {
                Thread.sleep(100);
                lock.lock();
                try{
                    boolean flag = true;
                    for (Player player:players.values()) {
                        if(player.getNextStepOp()==null){
                            flag = false;break;
                        }
                    }
                    if(flag){
                        for (Player player:players.values()) {
                            player.addOpatorList(player.getNextStepOp());
                        }
                        return true;
                    }
                }finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i%gameCountDown == 0){
                sendCountDown(gameCountDown-(i/gameCountDown));
            }
        }
        return false;
    }   //下一步操作计算

    private boolean checkValid(Integer playerId){
        List<Cell> cellList = players.get(playerId).getCellList();
        int n = cellList.size();
        Cell cell = cellList.get(n-1);
        if(cell.r>=r || cell.c>=c || cell.r <0 || cell.r <0 || gameMap.getMap()[cell.r][cell.c] == 1)return false;
        for(int i =0;i<n-1;i++){
            if(cellList.get(i).r == cell.r && cellList.get(i).c == cell.c)return false;
        }
        for(Player player:players.values()){
            if(player.getId() == playerId)continue;
            List<Cell> tempCell = player.getCellList();
            for(int i =0;i<tempCell.size()-1;i++){
                if(tempCell.get(i).r == cell.r && tempCell.get(i).c == cell.c)return false;
            }
        }
        return true;
    }   //检查玩家是否有碰到障碍物或自己

    private void judge(){
        for(Integer playerId : players.keySet()){
            if(!checkValid(playerId)){
                players.get(playerId).setIsDie(true);
                status = STATUS.FINISHED;
            }
        }
    }   //判断是否所以玩家存活状态

    private void sendCountDown(Integer time){
        JSONObject resp = new JSONObject();
        resp.put("event","game-countdown");
        JSONObject data = new JSONObject();
        data.put("time",time);
        resp.put("data",data.toJSONString());
        sendAllMessage(resp.toJSONString());
    }   //发送倒计时


    private void sendMove(){
        JSONObject resp = new JSONObject();
        resp.put("event","game-move");
        JSONObject data = new JSONObject();
        List<JSONObject>movelist = new ArrayList<>();
        for(Player player:players.values()){
            JSONObject json = new JSONObject();
            json.put("id",player.getId());
            json.put("op",player.getNextStepOp());
            movelist.add(json);
        }
        data.put("dir_list",movelist);
        for(Player player:players.values()){
            player.setNextStepOp(null);
        }
        resp.put("data",data.toJSONString());
        sendAllMessage(resp.toJSONString());
        System.out.println("sendMove");
    }   //发送移动信息

    private void sendResult(){
        JSONObject resp = new JSONObject();
        resp.put("event","game-result");
        Integer res = -1;
        for(Player player:players.values()){
            JSONObject data = new JSONObject();
            if(playerDieCount() == players.size()){
                data.put("result",RESULT.ALL.toString());
            }else if(player.getIsDie()){
                data.put("result",RESULT.LOSE.toString());
            }else{
                data.put("result",RESULT.WIN.toString());
                res = player.getId();
            }
            resp.put("data",data.toJSONString());
            player.sendMessage(resp.toJSONString());
        }
        List<Integer>playerIdList = new ArrayList<>();
        for (Integer id : players.keySet()){
            playerIdList.add(id);
        }

        BattleRecordServiceImpl.updateResult(gameId,res,step);
        WebSocketServer.games.remove(gameId);
    }   //发送结束信息
    private void startGame(){
        JSONObject resp = new JSONObject();
        resp.put("event","start-game");
        JSONObject data = new JSONObject();
        data.put("game_id",gameId.toString());
        List<JSONObject> playerItems = new ArrayList<>();
        for(Player player:players.values()){
            JSONObject json = new JSONObject();
            json.put("id",player.getId());
            json.put("row",player.getStartCell().r);
            json.put("col",player.getStartCell().c);
            playerItems.add(json);
        }
        data.put("player_items",playerItems);
        data.put("game_map",gameMap.getMap());
        resp.put("data",data.toJSONString());
        sendAllMessage(resp.toJSONString());
    }   //开始游戏
    private Integer playerDieCount(){
        Integer count = 0;
        for (Player player:players.values()) {
            if(player.getIsDie())count++;
        }
        return count;
    }   //玩家存活数量
    //给所有玩家发送消息
    private void sendAllMessage(String resp){
        for(Player player:players.values())player.sendMessage(resp);
    }
    @Override
    public void run() {
        status = STATUS.PLYING;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        startGame();
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(int i = 0;i<1000;i++){
            step++;
            if(nextStep()){//用户的下一步操作
                judge();//判断用户输入合法性
                if(status == STATUS.PLYING){
                    sendMove();
                } else if(status == STATUS.FINISHED){
                    sendResult();
                    break;
                }
            }
            else {
                System.out.println(playerDieCount());
                if (playerDieCount() == 0) {
                    status = STATUS.FINISHED;
                    lock.lock();
                    try {
                        for(Player player:players.values()){
                            if(player.getNextStepOp() == null){
                                player.setIsDie(true);
                            }
                        }
                    } finally {
                        lock.unlock();
                    }
                    sendResult();
                    break;
                }
            }
        }
    }   //线程运行
}
