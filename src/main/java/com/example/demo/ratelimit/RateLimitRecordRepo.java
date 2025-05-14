package com.example.demo.ratelimit;

public interface RateLimitRecordRepo {
    RateLimitRecord findByPathAndMethodAndKey(String path, String method, String key);
    RateLimitRecord createNewRecord(RateLimitRecord record);
    RateLimitRecord updateRecord(RateLimitRecord record);
}