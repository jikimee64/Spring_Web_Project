package kr.or.ns.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ns.service.AjaxService;
import kr.or.ns.vo.Users;

@RestController
@RequestMapping("/ajax/")
public class AjaxRestController {

	@Autowired
	private AjaxService service;
	
	//아이디 찾기 -> 아이디,이메일 입력 후 인증받기 -> 존재하는 회원이면 이메일로 보내기 / 인증번호 생성후 전송
	@RequestMapping(value = "emailCheck.do", method=RequestMethod.POST)
	public void emailCheck(@RequestBody HashMap<String, Object> params) {
		
		String username = (String) params.get("username");
		String useremail = (String) params.get("useremail");
		
		int flag = service.emailCheck(username, useremail);
		System.out.println("우철 : " + flag);
		
		
		
		
		if(flag == 1) {
			//이메일 보내기
			String key =  service.emailSend(useremail);
			System.out.println(key);
		}else {
			System.out.println("존재하지 않는 계쩡이라고 안내해야함");
		}
		
		
		
		
	}

}
