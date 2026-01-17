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

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // keep the controller class clean, for controller layer:
    // only do HTTP mapping and return response got from service layer, put business logics inside of service
    @GetMapping
    public ResponseEntity<Address> getAddress(@AuthenticationPrincipal UserDetails userDetails) {
        return addressService.getUserAddressFromName(userDetails.getUsername())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveAddress(
            @RequestBody AddressDTO request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Address savedAddress = addressService.saveUserAddressFromName(userDetails.getUsername(), request);
        return ResponseEntity.ok(savedAddress);
    }
}
