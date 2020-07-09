package kr.or.ns.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ns.dao.ManagerDao;
import kr.or.ns.vo.Users;
@Service
public class ManagerServiceImpl implements ManagerService {
	
	@Autowired
	private SqlSession sqlsession;
	
	
	//회원목록 가져오기 select All
	public List<Users> getMemberList() {
		
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		List<Users> list = dao.getMemberList();
		
		
	return list;
	}

}
