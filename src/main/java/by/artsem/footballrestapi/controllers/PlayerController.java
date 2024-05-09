package by.artsem.footballrestapi.controllers;

import by.artsem.footballrestapi.dto.PlayerDTO;
import by.artsem.footballrestapi.dto.mappers.PlayerMapper;
import by.artsem.footballrestapi.exceptions.DataNotCreatedException;
import by.artsem.footballrestapi.models.Player;
import by.artsem.footballrestapi.services.PlayerService;
import by.artsem.footballrestapi.util.ValidationErrMessage;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/get-all-id")
    public ResponseEntity<List<Player>> getAllWithId() {
        return ResponseEntity.ok(playerService.getPlayers());
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PlayerDTO playerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(ValidationErrMessage.createValidationErrMessage(bindingResult));
        }
        playerService.savePlayer(playerMapper.mapFromDTO(playerDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    private ResponseEntity<HttpStatus> update(@PathVariable("id") Long id,
                                              @RequestBody @Valid PlayerDTO playerDto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(ValidationErrMessage.createValidationErrMessage(bindingResult));
        }
        playerService.update(id, playerMapper.mapFromDTO(playerDto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}/add-club")
    private ResponseEntity<HttpStatus> addClub(@PathVariable("id") Long id,
                                               @RequestBody String clubName,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(ValidationErrMessage.createValidationErrMessage(bindingResult));
        }
        playerService.addExistClub(id, clubName);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}/add-brand")
    private ResponseEntity<HttpStatus> addBrand(@PathVariable("id") Long id,
                                                @RequestBody String brandName,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(ValidationErrMessage.createValidationErrMessage(bindingResult));
        }
        playerService.addExistBrand(id, brandName);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/remove")
    private ResponseEntity<HttpStatus> remove(@PathVariable("id") Long id) {
        playerService.removePlayer(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
