package com.kob.backend.interceptors;

import com.kob.backend.utils.JwtUtil;
import com.kob.backend.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptors implements HandlerInterceptor {  //登入拦截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取JWT令牌
        String token = request.getHeader("Authorization");
        //验证JWT令牌
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            System.out.println(claims);
            //存入ThreadLocal
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        }catch (Exception e){
            //将http请求响应状态码设置为401
            response.setStatus(401);
            //不放行
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //释放ThreadLocal数据
        ThreadLocalUtil.remove();
    }
}
