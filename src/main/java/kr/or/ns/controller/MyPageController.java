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

import kr.or.ns.service.MessageService;
import kr.or.ns.service.MyPageService;
import kr.or.ns.vo.Message;
import kr.or.ns.vo.Users;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mypage/")
public class MyPageController {
	
	@Autowired
	MyPageService service;
	
	@Autowired
	MessageService mservice;
	
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
	public String myMessageFromBoardPage(Principal principal, Model model) {
		//받은 쪽지 뿌려주기
		List<Message> mlist = mservice.getListMessage(principal.getName());
		model.addAttribute("message", mlist);
		
		System.out.println("받은 쪽지함으로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_From_Board"; 
	}
	@RequestMapping("mypage_Message_Send_Board.do")
	public String mypageMessageSendBoardPage(Principal principal, Model model) {
		//보낸 쪽지함
		List<Message> mlist = mservice.sendListMessage(principal.getName());
		model.addAttribute("message", mlist);
		System.out.println("보낸 목록 : " + mlist);
		
		System.out.println("보낸 쪽지함으로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_Send_Board"; 
	}
	
	//받은편지함 -> 상세보기
	@RequestMapping("mypage_Message_From_Detail_Board.do")
	public String mypageMessageFromDetailBoardPage(String m_seq, Model model) {
		
		System.out.println("편지 번호 : " + m_seq);
		Message message = mservice.getMessage(m_seq);
		System.out.println("우철이 : " + message);
		model.addAttribute("message", message);
		
		
		System.out.println("받은 쪽지함에서 해당게시글(쪽지)클릭시 해당쪽지 상세보기로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_From_Detail_Board"; 
	}
	@RequestMapping("mypage_Message_Send_Detail_Board.do")
	public String mypageMessageSendDetailBoardPage() {
		System.out.println("보낸 쪽지함에서 해당게시글(쪽지)클릭시 해당쪽지 상세보기로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_Send_Detail_Board"; 
	}
	@RequestMapping("mypage_Message_From_Send_Message.do")
	public String mypageMessageFromSendMessagePage(String senderid, Model model) {
		//상세 편지 -> 답장
		
		model.addAttribute("senderid", senderid);
		
		System.out.println("받은쪽지함 상세보기에서 답장 클릭 시 답장하는 페이지로 이동이동(연규가씀)");
		//return "user/mypage/mypage_Message_From_Send_Message"; 
		return "user/mypage/mypage_Message_Send_Send_Message"; 
	}
	@RequestMapping("mypage_Message_Send_Send_Message.do")
	public String mypageMessageSendSendMessagePage() {
		//받은편지함 -> 쪽지보내기
		
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
