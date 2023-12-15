package com.kob.backend.mapper;

import com.kob.backend.pojo.Bot;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BotMapper {
    Bot selectById(Integer botId);
    @Select("select * from bot where user_id=#{userId}")
    List<Bot> getBotList(Integer userId);

    @Insert("insert into bot(user_id,title,description,content,createtime,modifytime) " +
            "values(#{userId},#{title},#{description},#{content},#{createtime},#{modifytime})")
    Integer addBot(Bot bot);
    @Update("update bot set title=#{title},description=#{description},content=#{content},modifytime=#{modifytime} where id=#{id}")
    Integer updateBot(Bot bot);
    @Select("select * from bot where id=#{id}")
    Bot getBot(Integer id);
}
