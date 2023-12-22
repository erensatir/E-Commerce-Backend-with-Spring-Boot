
package com.example.cs393backend.ServiceTests;

import com.example.cs393backend.dto.AddressDto;
import com.example.cs393backend.entity.AddressEntity;
import com.example.cs393backend.repository.AddressRepository;
import com.example.cs393backend.service.AddressService;
import com.example.cs393backend.util.AddressMapper;
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
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressService addressService;

    private AddressDto addressDto;
    private AddressEntity addressEntity;

    @BeforeEach
    void setUp() {
        addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setStreet("123 Main St");
        addressDto.setCity("Anytown");
        addressDto.setState("State");
        addressDto.setZip("12345");

        addressEntity = new AddressEntity();
        addressEntity.setId(1L);
        addressEntity.setStreet("123 Main St");
        addressEntity.setCity("Anytown");
        addressEntity.setState("State");
        addressEntity.setZip("12345");
    }

    @Test
    void findAllAddresses() {
        when(addressRepository.findAll()).thenReturn(Collections.singletonList(addressEntity));
        when(addressMapper.addressEntityToDto(any(AddressEntity.class))).thenReturn(addressDto);

        List<AddressDto> result = addressService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(addressDto, result.get(0));

        verify(addressRepository).findAll();
        verify(addressMapper).addressEntityToDto(any(AddressEntity.class));
    }

    @Test
    void findAddressById() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(addressEntity));
        when(addressMapper.addressEntityToDto(any(AddressEntity.class))).thenReturn(addressDto);

        AddressDto result = addressService.findById(1L);

        assertNotNull(result);
        assertEquals(addressDto, result);

        verify(addressRepository).findById(anyLong());
        verify(addressMapper).addressEntityToDto(any(AddressEntity.class));
    }

    @Test
    void createAddress() {
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);
        when(addressMapper.addressDtoToEntity(any(AddressDto.class))).thenReturn(addressEntity);
        when(addressMapper.addressEntityToDto(any(AddressEntity.class))).thenReturn(addressDto);

        AddressDto result = addressService.createAddress(addressDto);

        assertNotNull(result);
        assertEquals(addressDto, result);

        verify(addressRepository).save(any(AddressEntity.class));
        verify(addressMapper).addressDtoToEntity(any(AddressDto.class));
        verify(addressMapper).addressEntityToDto(any(AddressEntity.class));
    }

    @Test
    void updateAddress() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(addressEntity));
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);

        AddressDto updatedAddressDto = new AddressDto();
        updatedAddressDto.setId(1L);
        updatedAddressDto.setStreet("Updated Street");
        updatedAddressDto.setCity("Updated City");
        updatedAddressDto.setState("Updated State");
        updatedAddressDto.setZip("Updated Zip");

        when(addressMapper.addressEntityToDto(eq(addressEntity))).thenReturn(updatedAddressDto);
        AddressDto result = addressService.updateAddress(1L, updatedAddressDto);

        assertNotNull(result);
        assertEquals(updatedAddressDto.getStreet(), result.getStreet());
        assertEquals(updatedAddressDto.getCity(), result.getCity());
        assertEquals(updatedAddressDto.getState(), result.getState());
        assertEquals(updatedAddressDto.getZip(), result.getZip());

        verify(addressRepository).findById(anyLong());
        verify(addressRepository).save(any(AddressEntity.class));
        verify(addressMapper).addressEntityToDto(any(AddressEntity.class));
    }

    @Test
    void deleteAddress() {
        Long addressId = 1L;
        when(addressRepository.existsById(addressId)).thenReturn(true);
        doNothing().when(addressRepository).deleteById(anyLong());

        addressService.deleteAddress(addressId);

        verify(addressRepository).existsById(addressId);
        verify(addressRepository).deleteById(addressId);
    }
}