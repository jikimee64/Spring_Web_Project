package kr.or.ns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/")
public class MemberController {
	
	@RequestMapping("/login.do")
	public String loginPage() {
		System.out.println("로그인으로 이동이동(연규가씀)");
		return "/user/member/login"; 
	}
	@RequestMapping("/join.do")
	public String joinPage() {
		System.out.println("회원가입으로 이동이동(연규가씀)");
		return "/user/member/join"; 
	}
	@RequestMapping("/find_ID.do")
	public String findIDPage() {
		System.out.println("아이디 찾기로 이동이동(연규가씀)");
		return "/user/member/find_ID"; 
	}
	@RequestMapping("/find_Passward.do")
	public String findPasswardPage() {
		System.out.println("비밀번호 찾기로 이동이동(연규가씀)");
		return "/user/member/find_Passward"; 
	}
}
