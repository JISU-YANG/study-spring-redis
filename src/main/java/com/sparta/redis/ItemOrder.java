package com.sparta.redis;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("order")
public class ItemOrder {
    @Id
    private String id;
    private String item;
    private Integer count;
    private Long totalPrice;
    private String status;
}
