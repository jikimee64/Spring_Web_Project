package kr.or.ns.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.weaver.tools.cache.AsynchronousFileCacheBacking.ClearCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ns.service.MessageService;
import kr.or.ns.service.MyPageService;
import kr.or.ns.vo.Message;
import kr.or.ns.vo.Users;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
		return "user/mypage/mypage";
	}

	// 유저 상세정보 뿌리기
	@RequestMapping(value = "MyPageUserDetail.do")
	public String mypageUserEditView(Model model, Principal principal) {
		System.out.println("상세정보");
		Users user = service.getUsers(principal.getName());
		List<HashMap<String, String>> list = service.getSkill(principal.getName());
		model.addAttribute("member", user);
		model.addAttribute("skill", list);
		return "user/mypage/mypage_User_Detail.html";
	}

	// 유저정보 수정페이지로 이동
	@RequestMapping(value = "MyPageUserEdit.do")
	public String mypageUserEdit(Model model, Principal principal) {
		Users user = service.getUsers(principal.getName());
		List<HashMap<String, String>> list = service.getSkill(principal.getName());
		model.addAttribute("member", user);
		model.addAttribute("skill", list);
		return "user/mypage/mypage_User_Edit.html";
	}

	// 유저정보 수정하기
	@RequestMapping(value = "MyPageUserEdit.do", method = RequestMethod.POST)
	public String mypageUserEdit(@RequestParam(value = "file", required = false) MultipartFile ipload, Users user,
			HttpServletRequest request, Model model, Principal principal) {
		user.setUser_pwd(this.bCryptPasswordEncoder.encode(user.getUser_pwd()));

		System.out.println("컨트롤러1");
		System.out.println(user);
		System.out.println("userrrrr:" + user);

		service.MyPageUserEdit(user, request);

		System.out.println("컨트롤러2");
		return "user/mypage/mypage?seq=" + user.getUser_id();

	}

	// 회원탈퇴
	@RequestMapping(value = "userDelete.do", method = RequestMethod.GET)
	public String userDelte(Principal principal, HttpServletRequest request, HttpServletResponse response) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		new SecurityContextLogoutHandler().logout(request, response, auth);
		String users = principal.getName();
		service.userDelete(users);
		return "redirect:/index.do";
	}

	@RequestMapping("mypage_Myboard.do")
	public String myBoardPage() {
		System.out.println("내가 쓴 게시판으로 이동이동(연규가씀)");
		return "user/mypage/mypage_Myboard";
	}

	@RequestMapping("mypage_Message_From_Board.do")
	public String myMessageFromBoardPage(Principal principal, Model model) {
		// 받은 쪽지 뿌려주기
		List<Message> mlist = mservice.getListMessage(principal.getName());
		model.addAttribute("message", mlist);

		System.out.println(mlist);
		
		System.out.println("받은 쪽지함으로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_From_Board";
	}

	@RequestMapping("mypage_Message_Send_Board.do")
	public String mypageMessageSendBoardPage(Principal principal, Model model) {
		// 보낸 쪽지함
		List<Message> mlist = mservice.sendListMessage(principal.getName());
		model.addAttribute("message", mlist);
		System.out.println("보낸 목록 : " + mlist);

		
		
		System.out.println("보낸 쪽지함으로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_Send_Board";
	}

	// 받은편지함 -> 상세보기
	@RequestMapping("mypage_Message_From_Detail_Board.do")
	public String mypageMessageFromDetailBoardPage(String m_seq, Model model) {

		Message message = mservice.getMessage(m_seq);
		model.addAttribute("message", message);

		System.out.println("받은 쪽지함에서 해당게시글(쪽지)클릭시 해당쪽지 상세보기로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_From_Detail_Board";
	}

	@RequestMapping("mypage_Message_Send_Detail_Board.do")
	public String mypageMessageSendDetailBoardPage(String m_seq, Model model) {

		Message message = mservice.getMessage(m_seq);
		model.addAttribute("message", message);

		System.out.println("보낸 쪽지함에서 해당게시글(쪽지)클릭시 해당쪽지 상세보기로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_Send_Detail_Board";
	}

	@RequestMapping("mypage_Message_From_Send_Message.do")
	public String mypageMessageFromSendMessagePage(String senderid, Model model) {
		// 상세 편지 -> 답장

		model.addAttribute("senderid", senderid);

		System.out.println("받은쪽지함 상세보기에서 답장 클릭 시 답장하는 페이지로 이동이동(연규가씀)");
		// return "user/mypage/mypage_Message_From_Send_Message";
		return "user/mypage/mypage_Message_Send_Send_Message";
	}

	@RequestMapping("mypage_Message_Send_Send_Message.do")
	public String mypageMessageSendSendMessagePage() {
		// 받은편지함 -> 쪽지보내기

		System.out.println("받은쪽지함 상세보기에서 답장 클릭 시 답장하는 페이지로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_Send_Send_Message";
	}

	// (받은편지함)상세페이지서 쪽지 삭제
	@RequestMapping(value = "deleteMessageOneFrom.do", method = RequestMethod.GET)
	public String deleteMessageOne(String m_seq) {

		System.out.println("쪽지 번호 : " + m_seq);

		int result = mservice.deleteMessageOne(m_seq);

		return "redirect:mypage_Message_From_Board.do";
	}

	// (보낸편지함)상세페이지서 쪽지 삭제
	@RequestMapping(value = "deleteMessageOneSend.do", method = RequestMethod.GET)
	public String deleteMessageOneSend(String m_seq) {

		System.out.println("쪽지 번호 : " + m_seq);

		int result = mservice.deleteMessageOne(m_seq);

		return "redirect:mypage_Message_Send_Board.do";
	}

	// 쪽지 보내기
	@RequestMapping(value = "sendMessage.do", method = RequestMethod.POST)
	public String sendMessage(@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "content", required = false) String content) {

		System.out.println("title : " + title);
		System.out.println("content : " + content);

		return "user/mypage/mypage_Message_Send_Send_Message";
	}
	@RequestMapping("mypage_Mycomment.do")
	public String mypageMycomment() {
		System.out.println("내가 쓴 게시판으로 이동이동(연규가씀)");
		return "user/mypage/mypage_Mycomment";
	}
}
