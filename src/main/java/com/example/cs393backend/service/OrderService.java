package com.example.cs393backend.service;

import com.example.cs393backend.dto.OrderDto;
import com.example.cs393backend.entity.OrderEntity;
import com.example.cs393backend.repository.OrderRepository;
import com.example.cs393backend.util.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    public OrderDto createOrder(OrderDto orderDto) {
        OrderEntity orderEntity = orderMapper.toEntity(orderDto);
        orderRepository.save(orderEntity);
        return orderMapper.toDto(orderEntity);
    }

    public OrderDto getOrder(Long id) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toDto(orderEntity);
    }

    public OrderDto updateOrder(OrderDto orderDto) {
        OrderEntity orderEntity = orderRepository.findById(orderDto.getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderMapper.updateOrderFromDto(orderDto, orderEntity);
        orderRepository.save(orderEntity);
        return orderMapper.toDto(orderEntity);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
