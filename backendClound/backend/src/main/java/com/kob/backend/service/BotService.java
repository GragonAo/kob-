package com.kob.backend.service;

import com.kob.backend.pojo.Bot;

import java.util.List;

public interface BotService {
    List<Bot> getBotList(Integer id);

    Integer addBot(Bot bot);

    Integer updateBot(Bot bot);

    Bot getBot(Integer id);
}
