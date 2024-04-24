package by.artsem.footballrestapi.controllers;

import by.artsem.footballrestapi.dto.MyUserDTO;
import by.artsem.footballrestapi.models.MyUser;
import by.artsem.footballrestapi.models.Player;
import by.artsem.footballrestapi.services.MyUserService;
import by.artsem.footballrestapi.util.DataErrorResponse;
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

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class MyUserController {
    private MyUserService myUserService;

    @GetMapping("/get-username/{username}")
    public MyUser findById(@PathVariable("username") String username) {
        return myUserService.findByName(username);
    }

    @GetMapping("/get-all")
    public List<MyUser> findAll() {
        return myUserService.findUsers();
    }

    @GetMapping("/get-id/{id}")
    public MyUser findById(@PathVariable("id") Long id) {
        return myUserService.findById(id);
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MyUser user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(createErrMessage(bindingResult));
        }
        myUserService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<DataErrorResponse> handlerException(DataNotFoundedException e) {
        DataErrorResponse dataErrorResponse = new DataErrorResponse(
                "user not found",
                System.currentTimeMillis());
        return new ResponseEntity<>(dataErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<DataErrorResponse> handlerException(DataNotCreatedException e) {
        DataErrorResponse dataErrorResponse = new DataErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(dataErrorResponse, HttpStatus.BAD_REQUEST);
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
