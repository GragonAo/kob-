package com.kob.backend.assets.scripts;

import com.kob.backend.consumer.WebSocketServer;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Integer id; //玩家ID
    private  Integer r; //初始化玩家位置x
    private  Integer c; //初始化玩家位置y
    private List<Integer> opatorList;   //玩家操作记录
    private int[] dx = {1,0,-1,0},dy = {0,1,0,-1};  //移动方位
    private Integer nextStepOp = null;      //玩家下一步操作
    private  boolean isDie = false; //玩家是否死亡
    public Player(Integer id){
        this.id = id;
        opatorList = new ArrayList<>();
    }   //构造函数
    public void setPlayerPos(Cell cell){
        this.r = cell.r;
        this.c = cell.c;
    }   //设置玩家位置
    public void setIsDie(boolean die){
        isDie = die;
    }   //玩家是否死亡属性
    public boolean getIsDie(){return isDie;}    //玩家是否死亡属性
    public void setNextStepOp(Integer op){
        nextStepOp = op;
    }   //玩家下一步操作属性
    public Integer getNextStepOp(){return nextStepOp;}  //玩家下一步操作属性
    public Integer getId(){
        return id;
    }   //获取玩家ID
    public Cell getStartCell(){
        return new Cell(r,c);
    }   //获取玩家开始位置
    public void addOpatorList(Integer nextStep){
        opatorList.add(nextStep);
    }   //记录玩家操作信息
    public List<Cell> getCellList() {
        List<Cell> res = new ArrayList<>();
        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
        int x = r, y = c;
        int step = 0;
        res.add(new Cell(x, y));
        for (int d: opatorList) {
            x += dx[d];
            y += dy[d];
            res.add(new Cell(x, y));
            if (!checkTailIncreasing( ++ step)) {
                res.remove(0);
            }
        }
        return res;
    }   //获取玩家操作所有信息
    private boolean checkTailIncreasing(int step){
        if(step <= 10)return true;
        return step % 3 == 1;
    }   //检查长度变化
    public void sendMessage(String message){
        if(WebSocketServer.users.containsKey(id)){
            WebSocketServer.users.get(id).sendMessage(message);
        }
    }   //向客户端发送消息
}
