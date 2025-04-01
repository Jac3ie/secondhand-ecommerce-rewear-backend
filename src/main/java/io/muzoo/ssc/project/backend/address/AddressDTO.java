package io.muzoo.ssc.project.backend.address;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;


@Getter
@Setter
public class AddressDTO {

    @NotBlank(message = "House number is required")
    @Size(max = 50, message = "House number must be less than 50 characters")
    private String houseNo;

    @NotBlank(message = "Province is required")
    @Size(max = 100, message = "Province must be less than 100 characters")
    private String province;

    @NotBlank(message = "Postal code is required")
    @Pattern(regexp = "^\\d{5}$", message = "Postal code must be 5 digits")
    private String postalCode;
}
