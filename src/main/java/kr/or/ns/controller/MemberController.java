package kr.or.ns.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kr.or.ns.dao.MemberDao;
import kr.or.ns.service.MemberService;
import kr.or.ns.vo.Skill;
import kr.or.ns.vo.Users;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
@RequestMapping("/member/")
public class MemberController {

	@Autowired
	MemberService service;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping("login.do")
	public String loginPage() {
		System.out.println("로그인으로 이동이동(연규가씀)");
		return "/user/member/login";
	}

	@RequestMapping("join.do")
	public String joinPage() {
		System.out.println("회원가입으로 이동이동(연규가씀)");
		return "/user/member/join";
	}

	// 회원가입처리

	@RequestMapping(value = "join.do", method = RequestMethod.POST)
	public String joininsert(@RequestParam(value = "file", required = false) MultipartFile ipload, Users users,
			HttpServletRequest request) throws IOException {
		

		
		users.setUser_pwd(this.bCryptPasswordEncoder.encode(users.getUser_pwd()));

		try {
			System.out.println("여긴오니...?");
			service.joininsert(users, request);

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return "redirect:/index.do"; // /index.htm
		// 주의사항
		// 요청 주소 ...아래처럼 ..
		// http://localhost:8090/SpringMVC_Basic06_WebSite_Annotation_JdbcTemplate/index.htm
		// return "redirect:noticeDetail.htm?seq="+n.getSeq();
	}

	@RequestMapping("find_Id.do")
	public String findIdPage() {
		System.out.println("아이디 찾기로 이동이동(연규가씀)");
		return "/user/member/find_Id";
	}

	@RequestMapping("find_Passward.do")
	public String findPasswardPage() {
		System.out.println("비밀번호 찾기로 이동이동(연규가씀)");
		return "/user/member/find_Passward";
	}
}
