package kr.or.ns.dao;

import java.sql.SQLException;

import kr.or.ns.vo.Skill;
import kr.or.ns.vo.Users;


public interface MemberDao {

	//회원가입
		public int joininsert(Users users) throws ClassNotFoundException, SQLException;
	
	
}
