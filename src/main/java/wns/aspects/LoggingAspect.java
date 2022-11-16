package wns.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @AfterThrowing(value = "@annotation(ToLog)", throwing = "ex")
    public void log(JoinPoint joinPoint, Exception ex) throws Throwable {
        logger.error("\nОшибка " + joinPoint.toShortString()+ "\nтип ошибки " + ex.toString());
    }
}
