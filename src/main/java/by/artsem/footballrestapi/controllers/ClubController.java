package by.artsem.footballrestapi.controllers;

import by.artsem.footballrestapi.dto.ClubDTO;
import by.artsem.footballrestapi.dto.mappers.ClubMapper;
import by.artsem.footballrestapi.exceptions.DataNotCreatedException;
import by.artsem.footballrestapi.models.Club;
import by.artsem.footballrestapi.services.ClubService;
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
@RequestMapping("/club")
public class ClubController {

    private ClubService clubService;

    private ClubMapper clubMapper;

    @GetMapping("/get-name/{name}")
    public ResponseEntity<ClubDTO> findById(@PathVariable("name") String name) {
        return ResponseEntity.ok(clubMapper.mapToDTO(clubService.findByName(name)));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ClubDTO>> findAll() {
        return ResponseEntity.ok(clubService.getClubs().stream().map(clubMapper::mapToDTO).collect(Collectors.toList()));
    }

    @GetMapping("/get-id/{id}")
    public ResponseEntity<ClubDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clubMapper.mapToDTO(clubService.findById(id)));
    }

    @GetMapping("/get-all-id")
    public ResponseEntity<List<Club>> getAllWithId() {
        return ResponseEntity.ok(clubService.getClubs());
    }


    @PostMapping("/new")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid ClubDTO clubDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(createErrMessage(bindingResult));
        }
        clubService.saveClub(clubMapper.mapFromDTO(clubDto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}/add-player")
    public ResponseEntity<HttpStatus> addPlayer(@PathVariable("id") Long id,
                                                @RequestBody String playerName,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(createErrMessage(bindingResult));
        }
        clubService.addExistPlayer(id, playerName);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<HttpStatus> remove(@PathVariable("id") Long id) {
        clubService.removeClub(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/get-players-100")
    public ResponseEntity<List<String>> getExpensivePlayers() {
        return ResponseEntity.ok(clubService.getClubWithExpensivePlayers());
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
