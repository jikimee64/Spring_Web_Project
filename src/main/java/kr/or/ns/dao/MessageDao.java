package kr.or.ns.dao;

import java.sql.SQLException;

import kr.or.ns.vo.Message;

public interface MessageDao {

	public int getmsgcount(String userid);
	
	public int insertMessage(Message message);
}
