package com.example.zhaowang.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class MessageController {
    private final RedisTemplate<String, String> redisTemplate;
    private HashOperations<String, String, String> hashOperations;

    @Autowired
    public MessageController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @GetMapping("/sendMsg")
    public Map<String, Object> sendMsg(@RequestParam("id") String userId) {
        String key = getKey(userId);
        String currentCount = hashOperations.get(key, "count");
        int count = currentCount != null ? Integer.parseInt(currentCount) : 0;
        hashOperations.put(key, "count", String.valueOf(count + 1));
        redisTemplate.expire(key, 7, TimeUnit.DAYS);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

    @GetMapping("/record")
    public Map<String, Object> getRecord(@RequestParam("id") String userId) {
        String key = getKey(userId);
        String userName = hashOperations.get(key, "userName");
        String count = hashOperations.get(key, "count");
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("id", userId);
        response.put("userName", userName);
        response.put("count", count != null ? Integer.parseInt(count) : 0);
        return response;
    }

    private String getKey(String userId) {
        return "user:" + userId;
    }
}
