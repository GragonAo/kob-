package com.kob.backend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class User {
    private Integer id;
    private String username;
    @JsonIgnore //让SpringMVC把当前对象转换为Json字符串时，自动屏蔽掉
    private String password;
    private Integer rating;
    private String photo;
}
