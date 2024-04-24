package by.artsem.footballrestapi.dto;

import jakarta.validation.constraints.NotEmpty;

public class MyUserDTO {
    @NotEmpty(message = "Password ought to be not empty")
    private String password;
    @NotEmpty(message = "Username ought to be not empty")
    private String username;
    @NotEmpty(message = "Role ought to be not empty")
    private String role;
}
