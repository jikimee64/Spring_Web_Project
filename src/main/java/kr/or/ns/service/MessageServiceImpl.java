package kr.or.ns.service;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import kr.or.ns.dao.MessageDao;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private SqlSession sqlsession;
	// 이것도 자동주입안됨

	@Override
	public int getmsgcount(String user_id) {
		System.out.println("아이디 넘겨와ㅐ라  " + user_id);
		int result = 0;
		System.out.println(sqlsession);
		MessageDao dao = sqlsession.getMapper(MessageDao.class);
		System.out.println("좀 와라!!");
		result = dao.getmsgcount(user_id);
		System.out.println("result : " + result);
		return result;
	}

}
