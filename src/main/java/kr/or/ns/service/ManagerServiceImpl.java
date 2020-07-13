package kr.or.ns.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ns.dao.ManagerDao;
import kr.or.ns.vo.Blame;
import kr.or.ns.vo.Users;

@Service
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	private SqlSession sqlsession;

	// 회원목록 가져오기 select All
	public List<Users> getMemberList() {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		List<Users> list = dao.getMemberList();
		return list;
	}

	@Override
	public Users getUsers(String user_id) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		Users user = dao.getUsers(user_id);
		return user;
	}

	@Override
	public List<HashMap<String, String>> getSkill(String user_id) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		List<HashMap<String, String>> list = dao.getSkill(user_id);
		System.out.println("욘두 : " + list);
		return list;
	}

	@Override
	public String memberDel(String user_id) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		dao.memberDelete(user_id);
		System.out.println("삭제 될까요?");
		return "redirect:member_Management.do";
	}

	@Override
	public List<HashMap<String, Object>> getBlameList() {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		List<HashMap<String, Object>> blame = dao.getBlameList();
		return blame;
	}

	public int stopMember(String user_id) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		int result = dao.stopMember(user_id);
		return result;
	}

	public int restoreMember(String user_id) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		int result = dao.restoreMember(user_id);
		return result;
	}

	public HashMap<String, Object> getDetailDeclare(String bl_seq) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		HashMap<String, Object> map = dao.getDetailDeclare(bl_seq);
		return map;
	}

}
