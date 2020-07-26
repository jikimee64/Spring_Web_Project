package kr.or.ns.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ns.vo.ChatRoom;
import kr.or.ns.vo.ChatRoomMember;

public interface ChatDao {
	
	//모든 채팅방을 가져오는 함수
	public List<HashMap<String, Object>> getListChatRoom();
	
	//채팅방 하나에 대한 정보를 가져오는 함수
	public ChatRoom getChatRoom(String ch_seq);
	
	//채팅방을 등록하는 함수
	public int registerRoom(Map<String, Object> params);
	
	//채팅방의 비밀번호를 가져오는 함수
	public String roomPw(String ch_seq);
	
	//채팅방에 입장한 멤버를 DB에 삽입하는 함수
	public int memberInsert(ChatRoomMember cm);
	
	//채팅방에서 나갈시 채팅방 테이블에서 해당하는 회원을 제거하는 함수
	public int chatRoomOut(ChatRoomMember cm);
	
	//채팅방 정보를 업데이트하는 함수
	public int chatUpdate(Map<String, Object> params);
	
	//채팅방 삭제
	public int chatDelete(String ch_seq);
	
	//채팅방의 현재 참여인원을 구하는 함수
	public List<HashMap<String, Object>> chatRoomMemberGet(String ch_seq);


}
