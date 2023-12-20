package com.example.cs393backend.service;

import com.example.cs393backend.dto.ItemDto;
import com.example.cs393backend.entity.ItemEntity;
import com.example.cs393backend.util.ItemMapper;
import com.example.cs393backend.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public List<ItemDto> findAll() {
        return itemRepository.findAll().stream()
                .map(itemMapper::itemEntityToDto)
                .collect(Collectors.toList());
    }

    public ItemDto findById(Long id) {
        ItemEntity item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        return itemMapper.itemEntityToDto(item);
    }

    public ItemDto createItem(ItemDto itemDto) {
        ItemEntity itemEntity = itemMapper.itemDtoToEntity(itemDto);
        ItemEntity savedItem = itemRepository.save(itemEntity);
        return itemMapper.itemEntityToDto(savedItem);
    }

    public ItemDto updateItem(Long id, ItemDto itemDto) {
        ItemEntity existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        // Update logic (e.g., quantity) based on itemDto
        existingItem.setQuantity(itemDto.getQuantity());
        ItemEntity updatedItem = itemRepository.save(existingItem);
        return itemMapper.itemEntityToDto(updatedItem);
    }

    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item not found");
        }
        itemRepository.deleteById(id);
    }
}
