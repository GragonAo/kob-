package com.kob.backend.mapper;

import com.kob.backend.pojo.BattleRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BattleRecordMapper {

    @Select("select * from battle_record where id=#{id}")
    BattleRecord getBattleRecord(Integer id);
    @Select("select * from battle_record where user_id_1=#{userId} or user_id_2=#{userId}")
    List<BattleRecord> getBattleRecordList(Integer userId);
    @Insert("insert  into battle_record(game_id,user_id_1,user_id_2,avg_rating,createtime)" +
            "values(#{gameId},#{userId1},#{userId2},#{avgRating},#{createtime})")
    Integer addBattleRecord(BattleRecord battleRecord);
    @Select("select * from battle_record where game_id=#{gameId}")
    BattleRecord getBattleRecordByGameId(String gameId);
    @Update("update battle_record set result=#{result} where id=#{id}")
    Integer updateResult(BattleRecord battleRecord);
}
