package kr.or.ns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager/")
public class ManagerController {
	
	@RequestMapping("index.do")
	public String indexPage() {
		System.out.println("어드민대문이동");

		return "manager/index"; 
	}

	@RequestMapping("includes/board/member_Management.do")
	public String memberManagementPage() {
		System.out.println("어드민 테이블페이지이동");

		return "manager/includes/board/member_Management"; 
	}
	
	@RequestMapping("includes/board/report_Management.do")
	public String reportManagementPage() {
		System.out.println("어드민 테이블페이지이동");

		return "manager/includes/board/report_Management"; 
	}
}
