package kr.or.ns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager/")
public class ManagerController {
	
	@RequestMapping("index.do")
	public String index() {
		System.out.println("어드민대문이동");

		return "manager/index"; 
	}

}
