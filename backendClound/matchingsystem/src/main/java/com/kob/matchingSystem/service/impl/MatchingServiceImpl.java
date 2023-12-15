package com.kob.matchingSystem.service.impl;

import com.kob.matchingSystem.service.MatchingService;
import com.kob.matchingSystem.service.impl.utils.MatchingPool;
import org.springframework.stereotype.Service;

@Service
public class MatchingServiceImpl implements MatchingService {
    public final static MatchingPool matchingPool = new MatchingPool();
    @Override
    public String addPlayer(Integer userId, Integer rating) {
        boolean res = matchingPool.addPlayer(userId,rating);
        return res? "success":"用户已经存在匹配池";
    }

    @Override
    public String removePlayer(Integer userId) {
        boolean res = matchingPool.removePlayer(userId);
        return res? "success":"用户从匹配移除出现异常";
    }
}
