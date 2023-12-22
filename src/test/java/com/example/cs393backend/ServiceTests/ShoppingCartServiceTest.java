package com.example.cs393backend.ServiceTests;

import com.example.cs393backend.dto.ShoppingCartDto;
import com.example.cs393backend.entity.ShoppingCartEntity;
import com.example.cs393backend.entity.UserEntity;
import com.example.cs393backend.repository.ShoppingCartRepository;
import com.example.cs393backend.repository.UserRepository;
import com.example.cs393backend.service.ShoppingCartService;
import com.example.cs393backend.util.ShoppingCartMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    private ShoppingCartDto shoppingCartDto;
    private ShoppingCartEntity shoppingCartEntity;

    @BeforeEach
    void setUp() {
        shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(1L);
        shoppingCartDto.setUserId(1L);

        shoppingCartEntity = new ShoppingCartEntity();
        shoppingCartEntity.setId(1L);
        shoppingCartEntity.setUser(new UserEntity());
    }

    @Test
    void findAllShoppingCarts() {
        when(shoppingCartRepository.findAll()).thenReturn(Collections.singletonList(shoppingCartEntity));
        when(shoppingCartMapper.shoppingCartEntityToDto(any(ShoppingCartEntity.class))).thenReturn(shoppingCartDto);

        List<ShoppingCartDto> result = shoppingCartService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(shoppingCartDto, result.get(0));

        verify(shoppingCartRepository).findAll();
        verify(shoppingCartMapper).shoppingCartEntityToDto(any(ShoppingCartEntity.class));
    }

    @Test
    void findShoppingCartById() {
        when(shoppingCartRepository.findById(anyLong())).thenReturn(Optional.of(shoppingCartEntity));
        when(shoppingCartMapper.shoppingCartEntityToDto(any(ShoppingCartEntity.class))).thenReturn(shoppingCartDto);

        ShoppingCartDto result = shoppingCartService.findById(1L);

        assertNotNull(result);
        assertEquals(shoppingCartDto, result);

        verify(shoppingCartRepository).findById(anyLong());
        verify(shoppingCartMapper).shoppingCartEntityToDto(any(ShoppingCartEntity.class));
    }

    @Test
    void createShoppingCart() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new UserEntity()));
        when(shoppingCartRepository.save(any(ShoppingCartEntity.class))).thenReturn(shoppingCartEntity);
        when(shoppingCartMapper.shoppingCartEntityToDto(any(ShoppingCartEntity.class))).thenReturn(shoppingCartDto);

        ShoppingCartDto result = shoppingCartService.createShoppingCart(shoppingCartDto);

        assertNotNull(result);
        assertEquals(shoppingCartDto, result);

        verify(userRepository).findById(anyLong());
        verify(shoppingCartRepository).save(any(ShoppingCartEntity.class));
        verify(shoppingCartMapper).shoppingCartEntityToDto(any(ShoppingCartEntity.class));
    }

    @Test
    void updateShoppingCart() {
        when(shoppingCartRepository.findById(anyLong())).thenReturn(Optional.of(shoppingCartEntity));
        when(shoppingCartRepository.save(any(ShoppingCartEntity.class))).thenReturn(shoppingCartEntity);


        ShoppingCartDto updatedDto = new ShoppingCartDto();
        updatedDto.setId(1L);
        updatedDto.setUserId(2L);

        when(shoppingCartMapper.shoppingCartEntityToDto(any(ShoppingCartEntity.class))).thenReturn(updatedDto);

        ShoppingCartDto result = shoppingCartService.updateShoppingCart(1L, updatedDto);

        assertNotNull(result);
        assertEquals(updatedDto.getUserId(), result.getUserId());

        verify(shoppingCartRepository).findById(anyLong());
        verify(shoppingCartRepository).save(any(ShoppingCartEntity.class));
        verify(shoppingCartMapper).shoppingCartEntityToDto(any(ShoppingCartEntity.class));
    }

    @Test
    void deleteShoppingCart() {
        Long cartId = 1L;
        when(shoppingCartRepository.existsById(cartId)).thenReturn(true);
        doNothing().when(shoppingCartRepository).deleteById(anyLong());

        shoppingCartService.deleteShoppingCart(cartId);

        verify(shoppingCartRepository).existsById(cartId);
        verify(shoppingCartRepository).deleteById(cartId);
    }
}