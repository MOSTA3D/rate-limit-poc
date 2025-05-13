package com.example.demo.repositories;

import com.example.demo.models.WaitRateLimitConfig;
import com.example.demo.ratelimit.RateLimitConfigRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitRateLimitConfigRepo extends RateLimitConfigRepo<WaitRateLimitConfig>, JpaRepository<WaitRateLimitConfig, Long> {
}