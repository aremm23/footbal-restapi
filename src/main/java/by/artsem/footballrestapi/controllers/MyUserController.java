package by.artsem.footballrestapi.controllers;

import by.artsem.footballrestapi.dto.MyUserRequestDTO;
import by.artsem.footballrestapi.dto.MyUserResponseDTO;
import by.artsem.footballrestapi.dto.mappers.MyUserMapper;
import by.artsem.footballrestapi.models.MyUser;
import by.artsem.footballrestapi.services.MyUserService;
import by.artsem.footballrestapi.util.ErrorResponse;
import by.artsem.footballrestapi.util.DataNotCreatedException;
import by.artsem.footballrestapi.util.DataNotFoundedException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class MyUserController {
    private MyUserService myUserService;
    private MyUserMapper myUserMapper;

    @GetMapping("/get-username/{username}")
    public ResponseEntity<MyUserResponseDTO> findById(@PathVariable("username") String username) {
        MyUser myUser = myUserService.findByName(username);
        return ResponseEntity.ok(myUserMapper.mapToDTO(myUser));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<MyUserResponseDTO>> findAll() {
        List<MyUser> users = myUserService.findUsers();
        List<MyUserResponseDTO> dtos = users.stream()
                .map(myUserMapper::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/get-id/{id}")
    public ResponseEntity<MyUser> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(myUserService.findById(id));
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MyUserRequestDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(createErrMessage(bindingResult));
        }
        myUserService.saveUser(myUserMapper.mapFromDTO(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private String createErrMessage(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";");
        }
        return errorMsg.toString();
    }
}
