package com.example.demo.ratelimit;

public interface RateLimitRecord {
    Long getRequestCount();

    Long getIntervalRequestCount();

    Long getLastRequestTime();

    String getKey();

    String getPath();

    String getMethod();

    String getIp();

    String getUserId();

    void setRequestCount(Long requestCount);

    void setLastRequestTime(Long lastRequestTime);

    void setIntervalRequestCount(Long intervalRequestCount);
}