package com.example.urlshortener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UrlService {
    
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final Map<String, String> shortToLong = new ConcurrentHashMap<>();
    private final Map<String, String> longToShort = new ConcurrentHashMap<>();
    private final Map<String, Integer> domainCounts = new ConcurrentHashMap<>();
    
    private final AtomicLong counter = new AtomicLong(1000000000L);

    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();

        while (num > 0) {
            sb.append(BASE62.charAt((int) (num % 62)));
            num /= 62;
        }
        return sb.reverse().toString();
    }

    public String shortenUrl(String originalUrl) {
        // Return existing url if already shortened
        if (longToShort.containsKey(originalUrl)) {
            return longToShort.get(originalUrl);
        }

        // Generate short key using Base62
        String shortKey = encode(counter.getAndIncrement());
        
        shortToLong.put(shortKey, originalUrl);
        longToShort.put(originalUrl, shortKey);
        
        updateDomainMetrics(originalUrl);
        
        return "http://localhost:8080/api/" + shortKey;
    }

    private void updateDomainMetrics(String url){

    }
}
