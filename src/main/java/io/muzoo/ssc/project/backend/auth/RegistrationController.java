package io.muzoo.ssc.project.backend.auth;

import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.UserRepository;
import io.muzoo.ssc.project.backend.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/api/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        // Check if username already exists
        if (userRepository.findFirstByUsername(registerRequest.getUsername()) != null) {
            return "Username is already taken!";
        }

        // Create a new user entity
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));  // Encrypt the password
        user.setRole(registerRequest.getRole() != null ? registerRequest.getRole() : "USER");  // Default role if not provided

        // Save the user
        userRepository.save(user);

        return "Registration successful!";
    }
}
