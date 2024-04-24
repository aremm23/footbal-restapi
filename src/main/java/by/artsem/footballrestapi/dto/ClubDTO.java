package by.artsem.footballrestapi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ClubDTO {

    @NotEmpty(message = "Club name should not be empty")
    private String name;

    private List<String> playersNames;
}
