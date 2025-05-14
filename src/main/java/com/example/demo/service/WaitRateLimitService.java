package com.example.demo.service;

import com.example.demo.models.WaitRateLimitConfig;
import com.example.demo.ratelimit.*;
import com.example.demo.repositories.RateLimitRecordRepoJpa;
import com.example.demo.repositories.WaitRateLimitConfigRepo;
import org.springframework.stereotype.Service;

@Service
class WaitRateLimitService extends AbstractRateLimitService<WaitRateLimitConfig> {
    private final RateLimitRecordRepoJpa rateLimitRecordRepo;
    private final WaitRateLimitConfigRepo waitRateLimitConfigRepo;

    WaitRateLimitService(WaitRateLimitConfigRepo waitRateLimitConfigRepo, RateLimitRecordRepoJpa rateLimitRecordRepo) {
        super(waitRateLimitConfigRepo, rateLimitRecordRepo);
        this.waitRateLimitConfigRepo = waitRateLimitConfigRepo;
        this.rateLimitRecordRepo = rateLimitRecordRepo;
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

    @Override
    public void updateRecord(RateLimitRecord rateLimitRecord, RateLimitResult rateLimitResult, WaitRateLimitConfig rateLimitConfig) {
        long currentTime = System.currentTimeMillis();
        if (rateLimitResult.getStatus() == RateLimitStatus.LIMITED) {
            rateLimitRecord.setRequestCount(rateLimitRecord.getRequestCount() + 1);
        }
        if (
                rateLimitResult.getStatus() == RateLimitStatus.ALLOWED &&
                        currentTime > (rateLimitRecord.getLastRequestTime() + rateLimitRecord.getIntervalRequestCount() * rateLimitConfig.getTimeToWait())
        ) {
            rateLimitRecord.setIntervalRequestCount(0L);
        }
        rateLimitRecord.setIntervalRequestCount(rateLimitRecord.getIntervalRequestCount() + 1);
        rateLimitRecord.setLastRequestTime(currentTime);
        rateLimitRecordRepo.updateRecord(rateLimitRecord);
    }
}