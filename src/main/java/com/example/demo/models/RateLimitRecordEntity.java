package com.example.demo.models;

import com.example.demo.ratelimit.RateLimitRecord;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rate_limit_record")
public class RateLimitRecordEntity implements RateLimitRecord {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "path")
    private String path;
    @Column(name = "method")
    private String method;
    @Column(name = "ip")
    private String ip;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "request_count")
    private Long requestCount;
    @Column(name = "last_request_time")
    private Long lastRequestTime;
    @Column(name = "key")
    private String key;
}
