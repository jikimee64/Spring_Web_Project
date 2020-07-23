package kr.or.ns.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ns.vo.ChatRoom;
import kr.or.ns.vo.ChatRoomMember;

public interface ChatDao {
	
	public List<ChatRoom> getListChatRoom();
	
	public ChatRoom getChatRoom(String ch_seq);
	
	public int registerRoom(Map<String, Object> params);
	
	public String roomPw(String ch_seq);
	
	public int memberInsert(ChatRoomMember cm);
	
	public int chatRoomOut(ChatRoomMember cm);
	
	public int chatUpdate(Map<String, Object> params);
	
	public int chatDelete(String ch_seq);
	
	public List<HashMap<String, Object>> chatRoomMemberGet(String ch_seq);


}
