package com.kob.backend.mapper;

import com.kob.backend.pojo.RatingUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RatingMapper {
    @Select("select * from user order by rating desc ")
    List<RatingUserInfo> getRatingList();
}
