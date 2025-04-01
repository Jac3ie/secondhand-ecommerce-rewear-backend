package io.muzoo.ssc.project.backend.address;

import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.UserRepository;
import io.muzoo.ssc.project.backend.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;
    private final UserRepository userRepository;

    public AddressController(AddressService addressService, UserRepository userRepository) {
        this.addressService = addressService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<Address> getAddress(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findFirstByUsername(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return addressService.getUserAddress(user.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveAddress(
            @RequestBody AddressDTO request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userRepository.findFirstByUsername(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        Address address = new Address();
        address.setUser(user);  // Set the actual User entity
        address.setHouseNo(request.getHouseNo());
        address.setProvince(request.getProvince());
        address.setPostalCode(request.getPostalCode());

        Address savedAddress = addressService.saveUserAddress(address);
        return ResponseEntity.ok(savedAddress);
    }
}
