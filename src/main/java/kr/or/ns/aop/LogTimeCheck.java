package kr.or.ns.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LogTimeCheck {

	@Around("execution(* kr.or.ns.controller.*Controller.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		Object returnObj = null;
		/* try { */
		String method = joinPoint.getSignature().getName();
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		returnObj = joinPoint.proceed();
		stopWatch.stop();
		System.out.println("#  " + method + "() Method Performance time : " + stopWatch.getTotalTimeMillis() + "(ms)");

		/*
		 * OutputStream output = new FileOutputStream("C:/kwc/Output.txt"); String str =
		 * "오늘 날씨는 아주 좋습니다."; byte[] by = str.getBytes(); output.write(by);
		 */

		/*
		 * } catch (Exception e) { e.getStackTrace(); }
		 */

		return returnObj;

	}
	/*
	 * @Before("execution(* kr.or.ns.controller.*Controller.*(..))") public void
	 * beforeLog(JoinPoint jp) { System.out.println("@Before"); log.info("[" +
	 * jp.getTarget().getClass().getSimpleName() + " " + jp.getSignature().getName()
	 * + " START]"); }
	 * 
	 * @After("execution(* kr.or.ns.controller.*Controller.*(..))") public void
	 * afterLog(JoinPoint jp) { System.out.println("@After"); log.info("[" +
	 * jp.getTarget().getClass().getSimpleName() + " " + jp.getSignature().getName()
	 * + " END]");
	 * 
	 * }
	 */

}
