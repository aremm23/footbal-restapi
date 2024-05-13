package by.artsem.footballrestapi.controllers;

import by.artsem.footballrestapi.services.RequestCounterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UtilController {
    private RequestCounterService requestCounterService;
    @GetMapping("/requests")
    public ResponseEntity<Integer> getNumberOfRequests() {
        return ResponseEntity.ok(requestCounterService.getNumberOfRequests());
    }
}
