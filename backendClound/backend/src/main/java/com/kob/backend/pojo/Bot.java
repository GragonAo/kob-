package com.kob.backend.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class Bot {
    private Integer id;
    private Integer userId;
    private String title;
    private String description;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createtime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date modifytime;
}
