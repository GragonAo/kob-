package com.kob.backend.mapper;

import com.kob.backend.pojo.Bot;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BotMapper {
    Bot selectById(Integer botId);
}
