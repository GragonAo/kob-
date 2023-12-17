package com.kob.backend.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class BattleRecord {
    @JsonIgnore //让SpringMVC把当前对象转换为Json字符串时，自动屏蔽掉
    private Integer id;
    private String gameId;
    private Integer userId1;
    private  Integer userId2;
    private  Integer avgRating;
    private  Integer result;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime createtime;
}
