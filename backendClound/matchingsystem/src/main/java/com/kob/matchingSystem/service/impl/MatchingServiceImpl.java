package com.kob.matchingSystem.service.impl;

import com.kob.matchingSystem.service.MatchingService;
import com.kob.matchingSystem.service.impl.utils.MatchingPool;
import org.springframework.stereotype.Service;

@Service
public class MatchingServiceImpl implements MatchingService {
    public final static MatchingPool matchingPool = new MatchingPool();
    @Override
    public String addPlayer(Integer userId, Integer rating) {
        matchingPool.addPlayer(userId,rating);
        return "Matching AddPlayer: "+userId+" Success";
    }

    @Override
    public String removePlayer(Integer userId) {
        matchingPool.removePlayer(userId);
        return "Matching RemovePlayer: "+userId+" Success";
    }
}
