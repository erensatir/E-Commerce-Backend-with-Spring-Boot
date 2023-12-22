package com.example.cs393backend.service;

import com.example.cs393backend.dto.OrderDto;
import com.example.cs393backend.entity.OrderEntity;
import com.example.cs393backend.entity.UserEntity;
import com.example.cs393backend.entity.ProductEntity;
import com.example.cs393backend.entity.AddressEntity;
import com.example.cs393backend.util.OrderMapper;
import com.example.cs393backend.repository.OrderRepository;
import com.example.cs393backend.repository.UserRepository;
import com.example.cs393backend.repository.ProductRepository;
import com.example.cs393backend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;

    private final OrderMapper orderMapper;


    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, AddressRepository addressRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderDto> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::orderEntityToDto)
                .collect(Collectors.toList());
    }

    public OrderDto findById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.orderEntityToDto(order);
    }

    public OrderDto createOrder(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity();
        setOrderDetails(orderEntity, orderDto);
        OrderEntity savedOrder = orderRepository.save(orderEntity);
        return orderMapper.orderEntityToDto(savedOrder);
    }

    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        OrderEntity existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        setOrderDetails(existingOrder, orderDto);
        OrderEntity updatedOrder = orderRepository.save(existingOrder);
        return orderMapper.orderEntityToDto(updatedOrder);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(id);
    }

    private void setOrderDetails(OrderEntity orderEntity, OrderDto orderDto) {
        orderEntity.setOrderDate(orderDto.getOrderDate());
        orderEntity.setTotalAmount(orderDto.getTotalAmount());

        UserEntity user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        orderEntity.setUser(user);

        AddressEntity address = addressRepository.findById(orderDto.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));
        orderEntity.setAddress(address);

        List<ProductEntity> products = productRepository.findAllById(orderDto.getProductIds());
        orderEntity.setProducts(products);
    }
}
