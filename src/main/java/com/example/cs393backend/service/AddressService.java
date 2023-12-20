package com.example.cs393backend.service;

import com.example.cs393backend.dto.AddressDto;
import com.example.cs393backend.entity.AddressEntity;
import com.example.cs393backend.util.AddressMapper;
import com.example.cs393backend.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public List<AddressDto> findAll() {
        return addressRepository.findAll().stream()
                .map(addressMapper::addressEntityToDto)
                .collect(Collectors.toList());
    }

    public AddressDto findById(Long id) {
        AddressEntity address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        return addressMapper.addressEntityToDto(address);
    }

    public AddressDto createAddress(AddressDto addressDto) {
        AddressEntity addressEntity = addressMapper.addressDtoToEntity(addressDto);
        AddressEntity savedAddress = addressRepository.save(addressEntity);
        return addressMapper.addressEntityToDto(savedAddress);
    }

    public AddressDto updateAddress(Long id, AddressDto addressDto) {
        AddressEntity existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        existingAddress.setStreet(addressDto.getStreet());
        existingAddress.setCity(addressDto.getCity());
        existingAddress.setState(addressDto.getState());
        existingAddress.setZip(addressDto.getZip());
        AddressEntity updatedAddress = addressRepository.save(existingAddress);
        return addressMapper.addressEntityToDto(updatedAddress);
    }

    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new RuntimeException("Address not found");
        }
        addressRepository.deleteById(id);
    }
}
