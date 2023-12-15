package com.kob.backend.service.impl;

import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.UserService;
import com.kob.backend.utils.Md5Util;
import com.kob.backend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
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
        String photo = "/uploads/init.png";
        pwd = Md5Util.getMD5String(pwd);
        userMapper.register(username,pwd,photo);
    }

    @Override
    public String updatePhoto(MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        Map<String,Object> data = ThreadLocalUtil.get();
        Integer id = (Integer)data.get("id");
        String url = "";
        try {
            // 文件保存的路径
            String path = "./uploads/";
            File directory = new File(path);
            // 如果目录不存在，创建它
            if (!directory.exists()) {
                directory.mkdirs();
            }
            path = directory.getAbsolutePath();
            String ext = Md5Util.getFileExtension(file.getOriginalFilename());
            String md5Dest = "/"+Md5Util.getMD5String(file.getBytes())+ext;
            url = "/uploads"+md5Dest;
            File dest = new File(path+md5Dest);
            if(!dest.exists()){
                // 将文件保存到服务器
                file.transferTo(dest);
            }
            // 获取文件的URL
            userMapper.updatePhoto(id,url);
        } catch (Exception e) {
            // 处理异常
            System.err.println(e.getMessage());
        }
        return url;
    }
}
