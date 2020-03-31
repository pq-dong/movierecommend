package pqdong.movie.recommend.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class CtrlLogAdviceAop {


    @Around("@annotation(requestMapping)")
    public Object requestMappingAdvice(ProceedingJoinPoint thisJoinPoint, RequestMapping requestMapping) throws Throwable {
        String path = requestMapping.value().length == 0 ? "" : requestMapping.value()[0];
        return process(thisJoinPoint, path);
    }

    @Around("@annotation(getMapping)")
    public Object getMappingAdvice(ProceedingJoinPoint thisJoinPoint, GetMapping getMapping) throws Throwable {
        String path = getMapping.value().length == 0 ? "" : getMapping.value()[0];
        return process(thisJoinPoint, path);
    }

    @Around("@annotation(postMapping)")
    public Object postMappingAdvice(ProceedingJoinPoint thisJoinPoint, PostMapping postMapping) throws Throwable {
        String path = postMapping.value().length == 0 ? "" : postMapping.value()[0];
        return process(thisJoinPoint, path);
    }

    @Around("@annotation(putMapping)")
    public Object putMappingAdvice(ProceedingJoinPoint thisJoinPoint, PutMapping putMapping) throws Throwable {
        String path = putMapping.value().length == 0 ? "" : putMapping.value()[0];
        return process(thisJoinPoint, path);
    }

    @Around("@annotation(deleteMapping)")
    public Object deleteMappingAdvice(ProceedingJoinPoint thisJoinPoint, DeleteMapping deleteMapping) throws Throwable {
        String path = deleteMapping.value().length == 0 ? "" : deleteMapping.value()[0];
        return process(thisJoinPoint, path);
    }


    private Object process(ProceedingJoinPoint pjp, String path) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        long endTime = System.currentTimeMillis();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        String logName = methodSignature.getMethod().getName() + "(" + path + ")";
        log.info(logName + ".time=" + (endTime - startTime));
        log.info(logName + " Args: " + Arrays.toString(pjp.getArgs()));
        return result;
    }
}
