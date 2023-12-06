package com.example.cs393backend;

import com.example.cs393backend.entity.AddressEntity;
import com.example.cs393backend.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;

    private AddressEntity createAddress() {
        // Create an address
        AddressEntity address = new AddressEntity();
        address.setStreet("Test street");
        address.setCity("Test city");
        address.setState("Test state");
        address.setZip("12345");

        return address;
    }

    @Test
    public void testCreateAddress() {
        // Given
        AddressEntity address = createAddress();

        // When
        AddressEntity savedAddress = addressRepository.save(address);

        // Then
        assertNotNull(savedAddress);
        assertNotNull(savedAddress.getId());
    }

    @Test
    public void testReadAddress() {
        // Given
        AddressEntity address = createAddress();
        AddressEntity savedAddress = addressRepository.save(address);

        // When
        Optional<AddressEntity> foundAddress = addressRepository.findById(savedAddress.getId());

        // Then
        assertTrue(foundAddress.isPresent());
        assertEquals(savedAddress.getId(), foundAddress.get().getId());
        assertEquals(savedAddress.getStreet(), foundAddress.get().getStreet());
        assertEquals(savedAddress.getCity(), foundAddress.get().getCity());
        assertEquals(savedAddress.getState(), foundAddress.get().getState());
        assertEquals(savedAddress.getZip(), foundAddress.get().getZip());
    }

    @Test
    public void testUpdateAddress() {
        // Given
        AddressEntity address = createAddress();
        AddressEntity savedAddress = addressRepository.save(address);
        savedAddress.setCity("Updated city");
        savedAddress.setState("Updated state");
        savedAddress.setZip("67890");

        // When
        AddressEntity updatedAddress = addressRepository.save(savedAddress);

        // Then
        assertNotNull(updatedAddress);
        assertEquals("Updated city", updatedAddress.getCity());
        assertEquals("Updated state", updatedAddress.getState());
        assertEquals("67890", updatedAddress.getZip());
    }

    @Test
    public void testDeleteAddress() {
        // Given
        AddressEntity address = createAddress();
        AddressEntity savedAddress = addressRepository.save(address);

        // When
        addressRepository.deleteById(savedAddress.getId());

        // Then
        Optional<AddressEntity> deletedAddress = addressRepository.findById(savedAddress.getId());
        assertFalse(deletedAddress.isPresent());
    }
}
