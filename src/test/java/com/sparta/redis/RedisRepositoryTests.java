package com.sparta.redis;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RedisRepositoryTests {
    private static String id = "";
    @Autowired
    private ItemRepository itemRepository;

    @Test
    @Order(1)
    public void testAddItem() {
        Item item = Item.builder()
                .name("keyboard")
                .description("Very Expensive Keyboard")
                .price(100000)
                .build();
        item = itemRepository.save(item);
        id = item.getId();
        System.out.println("id = " + id);
    }

    @Test
    @Order(2)
    public void testGetItem() {
        Item item = itemRepository.findById(id)
                .orElseThrow();
        System.out.println(item);
    }

    @Test
    @Order(3)
    public void testUpdateItem() {
        Item item = itemRepository.findById(id)
                .orElseThrow();
        item.setDescription("On Sale!!!");
        item = itemRepository.save(item);
        System.out.println("item.getDescription() = " + item.getDescription());
    }

    @Test
    @Order(4)
    public void testDeleteItem() {
        itemRepository.deleteById(id);
    }
}
