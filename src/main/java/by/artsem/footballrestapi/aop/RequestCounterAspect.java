package by.artsem.footballrestapi.aop;

import by.artsem.footballrestapi.util.RequestsCounter;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class RequestCounterAspect {
    private RequestsCounter requestsCounter;

    @Pointcut("execution(public * by.artsem.footballrestapi.controllers.*.*(..))")
    public static void counterPointCut() {
    }

    @Before(value = "counterPointCut()")
    public void logAtTheBeginning() {
        requestsCounter.enlargeNumber();
    }
}
