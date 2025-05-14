package com.example.demo.repositories;

import com.example.demo.models.RateLimitRecordEntity;
import com.example.demo.ratelimit.RateLimitRecord;
import com.example.demo.ratelimit.RateLimitRecordRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateLimitRecordRepoJpa extends RateLimitRecordRepo, JpaRepository<RateLimitRecordEntity, Long> {
    @Override
    default RateLimitRecord createNewRecord(RateLimitRecord record) {
        return save(new RateLimitRecordEntity(record));
    }

    @Override
    default RateLimitRecord updateRecord(RateLimitRecord record) {
        return save((RateLimitRecordEntity) record);
    }
}