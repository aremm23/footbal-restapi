package by.artsem.footballrestapi.controllers;

import by.artsem.footballrestapi.dto.PlayerDTO;
import by.artsem.footballrestapi.dto.mappers.PlayerMapper;
import by.artsem.footballrestapi.models.Player;
import by.artsem.footballrestapi.repository.PlayerRepository;
import by.artsem.footballrestapi.services.PlayerService;
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
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/player")
public class PlayerController {
    private PlayerService playerService;
    private PlayerMapper playerMapper;

    @GetMapping("/get-name/{name}")
    public ResponseEntity<PlayerDTO> findById(@PathVariable("name") String name) {
        return ResponseEntity.ok(playerMapper.mapToDTO(playerService.findByName(name)));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PlayerDTO>> findAll() {
        return ResponseEntity.ok(
                playerService.getPlayers().stream().map(playerMapper::mapToDTO).collect(Collectors.toList())
        );
    }

    @GetMapping("/get-id/{id}")
    public ResponseEntity<PlayerDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(playerMapper.mapToDTO(playerService.findById(id)));
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Player player, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(createErrMessage(bindingResult));
        }
        playerService.savePlayer(player);
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
