package com.example.RedisCaching.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ExternalApiService {
    public String getUserName(String userId){
        // 외부 서비스나 DB 호출
        try {
            Thread.sleep(500); // o.5초
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Getting user name from other service...;");

        if(userId.equals("A")){
            return "Adam";
        }
        if(userId.equals("B")){
            return "Bob";
        }

        return "";
    }

    /**
     * Spring의 Cache기능을 이용해서 쉽게 처리하기
     * @param userId
     * @return
     */
    @Cacheable(cacheNames = "userAgeCache", key = "#userId")
    public int getUserAge(String userId){
        // 외부 서비스나 DB 호출
        try {
            Thread.sleep(500); // o.5초
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Getting user age from other service...;");

        if(userId.equals("A")){
            return 23;
        }
        if(userId.equals("B")){
            return 45;
        }

        return 0;
    }
}
