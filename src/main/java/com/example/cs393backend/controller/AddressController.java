package com.example.cs393backend.controller;

import com.example.cs393backend.dto.AddressDto;
import com.example.cs393backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping
    public AddressDto createAddress(@RequestBody AddressDto addressDto) {
        return addressService.createAddress(addressDto);
    }

    @GetMapping("/{id}")
    public AddressDto getAddress(@PathVariable Long id) {
        return addressService.getAddress(id);
    }

    @PutMapping
    public AddressDto updateAddress(@RequestBody AddressDto addressDto) {
        return addressService.updateAddress(addressDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }
}
