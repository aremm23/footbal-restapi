package by.artsem.footballrestapi.security.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MyUserRequestDTO {
    @NotEmpty(message = "Password ought to be not empty")
    private String password;
    @NotEmpty(message = "Username ought to be not empty")
    private String username;
    @NotEmpty(message = "Role ought to be not empty")
    private String role;
}
