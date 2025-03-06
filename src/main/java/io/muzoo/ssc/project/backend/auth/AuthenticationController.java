package io.muzoo.ssc.project.backend.auth;

import io.muzoo.ssc.project.backend.SimpleResponseDTO;
import io.muzoo.ssc.project.backend.util.AjaxUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @GetMapping("/api/test")
    public String test() {
        return "If return this, it means login is successful becuz we did not set to permit this path.";
    }

    @PostMapping("/api/login")
    public SimpleResponseDTO login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try{
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
                    .message("Failed to log in, incorrect username or password")
                    .build();
        }
    }

    @GetMapping("/api/logout")
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
                    .message("Failed to log out")
                    .build();
        }
    }

}
