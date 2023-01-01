package seb4141preproject.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class SimpleLoggingAop {

    @Pointcut("execution(public * *(..))")
    private void anyPublicOperation() {}

    @Pointcut("within(seb4141preproject.answers.service..*)")
    private void inAnswers() {}
    @Pointcut("within(seb4141preproject.members.service..*)")
    private void inMembers() {}
    @Pointcut("within(seb4141preproject.questions.service..*)")
    private void inQuestions() {}
    @Pointcut("within(seb4141preproject.security.auth.service..*)")
    private void inSecurity() {}

    @Pointcut("anyPublicOperation() && ( inAnswers() || inMembers() || inQuestions() || inSecurity() )")
    private void cut() {}

    @Before("cut()")
    public void beforeParameterLogging(JoinPoint joinPoint) {
        Method method = getMethod(joinPoint);
        log.info("======= method name : {} =======", method.getName());

        Object[] args = joinPoint.getArgs();
        if (args.length == 0) {
            log.info("no parameter");
        } else {
            for (Object arg : args) {
                log.info("parameter type : {}", arg.getClass().getSimpleName());
                log.info("parameter value : {}", arg);
            }
        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterReturnLogging(JoinPoint joinPoint, Object returnObj) {
        Method method = getMethod(joinPoint);
        log.info("======= method name : {} =======", method.getName());

        log.info("return type : {}", returnObj.getClass().getSimpleName());
        log.info("return value : {}", returnObj);
    }

    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
