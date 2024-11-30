package com.sparta.redis.item;


import com.sparta.redis.item.domain.Item;
import com.sparta.redis.item.domain.ItemDto;
import com.sparta.redis.item.domain.ItemOrder;
import com.sparta.redis.item.repository.ItemRepository;
import com.sparta.redis.item.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final ZSetOperations<String, ItemDto> rankOps;

    public ItemService(
            ItemRepository itemRepository,
            OrderRepository orderRepository,
            RedisTemplate<String, ItemDto> rankTemplate
    ) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.rankOps = rankTemplate.opsForZSet();
    }

    public void purchase(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderRepository.save(ItemOrder.builder()
                .item(item)
                .count(1)
                .build());
        rankOps.incrementScore(
                "soldRanks",
                ItemDto.fromEntity(item),
                1
        );
    }

    public List<ItemDto> getMostSold(){
        // rankOps.reverseRange(): 순서를 보장 하는 Set
        Set<ItemDto> ranks = rankOps.reverseRange("soldRanks", 0, 2);
        if(ranks == null) return Collections.emptyList();
        return ranks.stream().toList();
    }
}
