package com.example.RedisCaching.service;

import com.example.RedisCaching.dto.UserProfile;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private ExternalApiService externalApiService;

    @Autowired
    StringRedisTemplate redisTemplate;

    public UserProfile getUserProfile(String userId){

        String userName = null;

        //레디스에서 캐싱하기
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String cacheName = ops.get("nameKey:"+userId);

        if(cacheName != null){
            userName = cacheName;
        }
        else {
            userName = externalApiService.getUserName(userId);
            ops.set("nameKey:" + userId, userName,15, TimeUnit.SECONDS);
        }

        //String userName = externalApiService.getUserName(userId);
        int userAge = externalApiService.getUserAge(userId);

        return new UserProfile(userName, userAge);
    }


}
