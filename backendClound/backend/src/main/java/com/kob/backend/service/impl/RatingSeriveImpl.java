package com.kob.backend.service.impl;

import com.kob.backend.mapper.RatingMapper;
import com.kob.backend.pojo.RatingUserInfo;
import com.kob.backend.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingSeriveImpl implements RatingService {
    @Autowired
    RatingMapper ratingMapper;
    @Override
    public List<RatingUserInfo> getRatingList() {
        return ratingMapper.getRatingList();
    }
}
