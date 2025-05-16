package com.example.demo.ratelimit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateLimitRecordImpl implements RateLimitRecord {
    private String path;
    private String method;
    private String ip;
    private String userId;
    private Long requestCount;
    private Long lastRequestTime;
    private String key;
    private Long intervalRequestCount;
}
