package kr.or.ns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ns.dao.MemberDao;
import kr.or.ns.service.MemberService;
import kr.or.ns.vo.Skill;
import kr.or.ns.vo.Users;

@Controller
@RequestMapping("/member/")
public class MemberController {
	
	@Autowired
	MemberService service;
	 
	
	@RequestMapping("/login.do")
	public String loginPage() {
		System.out.println("로그인으로 이동이동(연규가씀)");
		return "/user/member/login"; 
	}
	
	//회원가입 페이지 이동
	@RequestMapping("/join.do")
	public String joinPage() {
		System.out.println("회원가입으로 이동이동(연규가씀)");
		return "/user/member/join"; 
	}
	//회원가입처리
		@RequestMapping(value="join.do" , method=RequestMethod.POST)
		public String joininsert(Users users) {
			
			
			System.out.println("이거타나요");	
			System.out.println("1123 :" + users.getUser_id());
			System.out.println("1123 :" + users.getUser_pwd());
			System.out.println("1 :" + users.getJava());
			System.out.println("2 :" + users.getPython());
			System.out.println("3 :" + users.getHtml_css());
			System.out.println("4 :" + users.getJavascript());
			System.out.println("5 :" + users.getSql());
			
			try {
				service.joininsert(users);
			
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return "redirect:/index.do";  // /index.htm  
			//주의사항
			//요청 주소 ...아래처럼 ..
			//http://localhost:8090/SpringMVC_Basic06_WebSite_Annotation_JdbcTemplate/index.htm
			//return "redirect:noticeDetail.htm?seq="+n.getSeq();
		}
		
	@RequestMapping("/find_Id.do")
	public String findIdPage() {
		System.out.println("아이디 찾기로 이동이동(연규가씀)");
		return "/user/member/find_Id"; 
	}
	@RequestMapping("/find_Passward.do")
	public String findPasswardPage() {
		System.out.println("비밀번호 찾기로 이동이동(연규가씀)");
		return "/user/member/find_Passward"; 
	}
}
