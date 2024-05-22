package by.artsem.footballrestapi.controllers;


import by.artsem.footballrestapi.dto.BrandDTO;
import by.artsem.footballrestapi.dto.mappers.BrandMapper;
import by.artsem.footballrestapi.exceptions.DataNotCreatedException;
import by.artsem.footballrestapi.models.Brand;
import by.artsem.footballrestapi.services.BrandService;
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
@RequestMapping("/brand")
public class BrandController {

    private BrandService brandService;
    private BrandMapper brandMapper;

    @GetMapping("/get-all")
    public ResponseEntity<List<BrandDTO>> findAll() {
        return ResponseEntity.ok(
                brandService.getBrands().stream().map(brandMapper::mapToDTO).collect(Collectors.toList())
        );
    }

    @GetMapping("/get-id/{id}")
    public ResponseEntity<BrandDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(brandMapper.mapToDTO(brandService.findById(id)));
    }

    @GetMapping("/get-name/{name}")
    public ResponseEntity<BrandDTO> findById(@PathVariable("name") String name) {
        return ResponseEntity.ok(brandMapper.mapToDTO(brandService.findByName(name)));
    }

    @GetMapping("/get-all-id")
    public ResponseEntity<List<Brand>> getAllWithId() {
        return ResponseEntity.ok(brandService.getBrands());
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid BrandDTO brandDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(ValidationErrMessage.createValidationErrMessage(bindingResult));
        }
        brandService.saveBrand(brandMapper.mapFromDTO(brandDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}/update-name")
    private ResponseEntity<HttpStatus> update(@PathVariable("id") Long id,
                                              @RequestBody @Valid BrandDTO brandDto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(ValidationErrMessage.createValidationErrMessage(bindingResult));
        }
        brandService.updateName(id, brandMapper.mapFromDTO(brandDto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}/add-player")
    private ResponseEntity<HttpStatus> addPlayer(@PathVariable("id") Long id,
                                                 @RequestBody String playerName,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(ValidationErrMessage.createValidationErrMessage(bindingResult));
        }
        brandService.addExistPlayer(id, playerName);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/remove")
    private ResponseEntity<HttpStatus> remove(@PathVariable("id") Long id) {
        brandService.removeBrand(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
