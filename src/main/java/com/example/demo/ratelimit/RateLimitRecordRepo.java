package com.example.demo.ratelimit;

public interface RateLimitRecordRepo {
    RateLimitRecord findByPathAndMethodAndKey(String path, String method, String key);
    RateLimitRecord createNewRecord(String path, String method, String key, String ip, String userId);
    RateLimitRecord updateRecord(RateLimitRecord record);
}