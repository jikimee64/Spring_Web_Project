package kr.or.ns.dao;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import kr.or.ns.vo.Users;

public interface MemberDao {

	// 회원가입
	public int joininsert(Users users) throws ClassNotFoundException, SQLException;

	//회원가입(스킬)
	public int insertskill(HashMap<String, Object> map) throws ClassNotFoundException, SQLException;

}
