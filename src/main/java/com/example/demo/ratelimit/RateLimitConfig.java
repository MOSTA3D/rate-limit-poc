package com.example.demo.ratelimit;

public interface RateLimitConfig {
    Boolean getUseIp();

    Long getMaxRequests();
}