package by.artsem.footballrestapi.security.model.dto;

import by.artsem.footballrestapi.security.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Role role;
}
