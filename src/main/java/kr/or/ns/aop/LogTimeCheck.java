package kr.or.ns.aop;

import java.net.InetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.or.ns.vo.Users;

@Component
@Aspect
public class LogTimeCheck {

	static final Logger log = LoggerFactory.getLogger(LogTimeCheck.class);

	/**
	 * 모든 컨트롤러 시간 체크 및 유저 아이디, IP 출력
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* kr.or.ns.controller.*Controller.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		InetAddress local = InetAddress.getLocalHost();
		String ip = local.getHostAddress();
		String user_id = "";

		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("currentUser");
		if (user != null) {
			user_id = user.getUser_id();
		}

		Object returnObj = null;
		String method = joinPoint.getSignature().getName();
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		returnObj = joinPoint.proceed();
		stopWatch.stop();
		log.warn("# " + method + "() Method Performance Time Check : " + stopWatch.getTotalTimeMillis() + "(ms)"
				+ " 접속 아이디 : " + user_id + " 접속 IP : " + ip);

		return returnObj;

	}

}
