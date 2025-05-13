package com.example.demo.ratelimit;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractRateLimitService<T extends RateLimitConfig> {
    private final RateLimitConfigRepo<T> rateLimitConfigRepo;
    private final RateLimitRecordRepo rateLimitRecordRepo;

    public RateLimitResult rateLimit(RateLimitRequestData requestData) {
        RateLimitResult rateLimitResult = new RateLimitResult();
        T rateLimitConfig = rateLimitConfigRepo.findByPathAndMethod(requestData.getPath(), requestData.getMethod());

        if (rateLimitConfig == null) {
            rateLimitResult.setStatus(RateLimitStatus.NONE);
            return rateLimitResult;
        }

        // TODO("if other keys, make it abstract")
        String key = rateLimitConfig.getUseIp() ? requestData.getIp() : requestData.getUserId();

        RateLimitRecord rateLimitRecord = rateLimitRecordRepo.findByPathAndMethodAndKey(requestData.getPath(), requestData.getMethod(), key);
        if (rateLimitRecord == null) {
            rateLimitResult.setStatus(RateLimitStatus.FIRST);
            rateLimitRecordRepo.createNewRecord(
                    requestData.getPath(),
                    requestData.getMethod(),
                    key,
                    requestData.getIp(),
                    requestData.getUserId()
            );
            return rateLimitResult;
        }

        if (rateLimitRecord.getRequestCount() >= rateLimitConfig.getMaxRequests()) {
            rateLimitResult.setStatus(RateLimitStatus.BLOCKED);
            return rateLimitResult;
        }

        rateLimitResult = applyRateLimit(rateLimitRecord, rateLimitConfig);

        // TODO("make an abstract update method if this implementation is not sufficient")
        updateRecord(rateLimitRecord, rateLimitResult);
        return rateLimitResult;
    }

    private void updateRecord(RateLimitRecord rateLimitRecord, RateLimitResult rateLimitResult) {
        if (rateLimitResult.getStatus() == RateLimitStatus.LIMITED) {
            rateLimitRecord.setRequestCount(rateLimitRecord.getRequestCount() + 1);
        }
        rateLimitRecord.setLastRequestTime(System.currentTimeMillis());
        rateLimitRecordRepo.updateRecord(rateLimitRecord);
    }

    public abstract RateLimitResult applyRateLimit(RateLimitRecord rateLimitRecord, RateLimitConfig rateLimitConfig);
}