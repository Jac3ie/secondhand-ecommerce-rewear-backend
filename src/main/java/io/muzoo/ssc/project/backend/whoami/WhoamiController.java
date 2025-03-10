package io.muzoo.ssc.project.backend.whoami;

import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller to retrieve currrently logged-in user.
 */
@RestController
public class WhoamiController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Make sure that all API path begins with /api. This will be useful when we do proxy
     */
    @GetMapping("/api/whoami")
    public WhoamiDTO whoami() {
        // Put try-with clause around the statement becuz we use nested dot notation which
        // could raise a NullPointException
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && principal instanceof org.springframework.security.core.userdetails.User) {

                // user is logged in
                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;
                User u = userRepository.findFirstByUsername(user.getUsername());

                return WhoamiDTO.builder()
                        .loggedIn(true)
                        .name(u.getUsername())  // TODO: Example doesn't have a name field but if project needs, we can add here
                        .role(u.getRole())
                        .username(u.getUsername())
                        .build();
            }
        } catch (Exception e){
        }
        // user failed to log in
        return WhoamiDTO.builder()
                .loggedIn(false)
                .build();

    }
}
