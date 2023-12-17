package com.kob.backend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class RatingUserInfo {
    private String username;
    private Integer rating;
    private String photo;
}
