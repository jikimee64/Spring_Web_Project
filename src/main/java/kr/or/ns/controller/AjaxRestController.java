package kr.or.ns.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ns.service.AjaxService;
import kr.or.ns.vo.Users;

@RestController //controller + responsebody
@RequestMapping("/ajax/")
public class AjaxRestController {

	@Autowired
	private AjaxService service;

	// 아이디 찾기 -> 아이디,이메일 입력 후 인증받기 -> 존재하는 회원이면 이메일로 보내기 / 인증번호 생성후 전송
	@RequestMapping(value = "emailCheck.do", method = RequestMethod.POST)
	public HashMap<String, String> emailCheck(@RequestBody HashMap<String, Object> params) {
		// 해쉬맵 생성
		HashMap<String, String> map = new HashMap<String, String>();

		// 정보넣어주기
		String username = (String) params.get("username");
		String useremail = (String) params.get("useremail");

		// 결과값 확인
		int flag = service.emailCheck(username, useremail);
		System.out.println("우철 : " + flag);
		System.out.println(username);
		System.out.println(useremail);

		// 결과에 따른 안내
		if (flag == 1) {
			// 이메일 보내기
			System.out.println("여기탔습니다");
			String key = service.emailSend(useremail);
			map.put("key", key);

			// ID 찾아오셈
			String id = service.findId(username, useremail);
			System.out.println("여기는 컨트롤러" + id + "여기는 컨트롤러");
			map.put("id", id);

		} else {
			System.out.println("존재하지 않는 계쩡이라고 안내해야함");
			map.put("key", "null");
		}
		/* map.put("keyId", list); */

		return map;

	}

	// 임시비밀번호를 만들기 전에 이메일과 아이디값 확인하는 함수
	@RequestMapping(value = "pwCheck.do", method = RequestMethod.POST)
	public int pwCheck(@RequestBody HashMap<String, Object> params) {
		System.out.println("----------------------------------------------------------컨트롤러-시작-------");
		String userid = (String) params.get("userId");
		String useremail = (String) params.get("userEmail");
		// 서비스단에서 있는 이메일과, 아이디 인지 확인
		int result = 0;
		result = service.searchId(userid, useremail);

		if (result == 0) {
			System.out.println("--id 비어있어 --");

			return result;

		} else {
			System.out.println("result : " + result);
			System.out.println("----------------------------------------------------------컨트롤러-중간-------");
			// service.makeNewPw(userid,useremail);
			System.out.println("----------------------------------------------------------컨트롤러-끝-------");
			System.out.println("이메일 발급후 리턴 전");
			System.out.println("result : " + result);
			return result;
		}

	}

	// 임시비밀번호 발급 하는 함수
	@RequestMapping(value = "makeNewPWD.do", method = RequestMethod.POST)
	public void makeNewPWD(@RequestBody HashMap<String, Object> params) {
		System.out.println("----------------------------------------------------------컨트롤러-시작-------");
		String userid = (String) params.get("userId");
		String useremail = (String) params.get("userEmail");
		service.makeNewPw(userid, useremail);

	}

	// 아이디 중복체크
	@RequestMapping(value = "idcheck.do", method = RequestMethod.POST)
	public int idcheck(String id) throws ClassNotFoundException {
		System.out.println(id + " : user_id 컨트롤러");
		int userid = service.idcheck(id);

		return userid;

	}
	//일반스터디 글 등록하기
	@RequestMapping(value = "nomalstudy.do", method = RequestMethod.POST)
	public String applyNomalStudy(@RequestBody HashMap<String, Object>params, Principal principal) {
		String userid = (String) params.get("USER_ID");
		int result = service.applyNomalStudy(userid);
		System.out.println("잘됐나 확인" + result);
		
		return "redirect:/index.do";
	}



}
