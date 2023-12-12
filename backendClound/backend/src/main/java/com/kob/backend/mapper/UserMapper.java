package com.kob.backend.mapper;

import com.kob.backend.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);
    @Insert("insert into user(username,password,photo) values(#{username},#{pwd},#{photo})")
    void register(String username, String pwd,String photo);
    @Update("update user set photo=#{url} where id=#{id}")
    void updatePhoto(Integer id,String url);
    @Select("select  * from user where id=#{userId}")
    User selectById(Integer userId);
}
