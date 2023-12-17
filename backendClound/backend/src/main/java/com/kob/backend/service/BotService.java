package com.kob.backend.service;

import com.kob.backend.pojo.Bot;

import java.util.List;

public interface BotService {
    List<Bot> getBotList(Integer id);

    Integer addBot(Bot bot);

    Integer updateBot(Bot bot,boolean isUpdateTime);

    Bot getBot(Integer id);

    Integer deleteBot(Integer id,Integer userId);
}
