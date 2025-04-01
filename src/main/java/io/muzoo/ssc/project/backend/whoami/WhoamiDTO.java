package io.muzoo.ssc.project.backend.whoami;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WhoamiDTO {
    private Long id; // Include user ID

    private boolean loggedIn = false;

    private boolean isRegistered;  // Register field to indicate if the user is registered

    private String username;

    private String name;

    private String role;


}
