package com.example.cs393backend.service;

import com.example.cs393backend.dto.AddressDto;
import com.example.cs393backend.entity.AddressEntity;
import com.example.cs393backend.repository.AddressRepository;
import com.example.cs393backend.util.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    public AddressDto createAddress(AddressDto addressDto) {
        AddressEntity addressEntity = addressMapper.toEntity(addressDto);
        addressRepository.save(addressEntity);
        return addressMapper.toDto(addressEntity);
    }

    public AddressDto getAddress(Long id) {
        AddressEntity addressEntity = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        return addressMapper.toDto(addressEntity);
    }

    public AddressDto updateAddress(AddressDto addressDto) {
        AddressEntity addressEntity = addressRepository.findById(addressDto.getId())
                .orElseThrow(() -> new RuntimeException("Address not found"));
        addressMapper.updateAddressFromDto(addressDto, addressEntity);
        addressRepository.save(addressEntity);
        return addressMapper.toDto(addressEntity);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
