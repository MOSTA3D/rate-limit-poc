package com.example.demo.repositories;

import com.example.demo.models.RateLimitRecordEntity;
import com.example.demo.ratelimit.RateLimitRecord;
import com.example.demo.ratelimit.RateLimitRecordRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateLimitRecordRepoJpa extends RateLimitRecordRepo, JpaRepository<RateLimitRecordEntity, Long> {
    @Override
    default RateLimitRecord createNewRecord(String path, String method, String key, String ip, String userId) {
        RateLimitRecordEntity record = new RateLimitRecordEntity();
        record.setPath(path);
        record.setMethod(method);
        record.setKey(key);
        record.setIp(ip);
        record.setUserId(userId);
        record.setLastRequestTime(System.currentTimeMillis());
        record.setRequestCount(1L);
        return save(record);
    }

    @Override
    default RateLimitRecord updateRecord(RateLimitRecord record) {
        return save((RateLimitRecordEntity) record);
    }
}