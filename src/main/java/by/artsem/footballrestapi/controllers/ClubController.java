package by.artsem.footballrestapi.controllers;

import by.artsem.footballrestapi.models.Club;
import by.artsem.footballrestapi.services.ClubService;
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
@RequestMapping("/club")
public class ClubController {

    private ClubService clubService;

    @GetMapping("/get-name/{name}")
    public Club findById(@PathVariable("name") String name) {
        Club club = clubService.findByName(name);
        return club;
    }

    @GetMapping("/get-all")
    public List<Club> test() {
        return clubService.getClubs();
    }

    @GetMapping("/get-id/{id}")
    public Club findById(@PathVariable("id") Long id) {
        return clubService.findById(id);
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Club club, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(createErrMessage(bindingResult));
        }
        clubService.saveClub(club);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<DataErrorResponse> handlerException(DataNotFoundedException e) {
        DataErrorResponse dataErrorResponse = new DataErrorResponse(
                "club not found",
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
