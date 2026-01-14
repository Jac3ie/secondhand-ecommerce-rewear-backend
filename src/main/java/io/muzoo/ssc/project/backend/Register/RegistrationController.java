package io.muzoo.ssc.project.backend.Register;

import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/api/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequestDTO registerRequest) {
        Map<String, Object> response = new HashMap<>();

        // Check if username already exists
        if (userRepository.findFirstByUsername(registerRequest.getUsername()) != null) {
            response.put("success", false);
            response.put("message", "Username is already taken!");
            return ResponseEntity.badRequest().body(response);
        }

        // Create a new user entity
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));  // Encrypt the password
        user.setRole("buyer"); // Assign a default role since does not have admin register
        userRepository.save(user);

        response.put("success", true);
        response.put("message", "Registration successful!");
        return ResponseEntity.ok(response);
    }
}
