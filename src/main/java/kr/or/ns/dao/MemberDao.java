package kr.or.ns.dao;

import java.sql.SQLException;
import java.util.HashMap;

import kr.or.ns.vo.Skill;
import kr.or.ns.vo.Users;

public interface MemberDao {

	// 회원가입
	public int joininsert(Users users) throws ClassNotFoundException, SQLException;

	public int insertskill(HashMap<String, Object> map) throws ClassNotFoundException, SQLException;

}
