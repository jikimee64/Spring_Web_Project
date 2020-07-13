package kr.or.ns.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ns.dao.MessageDao;
import kr.or.ns.vo.Message;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private SqlSession sqlsession;

	public int getmsgcount(String user_id) {
		int result = 0;
		MessageDao dao = sqlsession.getMapper(MessageDao.class);
		result = dao.getmsgcount(user_id);
		return result;
	}

	@Override
	public int insertMessage(Message message) {
		MessageDao dao = sqlsession.getMapper(MessageDao.class);
		int result = 0;
		try {
			result = dao.insertMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// 받은편지 목록 뿌리기
	@Override
	public List<Message> getListMessage(String userid) {
		MessageDao dao = sqlsession.getMapper(MessageDao.class);
		List<Message> list = null;
		try {
			System.out.println("아이디 : " + userid);
			list = dao.getListMessage(userid);
			System.out.println("으철 : " + list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 편지 상세보기
	@Transactional
	@Override
	public Message getMessage(String m_seq) {

		MessageDao dao = sqlsession.getMapper(MessageDao.class);
		Message message = null;
		try {
			System.out.println("아이디 : " + m_seq);
			message = dao.getMessage(m_seq);
			dao.updateMessage(m_seq);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	// 보낸편지 목록 뿌리기
	@Override
	public List<Message> sendListMessage(String userid) {
		MessageDao dao = sqlsession.getMapper(MessageDao.class);
		List<Message> list = null;
		try {
			System.out.println("아이디 : " + userid);
			list = dao.sendListMessage(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//쪽지상세페이지서 쪽지 하나 삭제
	@Override
	public int deleteMessageOne(String m_seq) {
		MessageDao dao = sqlsession.getMapper(MessageDao.class);
		int result = 0;
		try {
			result = dao.deleteMessageOne(m_seq);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("일단 여긴옴2");
		return result;
	}

}
