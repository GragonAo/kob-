package com.kob.backend.service;

import com.kob.backend.pojo.BattleRecord;

import java.util.List;

public interface GameSerive {

    BattleRecord startGame(List<Integer> list, Integer count);
}
