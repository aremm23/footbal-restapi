package by.artsem.footballrestapi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class PlayerDTO {
    @NotEmpty(message = "Player name should not be empty")
    private String name;

    @NotEmpty(message = "Player price should not be empty")
    private Integer price;

    private String clubName;

    private List<String> brands;
}
