package kr.or.ns.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ns.dao.BoardDao;
import kr.or.ns.dao.ChatDao;
import kr.or.ns.vo.ChatRoom;
import kr.or.ns.vo.ChatRoomMember;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private SqlSession sqlsession;

	public List<ChatRoom> getListChatRoom() {
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

	@Override
	public String roomPw(String ch_seq) {
		ChatDao dao = sqlsession.getMapper(ChatDao.class);
		String password = dao.roomPw(ch_seq);
		return password;
	}

	public int memberInsert(ChatRoomMember cm) {
		ChatDao dao = sqlsession.getMapper(ChatDao.class);
		int result = dao.memberInsert(cm);
		return result;
	}

	public ChatRoom getChatRoom(String ch_seq) {
		ChatDao dao = sqlsession.getMapper(ChatDao.class);
		ChatRoom chatroom = dao.getChatRoom(ch_seq);
		return chatroom;
	}
	
	public int chatRoomOut(ChatRoomMember cm) {
		ChatDao dao = sqlsession.getMapper(ChatDao.class);
		int result = dao.chatRoomOut(cm);
		return result;
	}

}
