package kr.or.ns.dao;

import java.sql.SQLException;

import kr.or.ns.vo.Skill;
import kr.or.ns.vo.Users;


public interface MemberDao {

	//회원가입
		public int joininsert(Users users, Skill skill) throws ClassNotFoundException, SQLException;
	
		/*
		 * //회원가입 시 skill 인서트 public int skillinsert(Skill skill);
		 */
	
}
