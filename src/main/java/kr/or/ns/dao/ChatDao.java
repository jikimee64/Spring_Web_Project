package kr.or.ns.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ns.vo.ChatRoom;

public interface ChatDao {
	
	public List<ChatRoom> getListChatRoom();
	
	public int registerRoom(Map<String, Object> params);
	
	public String roomPw(String ch_seq);
}
