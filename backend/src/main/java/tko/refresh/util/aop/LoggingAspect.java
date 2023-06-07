package tko.refresh.util.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* tko.refresh.controller.*.*(..))")
    public void logControllerMethodCall(JoinPoint joinPoint) {
        logger.info("Calling controller method: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "execution(* tko.refresh.service.*.*(..))", returning = "result")
    public void logServiceMethodReturn(JoinPoint joinPoint, Object result) {
        logger.info("Returning from service method: " + joinPoint.getSignature().toShortString());
        logger.debug("Returned value: " + result);
    }

    @AfterThrowing(pointcut = "execution(* tko.refresh.service.*.*(..))", throwing = "exception")
    public void logServiceMethodException(JoinPoint joinPoint, Exception exception) {
        logger.error("Exception in service method: " + joinPoint.getSignature().toShortString());
        logger.error("Exception details: ", exception);
    }
}