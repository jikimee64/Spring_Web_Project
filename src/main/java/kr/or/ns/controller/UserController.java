package kr.or.ns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserController {
	@RequestMapping("main.do")
	public String mainPage() {
		System.out.println("메인으로 이동이동(연규가씀)");
		return "user/main"; 
	}
}
