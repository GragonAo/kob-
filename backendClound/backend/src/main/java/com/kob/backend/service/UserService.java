package com.kob.backend.service;

import com.kob.backend.pojo.User;

public interface UserService {
    User findByUserName(String username);

    void register(String username, String pwd);

    void updatePhoto(String url);
}
