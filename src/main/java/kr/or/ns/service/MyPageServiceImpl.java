package kr.or.ns.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ns.dao.MyPageDao;
import kr.or.ns.vo.Users;

@Repository
public class MyPageServiceImpl implements MyPageService {

	
	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	//회원 가져오기
	@Override
	public Users getUsers(String userid) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		Users user = dao.getUsers(userid);
		return user;
	}
	
	
	//회원정보 수정
	@Override
	public void MyPageUserEdit(Users user) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		int result = dao.MyPageUserEdit(user);
		
		if(result >0) {
			System.out.println("수정성공");
		}else {
			System.out.println("수정실패");
		}
		
	}

	

}
