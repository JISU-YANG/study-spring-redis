package com.sparta.redis.order;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<ItemOrder, String> {
}
