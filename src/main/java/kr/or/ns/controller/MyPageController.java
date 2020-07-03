package kr.or.ns.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import kr.or.ns.service.MyPageService;
import kr.or.ns.vo.Users;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mypage/")
public class MyPageController {
	
	@Autowired
	MyPageService service;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@RequestMapping("mypage.do")
	public String myPagePage() {
		System.out.println("마이페이지로 이동이동(연규가씀)");
		return "user/mypage/mypage"; 
	}
	@RequestMapping(value="MyPageUserEdit.do", method=RequestMethod.GET)
	public String mypageUserEdit(Model model, Principal principal) {
		System.out.println("유저 수정페이지로 이동이동(연규가씀)");
		Users user = service.getUsers(principal.getName());
		List<HashMap<String, String>> list = service.getSkill(principal.getName());
		model.addAttribute("member", user);
		model.addAttribute("skill", list);
		return "user/mypage/mypage_User_Edit.html"; 
	}
	
	@RequestMapping(value="MyPageUserEdit.do", method=RequestMethod.POST)
	public String mypageUserEdit(Model model, Users users, Principal principal) {
		
		System.out.println("컨트롤러1");
		System.out.println(users);
		System.out.println(principal);
		Users user = service.getUsers(principal.getName());
		System.out.println("userrrrr:"+ user);
		
		
		user.setUser_pwd(bCryptPasswordEncoder.encode(users.getUser_pwd()));
		user.setNickname(users.getNickname());
		user.setJava(users.getJava());
		user.setPython(users.getPython());
		user.setHtml_css(users.getHtml_css());
		user.setJavascript(users.getJavascript());
		user.setSql(users.getSql());
		service.MyPageUserEdit(user);
		
		System.out.println("컨트롤러2");
		return "redirect:user/mypage/mypage";

	}
	
	@RequestMapping("mypage_Myboard.do")
	public String myBoardPage() {
		System.out.println("내가 쓴 게시판으로 이동이동(연규가씀)");
		return "user/mypage/mypage_Myboard"; 
	}
	@RequestMapping("mypage_Message_From_Board.do")
	public String myMessageFromBoardPage() {
		System.out.println("받은 쪽지함으로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_From_Board"; 
	}
	@RequestMapping("mypage_Message_Send_Board.do")
	public String mypageMessageSendBoardPage() {
		System.out.println("보낸 쪽지함으로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_Send_Board"; 
	}
	@RequestMapping("mypage_Message_From_Detail_Board.do")
	public String mypageMessageFromDetailBoardPage() {
		System.out.println("받은 쪽지함에서 해당게시글(쪽지)클릭시 해당쪽지 상세보기로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_From_Detail_Board"; 
	}
	@RequestMapping("mypage_Message_Send_Detail_Board.do")
	public String mypageMessageSendDetailBoardPage() {
		System.out.println("보낸 쪽지함에서 해당게시글(쪽지)클릭시 해당쪽지 상세보기로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_Send_Detail_Board"; 
	}
	@RequestMapping("mypage_Message_From_Send_Message.do")
	public String mypageMessageFromSendMessagePage() {
		System.out.println("받은쪽지함 상세보기에서 답장 클릭 시 답장하는 페이지로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_From_Send_Message"; 
	}
	@RequestMapping("mypage_Message_Send_Send_Message.do")
	public String mypageMessageSendSendMessagePage() {
		System.out.println("받은쪽지함 상세보기에서 답장 클릭 시 답장하는 페이지로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_Send_Send_Message"; 
	}
	
	
	//쪽지 보내기
	@RequestMapping(value="sendMessage.do", method = RequestMethod.POST)
	public String sendMessage(@RequestParam(value="title", required=false) String title,
							  @RequestParam(value="content", required=false) String content) {
		
		System.out.println("title : "  + title);
		System.out.println("content : " + content);
		
		return "user/mypage/mypage_Message_Send_Send_Message"; 
	}
}
