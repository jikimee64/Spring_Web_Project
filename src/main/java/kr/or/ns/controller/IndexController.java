package kr.or.ns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/index.do")
	public String indexPage() {

		return "user/index"; 
	}
	
	@RequestMapping("/403.do")
	public String error403() {
	    return "user/403"; //view 주소
	}
	
	
}
