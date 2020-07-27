package kr.or.ns.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class LogTimeCheck {

	static final Logger log = LoggerFactory.getLogger(LogTimeCheck.class);
	
	//컨트롤러의 모든 메소드가 걸린 시간을 체크하는 메소드
	@Around("execution(* kr.or.ns.controller.*Controller.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		Object returnObj = null;
		String method = joinPoint.getSignature().getName();
		StopWatch stopWatch = new StopWatch();
		log.warn("# " + method + "() Method Performance Time Check Start");
		stopWatch.start();
		returnObj = joinPoint.proceed();
		stopWatch.stop();
		log.warn("# " + method + "() Method Performance Time Check End : " + stopWatch.getTotalTimeMillis() + "(ms)");

		return returnObj;

	}

}
