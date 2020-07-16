package kr.or.ns.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import kr.or.ns.vo.Skill;
import kr.or.ns.vo.Users;

public interface MemberService {
	
	//일반 회원가입
	public int joininsert(Users users, HttpServletRequest request) throws Exception, SQLException;
	
	//소셜 회원가입
	public int socialjoininsert(Users users, HttpServletRequest request) throws Exception, SQLException;
	
	//회원가입 했는지 확인용
	public Users confirmsocial(String user_email);
	
}
