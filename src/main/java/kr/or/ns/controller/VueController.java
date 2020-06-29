package kr.or.ns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vue/")
public class VueController {
	
	@RequestMapping("vue.do")
	public String index() {
		System.out.println("대문이동");

		return "/user/member/vue"; 
	}


}
