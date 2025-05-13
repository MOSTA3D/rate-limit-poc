package com.example.demo.ratelimit;


public interface RateLimitConfigRepo<T extends RateLimitConfig> {
    T findByPathAndMethod(String path, String method);
}