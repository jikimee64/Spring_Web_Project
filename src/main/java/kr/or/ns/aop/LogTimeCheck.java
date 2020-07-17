package kr.or.ns.aop;

import java.security.Principal;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Component
@Aspect
public class LogTimeCheck {
	
	static final Logger log = LoggerFactory.getLogger(LogTimeCheck.class);

	@Around("execution(* kr.or.ns.controller.*Controller.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		Object returnObj = null;
		/* try { */
		String method = joinPoint.getSignature().getName();
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		returnObj = joinPoint.proceed();
		stopWatch.stop();
		
		/*
		 * if(principal != null) { log.warn("# User" + principal.getName() +"| " +
		 * method + "() Method Performance time : " + stopWatch.getTotalTimeMillis() +
		 * "(ms)"); }else {
		 */
			log.warn("# " + method + "() Method Performance time : " + stopWatch.getTotalTimeMillis() + "(ms)");
		//}
		

		return returnObj;

	}

	/*
	 * @Before("execution(* kr.or.ns.controller.*Controller.*(..))") public void
	 * beforeLog(JoinPoint jp) { System.out.println("@Before"); log.warn("[" +
	 * jp.getSignature().getName() + " START]"); }
	 * 
	 * 
	 * @After("execution(* kr.or.ns.controller.*Controller.*(..))") public void
	 * afterLog(JoinPoint jp) { System.out.println("@After"); log.warn("[" +
	 * jp.getSignature().getName() + " END]"); }
	 */

}
