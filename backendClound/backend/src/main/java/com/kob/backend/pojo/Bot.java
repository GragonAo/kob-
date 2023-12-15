package com.kob.backend.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
@Data
public class Bot {
    @NotNull(groups = Update.class,message = "更新Bot请指定BotID")
    private Integer id;
    private Integer userId;
    @NotEmpty (message = "标题不能为空")
    @Size(max = 100,message = "标题长度必须小于100")
    private String title;
    private String description;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime createtime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime modifytime;
    public interface Update extends Default{}
}
