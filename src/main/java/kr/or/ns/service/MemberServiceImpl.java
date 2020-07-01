package kr.or.ns.service;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ns.dao.MemberDao;
import kr.or.ns.vo.Skill;
import kr.or.ns.vo.Users;

@Repository
public class MemberServiceImpl implements MemberService {

	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	@Override
	public int joininsert(Users users) throws Exception, SQLException {
		
		System.out.println("서비스오나요");
		System.out.println("유저정보" + users.getUser_id());
	
		int result = 0;
		 MemberDao dao = sqlsession.getMapper(MemberDao.class);
		result = dao.joininsert(users);
		
		return result;
	}
	
	/*
	 * public int skillinsert(Skill skill) throws Exception, SQLException {
	 * System.out.println("서비스오나요"); System.out.println("유저정보" +
	 * skill.getSkill_name()); int result = 0; MemberDao dao =
	 * sqlsession.getMapper(MemberDao.class); result= dao.skillinsert(skill);
	 * 
	 * return result; }
	 */

}
