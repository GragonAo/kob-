package com.kob.backend.config;

import com.kob.backend.interceptors.LoginInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {    //用于配置拦截器
    @Autowired
    private LoginInterceptors loginInterceptors;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //对登录和注册接口放行
        registry.addInterceptor(loginInterceptors)
                .excludePathPatterns("/uploads/**")//放行资源
                .excludePathPatterns("/user/login","/user/register","/game/startGame")//放行请求接口
                .excludePathPatterns("/websocket/**"); // 放行WebSocket;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:./uploads/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:*","https://www.xmut.shop","https://www.xmut.shop") // 允许的源模式
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // 允许的HTTP方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true) // 是否允许发送Cookie信息
                .maxAge(3600); // 预检请求的缓存时间（秒）
    }
}
