package kr.or.ns.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ns.dao.ManagerDao;
import kr.or.ns.vo.Blame;
import kr.or.ns.vo.Message;
import kr.or.ns.vo.Users;

@Service
@Qualifier("ManagerServiceImpl")
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	private SqlSession sqlsession;
	
	// 회원목록 가져오기 select All
	public List<Users> getMemberList() {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		List<Users> list = dao.getMemberList();
		return list;
	}
	//회원정보 가져오기
	@Override
	public Users getUsers(String user_id) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		Users user = dao.getUsers(user_id);
		return user;
	}
	//회원정보 스킬부분 가져오기
	@Override
	public List<HashMap<String, String>> getSkill(String user_id) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		List<HashMap<String, String>> list = dao.getSkill(user_id);
		return list;
	}
	//회원탈퇴
	@Override
	public String memberDel(String user_id) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		dao.memberDelete(user_id);
		return "redirect:member_Management.do";
	}
	//신고리스트
	@Override
	public List<HashMap<String, Object>> getBlameList() {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		List<HashMap<String, Object>> blame = dao.getBlameList();
		return blame;
	}
	//회원권한 (정지)
	public int stopMember(String user_id) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		int result = dao.stopMember(user_id);
		return result;
	}
	//회원권한 (복구)
	@Transactional
	public int restoreMember(String user_id) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		int result = 0;
		try {
			dao.restoreResetMember(user_id);
			dao.restoreMember(user_id);
			result = 1;
		} catch (Exception e) {
			System.out.println("둘 중에 하나라도 문제가 생기면 예외가 떨어지는 부분" + e.getMessage());
			throw e; // 예외를 다시 돌려줌. 그리고 이 예외가 발생하는 시점에 transactionManager가 감시를 하다가 rollback 처리를 한다.
		}
		return result;
	}
    //신고내역 상세 확인
	public HashMap<String, Object> getDetailDeclare(String bl_seq) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		HashMap<String, Object> map = dao.getDetailDeclare(bl_seq);
		return map;
	}
	//신고처리
	@Override
	@Transactional 
	public int blameYes(String bl_seq, String bl_target_id) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		int result = 0;
		try {
			int a = dao.blameYes(bl_seq);
			int b = dao.blameTargetUp(bl_target_id);
			
			if (a == 1 && b == 1) {
				result = 1;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e; // 예외를 다시 돌려줌. 그리고 이 예외가 발생하는 시점에 transactionManager가 감시를 하다가 rollback 처리를 한다.
		}
		return result;
	}
   //신고 취소
	public int blameNo(String bl_seq) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		int result = dao.blameNo(bl_seq);
		return result;
	}

	//신고관리 쪽지내용 확인
	public HashMap<String, Object> messageGet(String m_seq) {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		HashMap<String, Object> map = dao.messageGet(m_seq);
		return map;
	}

	// 총 회원 수
	@Override
	public int membercount() {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		int membercount = dao.memberCount();
		return membercount;
	}

	// 가장 많이 스터디가 개설된 지역
	@Override
	public String bestLocation() {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		String bestLocation = dao.bestLocation();

		return bestLocation;
	}

	// 가장 많이 선택받은 언어
	@Override
	public int getLectureCount() {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		int result = dao.getLectureCount();
		return result;
	}

	// 처리 안된 신고 갯수
	@Override
	public int blameCount() {
		ManagerDao dao = sqlsession.getMapper(ManagerDao.class);
		int blameCount = dao.blameCount();
		return blameCount;
	}

}
