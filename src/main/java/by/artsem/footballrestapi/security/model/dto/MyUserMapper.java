package by.artsem.footballrestapi.security.model.dto;

import by.artsem.footballrestapi.exceptions.DataNotFoundedException;
import by.artsem.footballrestapi.security.model.MyUser;
import by.artsem.footballrestapi.security.model.Role;
import org.springframework.stereotype.Component;

@Component
public class MyUserMapper {
    public MyUserResponseDTO mapToDTO(MyUser user) {
        MyUserResponseDTO userResponseDTO = new MyUserResponseDTO();
        userResponseDTO.setRole(user.getRole().toString());
        userResponseDTO.setUsername(user.getUsername());
        return userResponseDTO;
    }

    public MyUser mapFromDTO(MyUserRequestDTO userRequestDTO) {
        MyUser user = new MyUser();
        user.setUsername(userRequestDTO.getUsername());
        Role role = Role.parseStringToRole(userRequestDTO.getRole()).orElseThrow(() ->
                new DataNotFoundedException("Role not founded")
        );
        user.setRole(role);
        user.setPassword(userRequestDTO.getPassword());
        return user;
    }
}
