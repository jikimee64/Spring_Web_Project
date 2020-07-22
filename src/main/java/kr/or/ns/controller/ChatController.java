package kr.or.ns.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ns.page.PageMaker_Board;
import kr.or.ns.service.BoardService;
import kr.or.ns.service.ChatService;
import kr.or.ns.vo.ChatRoom;
import kr.or.ns.vo.ChatRoomMember;
import kr.or.ns.vo.Criteria_Board;

@Controller
@RequestMapping("/chat/")
public class ChatController {

	@Autowired
	private ChatService service;

	// 채팅리스트방 입장
	@RequestMapping("roomlist.do")
	public String studyListPage(Criteria_Board cri_b, Model model) throws ClassNotFoundException, SQLException {
		System.out.println("채팅 페이지로 이동이동(연규가씀)");
		List<ChatRoom> roomList = service.getListChatRoom();
		model.addAttribute("roomList", roomList);
		System.out.println("roomList :" + roomList);

		return "chat/roomlist";
	}
	
	

	@RequestMapping("chatroominsert.do")
	@ResponseBody
	public List<ChatRoom> chatRoomInsert(@RequestBody Map<String, Object> params, Principal principal) throws IOException {
		System.out.println("(채팅방 생성 후 DB 인서트)");

		System.out.println("방제목 : " + params.get("ch_title"));
		System.out.println("방설명 : " + params.get("ch_description"));
		System.out.println("방비번 : " + params.get("ch_pw"));
		if (params.get("ch_pw") != null) {
			params.put("ch_pw_check", 1);
		} else {
			params.put("ch_pw_check", 0);
		}
		params.put("user_id", principal.getName());
		service.registerRoom(params);
		List<ChatRoom> list = service.getListChatRoom();
		System.out.println("채팅룸list" + list);
		return list;
	}

	//채팅방 내부 입장 / 채팅멤버 테이블에 삽입
	@RequestMapping("entrance.do")
	public String chatRoomEntrance(@RequestParam HashMap<Object, Object> params, Model model, Principal principal) throws ClassNotFoundException, SQLException {
		
		String ch_seq = (String)params.get("ch_seq");
		String user_id = principal.getName();
		
		ChatRoomMember cm = new ChatRoomMember();
		cm.setCh_seq(Integer.parseInt(ch_seq));
		cm.setUser_id(user_id);
		int result = service.memberInsert(cm);
		ChatRoom chatroom = service.getChatRoom(ch_seq);
		System.out.println("삽입 결과 : " + result);
		
		model.addAttribute("chatroom", chatroom);
		model.addAttribute("user_id", user_id );
		
		return "chat/chatroom";
	}
	
	
	@RequestMapping("chatRoomOut.do")
	public String chatRoomOut(String ch_seq, Model model, Principal principal) throws ClassNotFoundException, SQLException {
		
		String user_id = principal.getName();
		
		ChatRoomMember cm = new ChatRoomMember();
		cm.setCh_seq(Integer.parseInt(ch_seq));
		cm.setUser_id(user_id);
		int result = service.chatRoomOut(cm);
		List<ChatRoom> roomList = service.getListChatRoom();
		model.addAttribute("roomList", roomList);
		System.out.println("roomList : " + roomList);
		
		return "chat/roomlist";
	}

	// 비밀방의 방번호를 받아서 패스워드 넘겨줌
	@RequestMapping(value = "roomPw.ajax", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, String> roomPw(@RequestParam HashMap<Object, Object> params) {
		String pw = "";
		Map<String, String> map = new HashMap<String, String>();
		String ch_seq = (String) params.get("ch_seq");
		System.out.println("방번호 : " + ch_seq);
		if (ch_seq != null && !ch_seq.trim().equals("")) {
			pw = service.roomPw(ch_seq);
			map.put("pw", pw);
		}
		return map;
	}
	
	//채팅방 내부에서 멤버리스트 보는 페이지로 이동
	@RequestMapping("chatmemberlist.do")
	public String chatmemberlist(Criteria_Board cri_b, Model model) throws ClassNotFoundException, SQLException {
		System.out.println("채팅멤버 리스트 페이지로 이동이동(연규가씀)");
		
		return "chat/memberlist";
	}
	

}
