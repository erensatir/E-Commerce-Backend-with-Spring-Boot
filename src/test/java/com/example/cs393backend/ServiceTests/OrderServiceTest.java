
package com.example.cs393backend.ServiceTests;

import com.example.cs393backend.dto.OrderDto;
import com.example.cs393backend.entity.OrderEntity;
import com.example.cs393backend.entity.UserEntity;
import com.example.cs393backend.entity.ProductEntity;
import com.example.cs393backend.entity.AddressEntity;
import com.example.cs393backend.repository.OrderRepository;
import com.example.cs393backend.repository.UserRepository;
import com.example.cs393backend.repository.ProductRepository;
import com.example.cs393backend.repository.AddressRepository;
import com.example.cs393backend.service.OrderService;
import com.example.cs393backend.util.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    private OrderDto orderDto;
    private OrderEntity orderEntity;

    @BeforeEach
    void setUp() {
        orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setUserId(1L);
        orderDto.setAddressId(1L);
        orderDto.setProductIds(Collections.singletonList(1L));
        orderDto.setTotalAmount(new BigDecimal("100.00"));

        orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setUser(new UserEntity());
        orderEntity.setAddress(new AddressEntity());
        orderEntity.setProducts(new ArrayList<>());
        orderEntity.setTotalAmount(new BigDecimal("100.00"));

    }

    @Test
    void findAllOrders() {
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(orderEntity));
        when(orderMapper.orderEntityToDto(any(OrderEntity.class))).thenReturn(orderDto);

        List<OrderDto> result = orderService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(orderDto, result.get(0));

        verify(orderRepository).findAll();
        verify(orderMapper).orderEntityToDto(any(OrderEntity.class));
    }

    @Test
    void findOrderById() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(orderEntity));
        when(orderMapper.orderEntityToDto(any(OrderEntity.class))).thenReturn(orderDto);

        OrderDto result = orderService.findById(1L);

        assertNotNull(result);
        assertEquals(orderDto, result);

        verify(orderRepository).findById(anyLong());
        verify(orderMapper).orderEntityToDto(any(OrderEntity.class));
    }

    @Test
    void createOrder() {
        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(new UserEntity()));
        when(addressRepository.findById(eq(1L))).thenReturn(Optional.of(new AddressEntity()));

        // Using any() instead of anyLong() for findAllById
        when(productRepository.findAllById(any())).thenReturn(Collections.singletonList(new ProductEntity()));

        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        when(orderMapper.orderEntityToDto(any(OrderEntity.class))).thenReturn(orderDto);

        OrderDto result = orderService.createOrder(orderDto);

        assertNotNull(result);
        assertEquals(orderDto, result);

        verify(orderRepository).save(any(OrderEntity.class));
        verify(userRepository).findById(eq(1L));
        verify(addressRepository).findById(eq(1L));
        verify(productRepository).findAllById(any());
        verify(orderMapper).orderEntityToDto(any(OrderEntity.class));
    }


    @Test
    void updateOrder() {
        // Given
        OrderDto updatedOrderDto = new OrderDto();
        updatedOrderDto.setId(1L);
        updatedOrderDto.setTotalAmount(new BigDecimal("200.00"));
        // Set other updated properties

        OrderEntity existingOrder = new OrderEntity();
        existingOrder.setId(1L);
        existingOrder.setUser(new UserEntity());
        existingOrder.setAddress(new AddressEntity());
        existingOrder.setProducts(new ArrayList<>());
        existingOrder.setTotalAmount(new BigDecimal("100.00"));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userRepository.findById(any())).thenReturn(Optional.of(new UserEntity()));
        when(addressRepository.findById(any())).thenReturn(Optional.of(new AddressEntity()));
        when(productRepository.findAllById(any())).thenReturn(Collections.singletonList(new ProductEntity()));
        when(orderMapper.orderEntityToDto(any(OrderEntity.class))).thenReturn(updatedOrderDto);

        // When
        OrderDto result = orderService.updateOrder(1L, updatedOrderDto);

        // Then
        assertNotNull(result);
        assertEquals(updatedOrderDto, result);
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any(OrderEntity.class));
        verify(userRepository).findById(any());
        verify(addressRepository).findById(any());
        verify(productRepository).findAllById(any());
        verify(orderMapper).orderEntityToDto(any(OrderEntity.class));
    }






    @Test
    void deleteOrder() {
        Long orderId = 1L;
        when(orderRepository.existsById(orderId)).thenReturn(true);
        doNothing().when(orderRepository).deleteById(anyLong());

        orderService.deleteOrder(orderId);

        verify(orderRepository).existsById(orderId);
        verify(orderRepository).deleteById(orderId);
    }
}