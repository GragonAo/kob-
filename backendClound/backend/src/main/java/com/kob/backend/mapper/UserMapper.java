package com.kob.backend.mapper;

import com.kob.backend.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);
    @Insert("insert into user(username,password,photo) values(#{username},#{pwd},#{photo})")
    void register(@Param("username")String username,@Param("pwd")String pwd,@Param("photo")String photo);
    @Update("update user set photo=#{photo} where id=#{id}")
    void updatePhoto(@Param("id") Integer id, @Param("photo") String photo);


    @Select("select  * from user where id=#{userId}")
    User selectById(Integer userId);
    @Update("UPDATE user SET rating = rating + #{score} WHERE id = #{userId}")
    void updateRating(@Param("userId") Integer userId, @Param("score") Integer score);

}
