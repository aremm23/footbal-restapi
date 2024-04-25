package by.artsem.footballrestapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class PlayerDTO {
    @NotEmpty(message = "Player name should not be empty")
    private String name;

    @Min(value = 0, message = "Price can't be less then 0 millions euro")
    @Max(value = 500, message = "Price can't be more then 500 millions euro")
    private Integer price;

    private String club;

    private List<String> brands;
}
