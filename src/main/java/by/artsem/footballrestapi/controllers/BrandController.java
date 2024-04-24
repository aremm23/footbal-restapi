package by.artsem.footballrestapi.controllers;


import by.artsem.footballrestapi.dto.BrandDTO;
import by.artsem.footballrestapi.dto.mappers.BrandMapper;
import by.artsem.footballrestapi.services.BrandService;
import by.artsem.footballrestapi.util.DataNotCreatedException;
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

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid BrandDTO brandDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(createErrMessage(bindingResult));
        }
        brandService.saveBrand(brandMapper.mapFromDTO(brandDto));
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
