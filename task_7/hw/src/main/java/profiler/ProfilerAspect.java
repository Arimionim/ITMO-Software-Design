package profiler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ProfilerAspect {
    final static ProfilerStatistics profiler = ProfilerStatistics.Companion.getInstance();
    static String profiledPackage;

    @Around("execution(* *(..))")
    public Object aroundProfile(ProceedingJoinPoint joinPoint) throws Throwable {
        final Signature signature = joinPoint.getSignature();

        if (!signature.getDeclaringTypeName().startsWith(profiledPackage)) {
            return joinPoint.proceed();
        }

        long startTime = System.currentTimeMillis();
        profiler.enterMethod(signature.toLongString());

        try {
            return joinPoint.proceed();
        } finally {
            profiler.exitMethod(System.currentTimeMillis() - startTime);
        }
    }
}
