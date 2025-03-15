package io.muzoo.ssc.project.backend;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String role;
    
}
