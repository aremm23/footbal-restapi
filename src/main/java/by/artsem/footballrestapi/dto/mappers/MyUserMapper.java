package by.artsem.footballrestapi.dto.mappers;

import by.artsem.footballrestapi.dto.MyUserRequestDTO;
import by.artsem.footballrestapi.dto.MyUserResponseDTO;
import by.artsem.footballrestapi.models.MyUser;
import org.springframework.stereotype.Component;

@Component
public class MyUserMapper {
    public MyUserResponseDTO mapToDTO(MyUser user) {
        MyUserResponseDTO userResponseDTO = new MyUserResponseDTO();
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setUsername(user.getUsername());
        return userResponseDTO;
    }

    public MyUser mapFromDTO(MyUserRequestDTO userRequestDTO) {
        MyUser user = new MyUser();
        user.setUsername(userRequestDTO.getUsername());
        user.setRole(userRequestDTO.getRole());
        user.setPassword(userRequestDTO.getPassword());
        return user;
    }
}
