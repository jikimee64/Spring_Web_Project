package kr.or.ns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/index.do")
	public String indexPage() {
		System.out.println("대문이동");

		return "user/index"; 
	}
	
	
}
