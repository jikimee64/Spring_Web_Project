package kr.or.ns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	//WEB-INF폴더 밖 index.do 실행 시 index페이지 이동
	@RequestMapping("/index.do")
	public String indexPage() {
		return "user/index"; 
	}
	
	//접근권한 페이지 이동
	@RequestMapping("/403.do")
	public String error403() {
	    return "user/403"; //view 주소
	}
	
	
}
