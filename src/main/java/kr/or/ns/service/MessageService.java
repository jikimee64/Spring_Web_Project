package kr.or.ns.service;

import org.springframework.stereotype.Service;

import kr.or.ns.vo.Message;

public interface MessageService {
	public int getmsgcount(String userid);
	public int insertMessage(Message message);

}
