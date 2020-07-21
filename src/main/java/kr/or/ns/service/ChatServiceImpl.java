package kr.or.ns.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ns.dao.BoardDao;
import kr.or.ns.dao.ChatDao;
import kr.or.ns.vo.ChatRoom;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private SqlSession sqlsession;
	
	public List<ChatRoom> getListChatRoom(){
		ChatDao dao = sqlsession.getMapper(ChatDao.class);
		List<ChatRoom> list = dao.getListChatRoom();
		
		return list;
	}
	
	@Override
	public int registerRoom(Map<String, Object> params) {
		ChatDao dao = sqlsession.getMapper(ChatDao.class);
		dao.registerRoom(params);
		return 0;
	}

}
