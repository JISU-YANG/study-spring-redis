package com.sparta.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTemplateTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void stringOpsTest(){
        // 문자열 조작
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("simpleKey", "simpleValue");
        System.out.println("ops.get(\"simpleKey\") = " + ops.get("simpleKey"));
        stringRedisTemplate.delete("simpleKey");

        // 집합 조작
        SetOperations<String, String> setOps = stringRedisTemplate.opsForSet();
        setOps.add("hobbies", "games");
        setOps.add("hobbies", "games","coding","alcohol","games");
        System.out.println("setOps.size(\"hobbies\") = " + setOps.size("hobbies"));
        stringRedisTemplate.expire("hobbies",10, TimeUnit.SECONDS);
    }
    
    @Autowired
    private RedisTemplate<String, ItemDto> itemRedisTemplate;
    
    @Test
    public void itemRedisTemplateTest(){
        ValueOperations<String, ItemDto> ops = itemRedisTemplate.opsForValue();
        ops.set("my:keyboard", ItemDto.builder()
                .name("Mechanical Keyboard")
                .price(250000)
                .description("OMG")
                .build());
        System.out.println("ops.get(\"my:keyboard\") = " + ops.get("my:keyboard"));

        ops.set("my:mouse", ItemDto.builder()
                .name("Mechanical Mouse")
                .price(100000)
                .description("OMG")
                .build());
        System.out.println("ops.get(\"my:mouse\") = " + ops.get("my:mouse"));
    }
}
