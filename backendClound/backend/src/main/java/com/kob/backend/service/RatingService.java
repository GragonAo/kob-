package com.kob.backend.service;

import com.kob.backend.pojo.RatingUserInfo;

import java.util.List;

public interface RatingService {
    List<RatingUserInfo> getRatingList();
}