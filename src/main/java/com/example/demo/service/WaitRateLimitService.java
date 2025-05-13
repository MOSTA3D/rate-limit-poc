package com.example.demo.service;

import com.example.demo.models.WaitRateLimitConfig;
import com.example.demo.ratelimit.*;
import com.example.demo.repositories.RateLimitRecordRepoJpa;
import com.example.demo.repositories.WaitRateLimitConfigRepo;
import org.springframework.stereotype.Service;

@Service
class WaitRateLimitService extends AbstractRateLimitService<WaitRateLimitConfig> {
    WaitRateLimitService(WaitRateLimitConfigRepo waitRateLimitConfigRepo, RateLimitRecordRepoJpa rateLimitRecordRepo) {
        super(waitRateLimitConfigRepo, rateLimitRecordRepo);
    }

    @Override
    public RateLimitResult applyRateLimit(RateLimitRecord rateLimitRecord, RateLimitConfig rateLimitConfig) {
        RateLimitResult rateLimitResult = new RateLimitResult();
        long currentTime = System.currentTimeMillis();
        long timeToWait = rateLimitRecord.getLastRequestTime() + ((WaitRateLimitConfig) rateLimitConfig).getTimeToWait() - currentTime;
        rateLimitResult.setTimeToWait(timeToWait);
        rateLimitResult.setStatus((timeToWait > 0) ? RateLimitStatus.LIMITED : RateLimitStatus.ALLOWED);
        return rateLimitResult;
    }
}