
package com.example.cs393backend.ServiceTests;

import com.example.cs393backend.dto.ItemDto;
import com.example.cs393backend.entity.ItemEntity;
import com.example.cs393backend.repository.ItemRepository;
import com.example.cs393backend.service.ItemService;
import com.example.cs393backend.util.ItemMapper;
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
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemService itemService;

    private ItemDto itemDto;
    private ItemEntity itemEntity;

    @BeforeEach
    void setUp() {
        itemDto = new ItemDto();
        itemDto.setId(1L);


        itemEntity = new ItemEntity();
        itemEntity.setId(1L);

    }

    @Test
    void findAllItems() {
        when(itemRepository.findAll()).thenReturn(Collections.singletonList(itemEntity));
        when(itemMapper.itemEntityToDto(any(ItemEntity.class))).thenReturn(itemDto);

        List<ItemDto> result = itemService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(itemDto, result.get(0));

        verify(itemRepository).findAll();
        verify(itemMapper).itemEntityToDto(any(ItemEntity.class));
    }

    @Test
    void findItemById() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(itemEntity));
        when(itemMapper.itemEntityToDto(any(ItemEntity.class))).thenReturn(itemDto);

        ItemDto result = itemService.findById(1L);

        assertNotNull(result);
        assertEquals(itemDto, result);

        verify(itemRepository).findById(anyLong());
        verify(itemMapper).itemEntityToDto(any(ItemEntity.class));
    }

    @Test
    void createItem() {
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(itemEntity);
        when(itemMapper.itemDtoToEntity(any(ItemDto.class))).thenReturn(itemEntity);
        when(itemMapper.itemEntityToDto(any(ItemEntity.class))).thenReturn(itemDto);

        ItemDto result = itemService.createItem(itemDto);

        assertNotNull(result);
        assertEquals(itemDto, result);

        verify(itemRepository).save(any(ItemEntity.class));
        verify(itemMapper).itemDtoToEntity(any(ItemDto.class));
        verify(itemMapper).itemEntityToDto(any(ItemEntity.class));
    }

    @Test
    void updateItem() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(itemEntity));
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(itemEntity);


        ItemDto updatedItemDto = new ItemDto();
        updatedItemDto.setId(1L);

        when(itemMapper.itemEntityToDto(any(ItemEntity.class))).thenReturn(updatedItemDto);
        ItemDto result = itemService.updateItem(1L, updatedItemDto);

        assertNotNull(result);

        verify(itemRepository).findById(anyLong());
        verify(itemRepository).save(any(ItemEntity.class));
        verify(itemMapper).itemEntityToDto(any(ItemEntity.class));
    }

    @Test
    void deleteItem() {
        Long itemId = 1L;
        when(itemRepository.existsById(itemId)).thenReturn(true);
        doNothing().when(itemRepository).deleteById(anyLong());

        itemService.deleteItem(itemId);

        verify(itemRepository).existsById(itemId);
        verify(itemRepository).deleteById(itemId);
    }
}