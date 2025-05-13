package com.example.demo.ratelimit;

import lombok.Data;

@Data
public class RateLimitRequestData {
     private String path;
     private String method;
     private String ip;
     private String userId;
}