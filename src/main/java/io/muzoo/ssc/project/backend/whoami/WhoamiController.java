package io.muzoo.ssc.project.backend.whoami;

import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller to retrieve the currently logged-in user.
 */
@RestController
public class WhoamiController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Make sure that all API paths begin with /api. This will be useful when we do proxy.
     */
    @GetMapping("/api/whoami")
    public WhoamiDTO whoami() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof org.springframework.security.core.userdetails.User) {

                // User is logged in
                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;
                User u = userRepository.findFirstByUsername(user.getUsername());

                // Check if the user is registered (i.e., exists in the database)
                boolean isRegistered = u != null;

                return WhoamiDTO.builder()
                        .loggedIn(true)
                        .name(u != null ? u.getUsername() : null)  // Use username if available
                        .role(u != null ? u.getRole() : "")  // Default to empty if user is null
                        .username(u != null ? u.getUsername() : "")
                        .isRegistered(isRegistered)  // Set the isRegistered field
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return WhoamiDTO.builder()
                .loggedIn(false)
                .isRegistered(false)
                .build();
    }
}
