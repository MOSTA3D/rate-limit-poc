package com.example.demo.ratelimit;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RateLimitResult {
    private Long timeToWait;
    private RateLimitStatus status;
}