package by.artsem.footballrestapi.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Pointcut("execution(public * by.artsem.footballrestapi.services.*.*(*))")
    public static void logPointCut(){}

    @Before(value = "logPointCut()")
    public void logAtTheBeginning(JoinPoint joinPoint) {
        log.info("Started: {}", joinPoint.getSignature());
    }

    @AfterThrowing(value = "logPointCut()", throwing = "thrVal")
    public void logAfterThrow(JoinPoint joinPoint, Throwable thrVal) {
        log.error("Exception thrown in {}. Exception: {} ", joinPoint.getSignature(), thrVal.toString());
    }
    @AfterReturning(value = "logPointCut()", returning = "retVal")
    public void logAfterReturn(JoinPoint joinPoint, Object retVal) {
        if(retVal == null) {
            log.info("Returned void from {}", joinPoint.getSignature());
        }
        else {
            log.info("Returned from {}, value {}", joinPoint.getSignature() ,retVal);
        }
    }
}
