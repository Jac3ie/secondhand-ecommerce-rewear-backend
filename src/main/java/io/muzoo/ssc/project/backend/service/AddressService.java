package io.muzoo.ssc.project.backend.service;

import io.muzoo.ssc.project.backend.address.Address;
import io.muzoo.ssc.project.backend.address.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address saveUserAddress(Address address) {
        // Delete existing address if present
        addressRepository.findByUserId(address.getUser().getId())
                .ifPresent(existing -> addressRepository.delete(existing));

        return addressRepository.save(address);
    }

    public Optional<Address> getUserAddress(Long userId) {
        return addressRepository.findByUserId(userId);
    }
}
