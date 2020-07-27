package kr.or.ns.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ns.page.PageMaker_Board;
import kr.or.ns.service.MessageService;
import kr.or.ns.vo.Criteria_Board;

@Controller
@RequestMapping("/message/")
public class MessageController {

	@Autowired
	MessageService mservice;

	// 받은 쪽지 목록+페이징
	@RequestMapping("mypage_Message_From_Board.do")
	public String myMessageFromBoardPage(Criteria_Board cri_b, Principal principal, Model model) {

		// 쪽지 테이블에서 사용자 아이디와 일치하는 데이터 가져오기
		String user_id = principal.getName();

		// 페이징
		PageMaker_Board pageMakerb = new PageMaker_Board();
		pageMakerb.setCri_b(cri_b);
		pageMakerb.setTotalCount(mservice.getFromMyMessageCount(user_id));

		// 받은 쪽지 뿌려주기

		// 페이징 들고오기
		List<HashMap<String, Object>> messageList = null;

		HashMap<String, Object> map = new HashMap();
		map.put("user_id", user_id);
		map.put("cri_b", cri_b);

		messageList = mservice.getMessageList(map);

		model.addAttribute("messageList", messageList);// view까지 전달(forward)
		model.addAttribute("pageMakerb", pageMakerb);

		return "user/mypage/mypage_Message_From_Board";
	}

	// 보낸쪽지 + 페이징
	@RequestMapping("mypage_Message_Send_Board.do")
	public String mypageMessageSendBoardPage(Criteria_Board cri_b, Principal principal, Model model) {

		// 쪽지 테이블에서 사용자 아이디와 일치하는 데이터 가져오기
		String user_id = principal.getName();

		// 페이징
		PageMaker_Board pageMakerb = new PageMaker_Board();
		pageMakerb.setCri_b(cri_b);
		pageMakerb.setTotalCount(mservice.getToMyMessageCount(user_id));

		// 받은 쪽지 뿌려주기

		// 페이징 들고오기
		List<HashMap<String, Object>> messageList = null;

		HashMap<String, Object> map = new HashMap();
		map.put("user_id", user_id);
		map.put("cri_b", cri_b);

		messageList = mservice.getSendMessageList(map);

		model.addAttribute("messageList", messageList);// view까지 전달(forward)
		model.addAttribute("pageMakerb", pageMakerb);


		return "user/mypage/mypage_Message_Send_Board";
	}

	// 받은편지함 -> 상세보기
	@RequestMapping("mypage_Message_From_Detail_Board.do")
	public String mypageMessageFromDetailBoardPage(String m_seq, Model model) {
		HashMap<String, Object> message = mservice.getReceptionMessage(m_seq);
		model.addAttribute("message", message);

		return "user/mypage/mypage_Message_From_Detail_Board";
	}

	// 보낸편지함 -> 상세보기
	@RequestMapping("mypage_Message_Send_Detail_Board.do")
	public String mypageMessageSendDetailBoardPage(String m_seq, Model model) {

		HashMap<String, Object> message = mservice.getMessage(m_seq);
		model.addAttribute("message", message);

		return "user/mypage/mypage_Message_Send_Detail_Board";
	}

	// 상세 편지 -> 답장
	@RequestMapping("mypage_Message_From_Send_Message.do")
	public String mypageMessageFromSendMessagePage(String senderid, Model model) {

		model.addAttribute("senderid", senderid);

		return "user/mypage/mypage_Message_Send_Send_Message";
	}

	// 받은편지함 -> 쪽지보내기
	@RequestMapping("mypage_Message_Send_Send_Message.do")
	public String mypageMessageSendSendMessagePage() {

		System.out.println("받은쪽지함 상세보기에서 답장 클릭 시 답장하는 페이지로 이동이동(연규가씀)");
		return "user/mypage/mypage_Message_Send_Send_Message";
	}

	// (받은편지함)상세페이지서 쪽지 삭제
	@RequestMapping(value = "deleteMessageOneFrom.do", method = RequestMethod.GET)
	public String deleteMessageOne(String m_seq) {

		int result = mservice.deleteFromMessageOne(m_seq);

		return "redirect:mypage_Message_From_Board.do";
	}

	// (보낸편지함)상세페이지서 쪽지 삭제
	@RequestMapping(value = "deleteMessageOneSend.do", method = RequestMethod.GET)
	public String deleteMessageOneSend(String m_seq) {

		int result = mservice.deleteSendMessageOne(m_seq);

		return "redirect:mypage_Message_Send_Board.do";
	}

}