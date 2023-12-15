package com.kob.backend.service;

import com.kob.backend.pojo.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User findByUserName(String username);

    void register(String username, String pwd);

    String updatePhoto(MultipartFile url);
}
