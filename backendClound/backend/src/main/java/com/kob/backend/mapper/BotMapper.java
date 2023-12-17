package com.kob.backend.mapper;

import com.kob.backend.pojo.Bot;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BotMapper {
    Bot selectById(Integer botId);
    @Select("select * from bot where user_id=#{userId} order by is_default desc ")
    List<Bot> getBotList(Integer userId);

    @Insert("insert into bot(user_id,title,description,content,createtime,modifytime,is_default) " +
            "values(#{userId},#{title},#{description},#{content},#{createtime},#{modifytime},#{isDefault})")
    Integer addBot(Bot bot);
    @Update("update bot set title=#{title},description=#{description},content=#{content},modifytime=#{modifytime},is_default=#{isDefault} where id=#{id}")
    Integer updateBot(Bot bot);
    @Select("select * from bot where id=#{id}")
    Bot getBot(Integer id);
    @Delete("delete from bot where id=#{id} and user_id=#{userId}")
    Integer deleteBot(@Param("id") Integer id,@Param("userId") Integer userId);
}
