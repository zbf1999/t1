package com.example.zhaowang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

@Service
public class MessageService {
    private static final String SMS_KEY_PREFIX = "sms:";
    private static final int EXPIRATION_DAYS = 7;

    private final RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    public MessageService(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean sendSMS(int userId) {
        String key = SMS_KEY_PREFIX + userId;
        LocalDate currentDate = LocalDate.now();
        long timestamp = currentDate.atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond();
        String dailyKey = key + ":" + timestamp;
        Long countAfterIncrement = redisTemplate.opsForValue().increment(dailyKey);
        if (countAfterIncrement != null && countAfterIncrement == 1) {
            LocalDate expirationDate = currentDate.plusDays(EXPIRATION_DAYS);
            redisTemplate.expireAt(dailyKey, expirationDate.atStartOfDay().toInstant(ZoneOffset.UTC));
        }
        return true;
    }

    public int getSMSCount(int userId) {
        String key = SMS_KEY_PREFIX + userId;
        int count = 0;
        for (int i = 0; i < EXPIRATION_DAYS; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            long timestamp = date.atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond();
            String dailyKey = key + ":" + timestamp;
            Integer dailyCount = redisTemplate.opsForValue().get(dailyKey);
            if (dailyCount != null) {
                count += dailyCount;
            }
        }
        return count;
    }
}
