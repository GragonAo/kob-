package com.kob.backend.controller;

import com.kob.backend.pojo.User;
import com.kob.backend.pojo.Result;
import com.kob.backend.service.UserService;
import com.kob.backend.utils.JwtUtil;
import com.kob.backend.utils.Md5Util;
import com.kob.backend.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result register(@RequestBody Map<String,String> data){
        String username =data.get("username");
        String pwd = data.get("pwd");
        String rePwd = data.get("re_pwd");
        if(username == null || pwd == null || rePwd == null){
            return Result.error("提交有误");
        }
        if(!pwd.equals(rePwd)){
            return Result.error("两次填写的新密码不一致");
        }
        String regex = "^\\S{5,16}$";
        if(!pwd.matches(regex)){
            return Result.error("密码不符合要求");
        }
        User user = userService.findByUserName(username);
        if(user!=null){
            return Result.error("用户名重复");
        }
        userService.register(username,pwd);
        return Result.success();
    }
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,String>data){
        String username =data.get("username");
        String password = data.get("password");
        if(username == null || password == null){
            return Result.error("提交有误");
        }
        String regex = "^\\S{5,16}$";
        if(!password.matches(regex)){
            return Result.error("密码不符合要求");
        }
        User loginUser = userService.findByUserName(username);
        if(loginUser == null){
            return Result.error("用户名不存在");
        }
        if(!Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            return Result.error("密码错误");
        }
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",loginUser.getId());
        claims.put("username",loginUser.getUsername());
        String token = JwtUtil.genToken(claims);
        return Result.success(token);
    }
    @GetMapping("/userInfo")
    public Result<User> userInfo(){
        //根据用户名查询用户信息
        Map<String,Object> data = ThreadLocalUtil.get();
        String username = (String) data.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);

    }
    @PatchMapping("/updatePhoto")
    public Result updatePhoto(@RequestBody Map<String, String> data){
        String url = data.get("url");
        // 确保url不为空且符合URL格式
        if (url != null && url.matches("^(http|https)://.*")) {
            userService.updatePhoto(url);
            return Result.success();
        } else {
            return Result.error("提供的URL无效");
        }
    }
}
