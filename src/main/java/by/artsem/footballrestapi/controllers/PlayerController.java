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
@RequestMapping("/api/v1/players")
public class PlayerController {
    private PlayerService playerService;
    private PlayerMapper playerMapper;

    @GetMapping("/name/{name}")
    public ResponseEntity<PlayerDTO> findByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(playerMapper.mapToDTO(playerService.findByName(name)));
    }

    @GetMapping("")
    public ResponseEntity<List<PlayerDTO>> findAll() {
        return ResponseEntity.ok(
                playerService.getPlayers().stream().map(playerMapper::mapToDTO).collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(playerMapper.mapToDTO(playerService.findById(id)));
    }

    @GetMapping("/with-id")
    public ResponseEntity<List<Player>> getAllWithId() {
        return ResponseEntity.ok(playerService.getPlayers());
    }

    @PostMapping("")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PlayerDTO playerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(ValidationErrMessage.createValidationErrMessage(bindingResult));
        }
        playerService.savePlayer(playerMapper.mapFromDTO(playerDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id,
                                              @RequestBody @Valid PlayerDTO playerDto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(ValidationErrMessage.createValidationErrMessage(bindingResult));
        }
        playerService.update(id, playerMapper.mapFromDTO(playerDto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/club")
    public ResponseEntity<HttpStatus> addClub(@PathVariable("id") Long id,
                                               @RequestBody String clubName,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(ValidationErrMessage.createValidationErrMessage(bindingResult));
        }
        playerService.addExistClub(id, clubName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/brand")
    public ResponseEntity<HttpStatus> addBrand(@PathVariable("id") Long id,
                                                @RequestBody String brandName,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(ValidationErrMessage.createValidationErrMessage(bindingResult));
        }
        playerService.addExistBrand(id, brandName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> remove(@PathVariable("id") Long id) {
        playerService.removePlayer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
