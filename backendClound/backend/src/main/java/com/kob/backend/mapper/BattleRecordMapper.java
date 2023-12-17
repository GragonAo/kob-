package com.kob.backend.mapper;

import com.kob.backend.pojo.BattleRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BattleRecordMapper {

    @Select("select * from battle_record where id=#{id}")
    BattleRecord getBattleRecord(Integer id);
    @Select("SELECT * FROM battle_record WHERE user_id_1=#{userId} OR user_id_2=#{userId} ORDER BY createtime DESC LIMIT #{start}, #{length}")
    List<BattleRecord> getBattleRecordList(@Param("userId") Integer userId, @Param("start") Integer start, @Param("length") Integer length);


    @Insert("insert  into battle_record(game_id,user_id_1,user_id_2,avg_rating,createtime)" +
            "values(#{gameId},#{userId1},#{userId2},#{avgRating},#{createtime})")
    Integer addBattleRecord(BattleRecord battleRecord);
    @Select("select * from battle_record where game_id=#{gameId}")
    BattleRecord getBattleRecordByGameId(String gameId);
    @Update("update battle_record set result=#{result} where id=#{id}")
    Integer updateResult(BattleRecord battleRecord);
    @Select("select count(*) from battle_record where user_id_1=#{id} OR user_id_2=#{id}")
    Integer getBattleRecordCount(Integer id);
}
