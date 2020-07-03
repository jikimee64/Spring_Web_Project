package kr.or.ns.service;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import kr.or.ns.dao.MessageDao;
import kr.or.ns.vo.Message;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private SqlSession sqlsession;
	// 이것도 자동주입안됨

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
		System.out.println("일단 여긴옴");
		System.out.println(message.toString());
		try {
			result = dao.insertMessage(message);
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		System.out.println("일단 여긴옴2");
		return result;
	}

}
