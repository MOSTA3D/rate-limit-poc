package com.example.demo.filter;

import com.example.demo.models.WaitRateLimitConfig;
import com.example.demo.ratelimit.AbstractRateLimitService;
import com.example.demo.ratelimit.RateLimitRequestData;
import com.example.demo.ratelimit.RateLimitResult;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimitFilter implements Filter {
    private final AbstractRateLimitService<WaitRateLimitConfig> rateLimitService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RateLimitRequestData rateLimitData = getRateLimitData((HttpServletRequest) request);
        RateLimitResult rateLimitResult = rateLimitService.rateLimit(rateLimitData);

        switch (rateLimitResult.getStatus()) {
            case LIMITED -> response.getWriter().write(String.format("Rate limit exceeded. Please wait %s  seconds.", rateLimitResult.getTimeToWait()));
            case BLOCKED -> response.getWriter().write("You are blocked, please contact support.");
            default -> chain.doFilter(request, response);
        }
    }

    private RateLimitRequestData getRateLimitData(HttpServletRequest request) {
        RateLimitRequestData rateLimitData = new RateLimitRequestData();
        rateLimitData.setPath(request.getRequestURI());
        rateLimitData.setMethod(request.getMethod());
        rateLimitData.setIp(getUserIp(request));
        return rateLimitData;
    }

    private String getUserIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        return (xForwardedFor != null && !xForwardedFor.isEmpty()) ? xForwardedFor.split(",")[0] : request.getRemoteAddr();
    }
}