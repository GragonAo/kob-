package com.kob.backend.service.impl;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.service.BotService;
import com.kob.backend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class BotServiceImpl implements BotService {
    @Autowired
    private BotMapper botMapper;
    @Override
    public List<Bot> getBotList(Integer id) {
        return botMapper.getBotList(id);
    }

    @Override
    public Integer addBot(Bot bot) {
        Map<String,Object> data = ThreadLocalUtil.get();
        Integer id = (Integer) data.get("id");
        bot.setUserId(id);
        bot.setCreatetime(LocalDateTime.now());
        bot.setModifytime(LocalDateTime.now());
        return botMapper.addBot(bot);
    }

    @Override
    public Integer updateBot(Bot bot) {
        bot.setModifytime(LocalDateTime.now());
        return botMapper.updateBot(bot);
    }

    @Override
    public Bot getBot(Integer id) {
        return botMapper.getBot(id);
    }
}
