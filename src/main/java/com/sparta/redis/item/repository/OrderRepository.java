package com.sparta.redis.item.repository;


import com.sparta.redis.item.domain.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<ItemOrder, Long> { }
