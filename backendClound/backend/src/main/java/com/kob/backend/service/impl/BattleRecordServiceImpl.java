package com.kob.backend.service.impl;

import com.kob.backend.mapper.BattleRecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.BattleRecord;
import com.kob.backend.service.BattleRecordService;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BattleRecordServiceImpl implements BattleRecordService {

    private static BattleRecordMapper battleRecordMapper;
    private static UserMapper userMapper;
    @Autowired
    private  void setUserMapper(UserMapper userMapper){
        BattleRecordServiceImpl.userMapper = userMapper;
    }
    @Autowired
    private void setBattleRecordMapper(BattleRecordMapper battleRecordMapper){
        BattleRecordServiceImpl.battleRecordMapper = battleRecordMapper;
    }
    @Override
    public BattleRecord getBattleRecord(Integer id) {
        return battleRecordMapper.getBattleRecord(id);
    }

    @Override
    public List<BattleRecord> getBattleRecordList(Integer id,Integer start,Integer length) {
        return battleRecordMapper.getBattleRecordList(id,start,length);
    }

    public boolean addBattleRecord(BattleRecord battleRecord){
        try {
            battleRecord.setCreatetime(LocalDateTime.now());

            Integer res = battleRecordMapper.addBattleRecord(battleRecord);
            if(res != 1)return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Integer getBattleRecordCount(Integer id) {
        return battleRecordMapper.getBattleRecordCount(id);
    }

    public static void updateResult(String gameId,Integer res,Integer step){
        try{
            BattleRecord battleRecord = battleRecordMapper.getBattleRecordByGameId(gameId);
            battleRecord.setResult(res);
            battleRecordMapper.updateResult(battleRecord);
            Integer score = 10+step/5;;
            if(res == -1){
                userMapper.updateRating(battleRecord.getUserId1(),0);
                userMapper.updateRating(battleRecord.getUserId2(),0);
            }else if(res == battleRecord.getUserId1()){
                userMapper.updateRating(battleRecord.getUserId1(),score);
                userMapper.updateRating(battleRecord.getUserId2(),-score);
            }else{
                userMapper.updateRating(battleRecord.getUserId1(),-score);
                userMapper.updateRating(battleRecord.getUserId2(),score);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
