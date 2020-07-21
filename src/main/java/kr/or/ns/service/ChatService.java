package kr.or.ns.service;

import java.util.List;
import java.util.Map;

import kr.or.ns.vo.ChatRoom;

public interface ChatService {
	
	public List<ChatRoom> getListChatRoom();
	
	public int registerRoom(Map<String, Object> params);

}
