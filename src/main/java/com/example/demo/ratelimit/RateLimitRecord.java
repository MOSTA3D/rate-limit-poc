package com.example.demo.ratelimit;

public interface RateLimitRecord {
    Long getRequestCount();

    Long getLastRequestTime();

    String getKey();

    String getPath();

    String getMethod();

    String getIp();

    String getUserId();

    void setRequestCount(Long requestCount);

    void setLastRequestTime(Long lastRequestTime);
}