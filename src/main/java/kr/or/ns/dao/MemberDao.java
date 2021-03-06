package kr.or.ns.dao;

import java.sql.SQLException;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import kr.or.ns.vo.Users;
@Repository
public interface MemberDao {

	// 회원가입
	public int joininsert(Users users) throws ClassNotFoundException, SQLException;

	//회원가입(스킬)
	public int insertskill(HashMap<String, Object> map) throws ClassNotFoundException, SQLException;
	
	//소셜 회원가입
	public int socialjoininsert(Users users);
	
	//이미 등록된 이메일인지 확인용
	public Users confirmsocial(String user_id);
	

}
