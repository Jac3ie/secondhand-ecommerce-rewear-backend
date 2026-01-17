package io.muzoo.ssc.project.backend.service;

import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.UserRepository;
import io.muzoo.ssc.project.backend.address.Address;
import io.muzoo.ssc.project.backend.address.AddressDTO;
import io.muzoo.ssc.project.backend.address.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public Address saveUserAddressFromName(String userName, AddressDTO addrRequest) {
        User user = userRepository.findFirstByUsername(userName);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // if have addr then update, if not then create
        Address addr = addressRepository.findByUserId(user.getId())
                        .orElseGet(Address::new);
        addr.setUser(user);
        addr.setProvince(addrRequest.getProvince());
        addr.setPostalCode(addrRequest.getPostalCode());
        addr.setHouseNo(addrRequest.getHouseNo());

        return addressRepository.save(addr);
    }

    public Optional<Address> getUserAddressFromName(String userName) {
        User user = userRepository.findFirstByUsername(userName);
        if (user == null) return Optional.empty();
        return addressRepository.findByUserId(user.getId());
    }
}
