package com.kob.matchingSystem.service.impl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool extends Thread{
    private static List<Player> playerList = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private static RestTemplate restTemplate;
    private final static String startGameUrl = "http://127.0.0.1:3300/game/startGame";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        MatchingPool.restTemplate = restTemplate;
    }
    public void addPlayer(Integer userId,Integer rating){
        lock.lock();
        try{
            MatchingPool.playerList.add(new Player(userId,rating,0));
        }
        finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId){
        lock.lock();
        try{
            List<Player> temp = new ArrayList<>();
            for(Player player : MatchingPool.playerList){
                if(!player.getUserId().equals(userId)){
                    temp.add(player);
                }
            }
            MatchingPool.playerList = temp;
        }
        finally {
            lock.unlock();
        }
    }
    private boolean checkMatched(Player a, Player b){
        Integer differenceValue = Math.abs(a.getRating() - b.getRating());
        Integer waiting = Math.min(a.getWaitingTime(),b.getWaitingTime());
        return differenceValue <= waiting * 10;
    }
    private void sendResult(Player a,Player b){
        Map<String,String> resp = new HashMap<>();
        resp.put("a_id",a.getUserId().toString());
        resp.put("b_id",b.getUserId().toString());
        restTemplate.postForObject(startGameUrl,resp,String.class);
    }
    private void increaseWaitingTime(){
        for(Player player : playerList){
            player.setWaitingTime(player.getWaitingTime()+1);
        }
    }

    private void tryMatchPlayers(){
        System.out.println("matchPlaerys: "+playerList.toString());
        boolean[] used = new boolean[playerList.size()];
        for(int i = 0;i<used.length;i++){
            if(used[i] == true)continue;
            for(int j = i+1;j<used.length;j++){
                if(used[j] == true)continue;
                Player a = playerList.get(i),b = playerList.get(j);
                if(checkMatched(a,b)){
                    used[i] = used[j] = true;
                    sendResult(a,b);
                    break;
                }
            }
        }
        List<Player> temp = new ArrayList<>();
        for(int i = 0;i<used.length;i++){
            if(!used[i]){
                temp.add(playerList.get(i));
            }
        }
        playerList = temp;
    }


    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
                lock.lock();
                try{
                    increaseWaitingTime();
                    tryMatchPlayers();
                }
                finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
