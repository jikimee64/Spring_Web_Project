package kr.or.ns.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ns.vo.ChatRoom;
import kr.or.ns.vo.ChatRoomMember;

public interface ChatService {
	
	public List<ChatRoom> getListChatRoom();

	public int registerRoom(Map<String, Object> params);
	
	public String roomPw(String ch_seq);
	
	public int memberInsert(ChatRoomMember cm);
	
	public ChatRoom getChatRoom(String ch_seq);
	
	public int chatRoomOut(ChatRoomMember cm);
	
	public List<HashMap<String, Object>> chatRoomMemberGet(String ch_seq);

}
