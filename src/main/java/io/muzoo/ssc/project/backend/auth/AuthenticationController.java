package io.muzoo.ssc.project.backend.auth;
import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.SimpleResponseDTO;
import io.muzoo.ssc.project.backend.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;


    @GetMapping("/api/test")
    public String test() {
        return "If return this, it means login is successful becuz we did not set to permit this path.";
    }

    @PostMapping("/api/login")
    public SimpleResponseDTO login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try{
            // fixing sequential login bug
            // check if there is a user logged in, if so log that user out first
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof org.springframework.security.core.userdetails.User) {
                request.logout();
            }

            request.login(username, password);
            return SimpleResponseDTO
                    .builder()
                    .success(true)
                    .message("You are successfully log in")
                    .build();
        } catch (ServletException e){
            return SimpleResponseDTO
                    .builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/api/logout") // match the frontend post request
    public SimpleResponseDTO logout(HttpServletRequest request){
        try {
            request.logout();
            return SimpleResponseDTO
                    .builder()
                    .success(true)
                    .message("You are successfully log out")
                    .build();
        } catch (ServletException e) {
            return SimpleResponseDTO
                    .builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }
    // Registration endpoint
    @PostMapping("/register")
    public SimpleResponseDTO register(@RequestBody User user) {
        boolean isRegistered = userService.registerUser(user);
        if (isRegistered) {
            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("Registration successful")
                    .build();
        } else {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("User already exists or registration failed")
                    .build();
        }
    }
}
