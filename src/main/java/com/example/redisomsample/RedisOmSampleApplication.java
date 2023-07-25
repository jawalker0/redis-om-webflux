package com.example.redisomsample;

import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRedisDocumentRepositories
public class RedisOmSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisOmSampleApplication.class, args);
    }

}
