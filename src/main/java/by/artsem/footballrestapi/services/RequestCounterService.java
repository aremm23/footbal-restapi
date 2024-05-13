package by.artsem.footballrestapi.services;

import by.artsem.footballrestapi.util.RequestsCounter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RequestCounterService {
    private RequestsCounter requestsCounter;

    public Integer getNumberOfRequests() {
        return requestsCounter.getNumberOfRequests();
    }
}
