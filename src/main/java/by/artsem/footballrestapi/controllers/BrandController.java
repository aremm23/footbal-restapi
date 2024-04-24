package by.artsem.footballrestapi.controllers;


import by.artsem.footballrestapi.models.Brand;
import by.artsem.footballrestapi.services.BrandService;
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
@RequestMapping("/brand")
public class BrandController {

    private BrandService brandService;

    @GetMapping("/get-all")
    public List<Brand> test() {
        return brandService.getBrands();
    }

    @GetMapping("/get-id/{id}")
    public Brand findById(@PathVariable("id") Long id) {
        return brandService.findById(id);
    }

    @GetMapping("/get-name/{name}")
    public Brand findById(@PathVariable("name") String name) {
        return brandService.findByName(name);
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Brand brand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotCreatedException(createErrMessage(bindingResult));
        }
        brandService.saveBrand(brand);
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


}
