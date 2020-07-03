package kr.or.ns.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ns.vo.Message;

public interface MessageService {
	public int getmsgcount(String userid);
	public int insertMessage(Message message);
	public List<Message> getListMessage(String userid);
	public Message getMessage(String m_seq);
	public List<Message> sendListMessage(String userid);
	
}
