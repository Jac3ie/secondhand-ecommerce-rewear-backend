package io.muzoo.ssc.project.backend.Role;

import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Grant admin role to a user
    @PostMapping("/users/{userId}/grant-admin")
    public ResponseEntity<String> grantAdminRole(@PathVariable long userId) {
        User updatedUser = userService.grantAdminRole(userId);
        if (updatedUser != null) {
            return ResponseEntity.ok("User role updated to admin");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }
}
