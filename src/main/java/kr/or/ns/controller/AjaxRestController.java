package kr.or.ns.controller;

import java.util.ArrayList;
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
	public HashMap<String, String> emailCheck(@RequestBody HashMap<String, Object> params) {
		//해쉬맵 생성
		HashMap<String, String> map = new HashMap<String, String>();
		
		//정보넣어주기
		String username = (String) params.get("username");
		String useremail = (String) params.get("useremail");
		
		//결과값 확인
		int flag = service.emailCheck(username, useremail);
		System.out.println("우철 : " + flag);
		System.out.println(username);
		System.out.println(useremail);
		
		
	    //결과에 따른 안내
		if(flag == 1) {
			//이메일 보내기
			System.out.println("여기탔습니다");
			String key =  service.emailSend(useremail);
			map.put("key", key);
			
			//ID 찾아오셈
			String id = service.findId(username, useremail);
			System.out.println("여기는 컨트롤러"+id+"여기는 컨트롤러");
			map.put("id", id);
			
		}else {
			System.out.println("존재하지 않는 계쩡이라고 안내해야함");
			map.put("key", "null");
		}
		/* map.put("keyId", list); */
		
		return map;
		
	}
	
	
	@RequestMapping(value = "pwCheck.do", method=RequestMethod.POST)
	public void pwCheck(@RequestBody HashMap<String, Object> params) {
	
		String userid = (String) params.get("userId");
		String useremail = (String) params.get("userEmail");
		System.out.println("************************");
		System.out.println(userid);
		System.out.println(useremail);
		System.out.println("************************");
		
		
		
		service.makeNewPw(userid,useremail);
		
				
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
	

}
