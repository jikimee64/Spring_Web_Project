package kr.or.ns.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.ns.vo.Message;

public interface MessageDao {

	// 수신인 쪽지 개수
	public int getmsgcount(String userid);

	// 쪽지 보내기
	public int insertMessage(Message message);

	// 받은 쪽지함 목록
	public List<Message> getListMessage(String userid);

	// 받은 편지 상세보기
	public Message getMessage(String m_seq);

	// 보낸 쪽지함 목록
	public List<Message> sendListMessage(String userid);
	
	//상세페이지서 쪽지 삭제
	public int deleteMessageOne(String m_seq);

}
