package com.sparta.redis.item.repository;

import com.sparta.redis.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
//    @Query("")
//    List<Item> top10MostSoldItems();
}
