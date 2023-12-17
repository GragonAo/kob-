package com.kob.backend.controller;

import com.kob.backend.pojo.RatingUserInfo;
import com.kob.backend.request.Result;
import com.kob.backend.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    RatingService ratingService;
    @GetMapping("/")
    public Result<List<RatingUserInfo>> getRatingList(){
        return Result.success(ratingService.getRatingList());
    }
}
