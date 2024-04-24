package by.artsem.footballrestapi.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataErrorResponse {
    private String message;
    private long timestamp;
}
