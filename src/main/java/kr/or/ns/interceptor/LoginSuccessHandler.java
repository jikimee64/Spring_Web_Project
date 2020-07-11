package kr.or.ns.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import kr.or.ns.dao.MyPageDao;
import kr.or.ns.vo.Users;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
    
    public LoginSuccessHandler() {
    	System.out.println("LoginSuccessHandler 생성");
    }
	
//    private UserService userService;
//	@Autowired
//	public void setUserService(UserService userService) {
//		this.userService = userService;
//	}
    @Autowired
    private SqlSession sqlsession;
    
	private String defaultUrl;
	
	public String getDefaultUrl() {
		return defaultUrl;
	}
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		resultRedirectStrategy(request, response, authentication);
		clearAuthenticationAttributes(request);
		sessionAdd(request, authentication);
		
	}
    protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        
        if(savedRequest!=null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStratgy.sendRedirect(request, response, targetUrl);
        } else {
            redirectStratgy.sendRedirect(request, response, defaultUrl);
        }
        
    }
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null) return;
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
    
    
    //세션 저장
    protected void sessionAdd(HttpServletRequest request , Authentication authentication) {
    	
    	//true일시 세션이 있으면 기존꺼 사용 아니면 세션을새로 만듬
        HttpSession session = request.getSession(true);
        Users currentUser = null;
        System.out.println("sqlsession : "+ sqlsession);
//        currentUser= userService.getUser(authentication.getName());
      try {	
        	System.out.println("authentication.getName() : "+authentication.getName());
        	MyPageDao userdao = sqlsession.getMapper(MyPageDao.class);
        	currentUser = userdao.getUsers(authentication.getName());
        	System.out.println("우철 : " + currentUser);
        
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println("currentUser : "+currentUser);
        session.setAttribute("currentUser", currentUser);
        }

}