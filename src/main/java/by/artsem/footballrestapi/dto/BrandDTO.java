package by.artsem.footballrestapi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class BrandDTO {
    @NotEmpty(message = "Brand name should not be empty")
    private String name;
    private List<String> players;
}
