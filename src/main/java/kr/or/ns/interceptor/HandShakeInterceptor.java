package kr.or.ns.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


public class HandShakeInterceptor extends HttpSessionHandshakeInterceptor{
	  
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    	System.out.println("Before Handshake");
    	ServletServerHttpRequest ssreq = (ServletServerHttpRequest) request;
    	HttpServletRequest req= ssreq.getServletRequest();
    	
    	//HttpServletReques를 이용하여 파라미터 추출
    	//파라미터로 받은 roomNumber와 userid 추출
    	String roomNumber  = req.getParameter("roomNumber");
    	String userid  = req.getParameter("userid");
    	
    	// 파라미터로 입력된 attributes에 put을 하면 WebSocketSession에서 접근가능
    	attributes.put("roomNumber", roomNumber);
    	attributes.put("userid", userid);
    	
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
  
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
