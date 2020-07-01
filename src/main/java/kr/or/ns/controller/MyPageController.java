package kr.or.ns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage/")
public class MypageController {
	
	@RequestMapping("/mypage.do")
	public String myPagePage() {
		System.out.println("마이페이지로 이동이동(연규가씀)");
		return "/user/mypage/mypage"; 
	}
	@RequestMapping("/mypage_User_Edit.do")
	public String mypageUserEditPage() {
		System.out.println("유저 수정페이지로 이동이동(연규가씀)");
		return "/user/mypage/mypage_User_Edit"; 
	}
	@RequestMapping("/mypage_Myboard.do")
	public String myBoardPage() {
		System.out.println("내가 쓴 게시판으로 이동이동(연규가씀)");
		return "/user/mypage/mypage_Myboard"; 
	}
	@RequestMapping("/mypage_Message_From_Board.do")
	public String myMessageFromBoardPage() {
		System.out.println("받은 쪽지함으로 이동이동(연규가씀)");
		return "/user/mypage/mypage_Message_From_Board"; 
	}
	@RequestMapping("/mypage_Message_Send_Board.do")
	public String mypageMessageSendBoardPage() {
		System.out.println("보낸 쪽지함으로 이동이동(연규가씀)");
		return "/user/mypage/mypage_Message_Send_Board"; 
	}
	@RequestMapping("/mypage_Message_From_Detail_Board.do")
	public String mypageMessageFromDetailBoardPage() {
		System.out.println("받은 쪽지함에서 해당게시글(쪽지)클릭시 해당쪽지 상세보기로 이동이동(연규가씀)");
		return "/user/mypage/mypage_Message_From_Detail_Board"; 
	}
	@RequestMapping("/mypage_Message_Send_Detail_Board.do")
	public String mypageMessageSendDetailBoardPage() {
		System.out.println("보낸 쪽지함에서 해당게시글(쪽지)클릭시 해당쪽지 상세보기로 이동이동(연규가씀)");
		return "/user/mypage/mypage_Message_Send_Detail_Board"; 
	}
	@RequestMapping("/mypage_Message_From_Send_Message.do")
	public String mypageMessageFromSendMessagePage() {
		System.out.println("받은쪽지함 상세보기에서 답장 클릭 시 답장하는 페이지로 이동이동(연규가씀)");
		return "/user/mypage/mypage_Message_From_Send_Message"; 
	}
	@RequestMapping("/mypage_Message_Send_Send_Message.do")
	public String mypageMessageSendSendMessagePage() {
		System.out.println("받은쪽지함 상세보기에서 답장 클릭 시 답장하는 페이지로 이동이동(연규가씀)");
		return "/user/mypage/mypage_Message_Send_Send_Message"; 
	}
}
