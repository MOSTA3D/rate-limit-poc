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
                    new RateLimitRecordImpl(
                            requestData.getPath(),
                            requestData.getMethod(),
                            requestData.getIp(),
                            requestData.getUserId(),
                            1L,
                            System.currentTimeMillis(),
                            key,
                            rateLimitConfig.getInterval()
                    )
            );
            return rateLimitResult;
        }

        if (rateLimitRecord.getIntervalRequestCount() < rateLimitConfig.getInterval()) {
            rateLimitResult.setStatus(RateLimitStatus.ALLOWED);
            return rateLimitResult;
        }


        if (rateLimitRecord.getRequestCount() >= rateLimitConfig.getMaxRequests()) {
            rateLimitResult.setStatus(RateLimitStatus.BLOCKED);
            return rateLimitResult;
        }

        rateLimitResult = applyRateLimit(rateLimitRecord, rateLimitConfig);

        // TODO("make an abstract update method if this implementation is not sufficient")
        updateRecord(rateLimitRecord, rateLimitResult, rateLimitConfig);
        return rateLimitResult;
    }

    public abstract RateLimitResult applyRateLimit(RateLimitRecord rateLimitRecord, RateLimitConfig rateLimitConfig);

    public abstract void updateRecord(RateLimitRecord rateLimitRecord, RateLimitResult rateLimitResult, T rateLimitConfig);
}