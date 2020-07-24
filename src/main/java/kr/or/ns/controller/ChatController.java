package kr.or.ns.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import kr.or.ns.service.MyPageService;
import kr.or.ns.vo.ChatRoom;
import kr.or.ns.vo.ChatRoomMember;
import kr.or.ns.vo.Criteria_Board;
import kr.or.ns.vo.Users;

@Controller
@RequestMapping("/chat/")
public class ChatController {

	@Autowired
	private ChatService service;

	@Autowired
	private MyPageService mservice;

	// 채팅리스트방 입장
	@RequestMapping("roomlist.do")
	public String roomListPage(Model model) throws ClassNotFoundException, SQLException {
		System.out.println("채팅 페이지로 이동이동(연규가씀)");
		List<HashMap<String, Object>> roomList = service.getListChatRoom();
		model.addAttribute("roomList", roomList);
		System.out.println("roomList :" + roomList);

		return "chat/roomlist";
	}

	@RequestMapping("chatroominsert.do")
	@ResponseBody
	public List<HashMap<String, Object>> chatRoomInsert(@RequestBody Map<String, Object> params, Principal principal)
			throws IOException {
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
		List<HashMap<String, Object>> list = service.getListChatRoom();
		System.out.println("채팅룸list" + list);
		return list;
	}

	// 채팅방 내부 입장 / 채팅멤버 테이블에 삽입
	@RequestMapping("entrance.do")
	public String chatRoomEntrance(@RequestParam HashMap<Object, Object> params, Model model, Principal principal)
			throws ClassNotFoundException, SQLException {

		// 현재날짜 포맷팅해서 구하기
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String datestr = sdf.format(cal.getTime());
		System.out.println(datestr);

		String ch_seq = (String) params.get("ch_seq");
		String user_id = principal.getName();

		ChatRoomMember cm = new ChatRoomMember();
		cm.setCh_seq(Integer.parseInt(ch_seq));
		cm.setUser_id(user_id);
		int result = service.memberInsert(cm);
		ChatRoom chatroom = service.getChatRoom(ch_seq);
		System.out.println("삽입 결과 : " + result);

		List<HashMap<String, Object>> list = service.chatRoomMemberGet(ch_seq);
		String master = (String) list.get(0).get("master");
		System.out.println("master : " + master);

		model.addAttribute("master", master);
		model.addAttribute("chatroom", chatroom);
		model.addAttribute("user_id", user_id);
		model.addAttribute("datestr", datestr);

		return "chat/chatroom";
	}

	@RequestMapping("chatRoomOut.do")
	public String chatRoomOut(String ch_seq, Model model, Principal principal)
			throws ClassNotFoundException, SQLException {

		String user_id = principal.getName();

		ChatRoomMember cm = new ChatRoomMember();
		cm.setCh_seq(Integer.parseInt(ch_seq));
		cm.setUser_id(user_id);
		
		List<HashMap<String, Object>> list = service.chatRoomMemberGet(ch_seq);
		
		System.out.println("user_id : " + user_id);
		System.out.println("master : " + list.get(0).get("master"));
		if(!user_id.equals(list.get(0).get("master"))) { 
			System.out.println("오ㅓ지마라 너는");
			int result = service.chatRoomOut(cm);
		}
	
		List<HashMap<String, Object>> roomList = service.getListChatRoom();
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

	// 채팅방 내부에서 멤버리스트 보는 페이지로 이동
	@RequestMapping("chatmemberlist.do")
	public String chatmemberlist() throws ClassNotFoundException, SQLException {
		System.out.println("채팅멤버 리스트 페이지로 이동이동(연규가씀)");

		return "chat/memberlist";
	}

	// 채팅방 내부에서 멤버리스트 보는 페이지로 이동
	@RequestMapping("chatDelete.do")
	public String chatDelete(String ch_seq) throws ClassNotFoundException, SQLException {
		System.out.println("채팅방 삭제");
		int result = service.chatDelete(ch_seq);
		return "redirect:/chat/roomlist.do";
	}

	@RequestMapping("chatRoomMemberGet.do")
	@ResponseBody
	public List<HashMap<String, Object>> chatRoomMemberGet(@RequestBody Map<String, Object> params) throws IOException {
		String ch_seq = (String) params.get("ch_seq");
		List<HashMap<String, Object>> list = service.chatRoomMemberGet(ch_seq);
		System.out.println("채팅룸 멤버 list" + list);
		return list;
	}
	

	@RequestMapping("chatUpdate.do")
	@ResponseBody
	public void chatUpdate(@RequestBody Map<String, Object> params) throws IOException {
		int result = service.chatUpdate(params);
		
	}
	


}
