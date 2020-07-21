package kr.or.ns.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ns.page.PageMaker_Board;
import kr.or.ns.vo.ChatRoom;
import kr.or.ns.vo.Criteria_Board;

@Controller
@RequestMapping("/chat/")
public class ChatController {
	
	//  채팅리스트방 입장
		@RequestMapping("roomlist.do")
		public String studyListPage(Criteria_Board cri_b, Model model) throws ClassNotFoundException, SQLException {
			System.out.println("채팅 페이지로 이동이동(연규가씀)");

			return "chat/roomlist"; 
		}
		
		@RequestMapping("chatroominsert.do")
		@ResponseBody
		public List<ChatRoom> chatRoomInsert(@RequestBody Map<String, Object> params, Principal principal) throws IOException {
			System.out.println("(채팅방 생성 후 DB 인서트)");
			
			System.out.println("방제목 : " + params.get("ch_title"));
			System.out.println("방설명 : " + params.get("ch_description"));
			System.out.println("방비번 : " + params.get("ch_pw"));
			if(params.get("ch_pw") != null) {
				params.put("ch_pw_check", 1);
			}else {
				params.put("ch_pw_check", 0);
			}
			params.put("user_id", principal.getName());
			
			List<ChatRoom> roomList = null;
			
			
			return roomList; 
		}
		
	//채팅방 내부 입장
		
		@RequestMapping("entrance.do")
		public String chatRoomEntrance(Criteria_Board cri_b, Model model) throws ClassNotFoundException, SQLException {
			System.out.println("채팅방 내부 페이지로 이동이동(연규가씀)");

			return "chat/chatroom"; 
		}

}
