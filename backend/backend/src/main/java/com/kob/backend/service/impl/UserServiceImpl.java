package com.kob.backend.service.impl;

import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.UserService;
import com.kob.backend.utils.Md5Util;
import com.kob.backend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }
    @Override
    public void register(String username, String pwd) {
        String photo = "https://cdn.acwing.com/media/user/profile/photo/197053_lg_7ce5da07df.webp";
        pwd = Md5Util.getMD5String(pwd);
        userMapper.register(username,pwd,photo);
    }

    @Override
    public void updatePhoto(String url) {
        Map<String,Object> data = ThreadLocalUtil.get();
        Integer id = (Integer)data.get("id");
        userMapper.updatePhoto(id,url);
    }
}
