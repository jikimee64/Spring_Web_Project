package kr.or.ns.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ns.service.MessageService;
import kr.or.ns.service.MyPageService;
import kr.or.ns.vo.Message;

@Controller
@RequestMapping("/message/")
public class MessageController {

	@Autowired
	MessageService mservice;

	
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