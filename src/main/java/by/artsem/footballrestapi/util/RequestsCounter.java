package by.artsem.footballrestapi.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RequestsCounter {
    private final AtomicInteger numberOfRequests = new AtomicInteger();

    public Integer getNumberOfRequests() {
        return numberOfRequests.get();
    }

    public void enlargeNumber() {
        numberOfRequests.incrementAndGet();
    }
}
