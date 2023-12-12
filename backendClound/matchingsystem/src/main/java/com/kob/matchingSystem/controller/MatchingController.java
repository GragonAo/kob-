package com.kob.matchingSystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.kob.matchingSystem.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class MatchingController {
    @Autowired
    private MatchingService matchingService;

    @PostMapping("/matching/player/add/")
    public String addPlayer(@RequestBody Map<String,String> data){
        System.out.println(data );
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.get("user_id")));
        Integer rating = Integer.parseInt(Objects.requireNonNull(data.get("rating")));
        return matchingService.addPlayer(userId,rating);
    }
    @PostMapping("/matching/player/remove/")
    public String removePlayer(@RequestBody Map<String,String> data){
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.get("user_id")));
        return matchingService.removePlayer(userId);
    }
}
