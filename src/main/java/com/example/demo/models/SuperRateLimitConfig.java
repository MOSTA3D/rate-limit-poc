package com.example.demo.models;

import com.example.demo.ratelimit.RateLimitConfig;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class SuperRateLimitConfig implements RateLimitConfig {
    @Column(name = "max_requests")
    private Long maxRequests;
    @Column(name = "useIp")
    private Boolean useIp;
    @Column(name = "path")
    private String path;
    @Column(name = "method")
    private String method;

    @Override
    public Boolean getUseIp() {
        return useIp;
    }

    @Override
    public Long getMaxRequests() {
        return maxRequests;
    }

}