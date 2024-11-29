package com.sparta.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;

    @GetMapping
    public List<ItemOrder> getOrders() {
        List<ItemOrder> orders = new ArrayList<>();
        orderRepository.findAll()
                .forEach(orders::add);
        return orders;
    }

    @GetMapping("/{order_id}")
    public ItemOrder getOrder(@PathVariable String order_id) {
        return orderRepository.findById(order_id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @PostMapping
    public ItemOrder addOrder(
            @RequestBody ItemOrder itemOrder
    ) {
        return orderRepository.save(itemOrder);
    }

    @PutMapping("/{order_id}")
    public ItemOrder updateOrder(
            @PathVariable("order_id") String orderId,
            @RequestBody ItemOrder itemOrder
    ) {
        ItemOrder updatedOrder = orderRepository.findById(orderId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        updatedOrder.setItem(itemOrder.getItem());
        updatedOrder.setStatus(itemOrder.getStatus());
        updatedOrder.setCount(itemOrder.getCount());
        updatedOrder.setTotalPrice(itemOrder.getTotalPrice());
        return orderRepository.save(updatedOrder);
    }

    @DeleteMapping("/{order_id}")
    public void deleteOrder(
            @PathVariable("order_id") String orderId
    ) {
        ItemOrder itemOrder = orderRepository.findById(orderId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        orderRepository.deleteById(itemOrder.getId());
    }
}
