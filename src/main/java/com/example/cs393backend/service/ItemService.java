package com.example.cs393backend.service;

import com.example.cs393backend.dto.ItemDto;
import com.example.cs393backend.entity.ItemEntity;
import com.example.cs393backend.repository.ItemRepository;
import com.example.cs393backend.util.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    public ItemDto createItem(ItemDto itemDto) {
        ItemEntity itemEntity = itemMapper.toEntity(itemDto);
        itemRepository.save(itemEntity);
        return itemMapper.toDto(itemEntity);
    }

    public ItemDto getItem(long id) {
        ItemEntity itemEntity = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        return itemMapper.toDto(itemEntity);
    }

    public ItemDto updateItem(ItemDto itemDto) {
        ItemEntity itemEntity = itemRepository.findById(itemDto.getId())
                .orElseThrow(() -> new RuntimeException("Item not found"));
        itemMapper.updateItemFromDto(itemDto, itemEntity);
        itemRepository.save(itemEntity);
        return itemMapper.toDto(itemEntity);
    }

    public void deleteItem(long id) {
        itemRepository.deleteById(id);
    }
}
