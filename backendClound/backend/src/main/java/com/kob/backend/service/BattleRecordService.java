package com.kob.backend.service;

import com.kob.backend.pojo.BattleRecord;

import java.util.List;

public interface BattleRecordService {
    BattleRecord getBattleRecord(Integer id);

    List<BattleRecord> getBattleRecordList(Integer id);
    boolean addBattleRecord(BattleRecord battleRecord);
}
