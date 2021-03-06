package kr.or.ns.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ns.vo.Message;

public interface MessageService {
	
	public int getmsgcount(String userid);
	
	public int insertMessage(Message message);
	
	
	public HashMap<String, Object> getMessage(String m_seq);
	
	//보낸목록
	public List<Message> sendListMessage(String userid);
	
	public int deleteSendMessageOne(String m_seq);
	
	public int deleteFromMessageOne(String m_seq);
	
	//목록
	public List<Message> getListMessage(String userid);
	
	//내 메세지 갯수(받은편지함)
	public int getFromMyMessageCount(String user_id);
	
	//내 메세지 갯수(보낸편지함)
	public int getToMyMessageCount(String user_id);
	
	//받은목록+페이징
	public List<HashMap<String, Object>> getMessageList(HashMap<String, Object> map);
	
	//보낸목록+페이징
	public List<HashMap<String, Object>> getSendMessageList(HashMap<String, Object> map);

	//받은쪽지 상세
	public HashMap<String, Object> getReceptionMessage(String m_seq);
	
}
