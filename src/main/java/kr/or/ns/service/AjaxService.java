package kr.or.ns.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ns.vo.Blame;

public interface AjaxService {
	
	
	public int emailCheck(String user_name, String user_email);

	public String emailSend(String useremail);

	
	public String findId(String user_name, String user_email);

	public void makeNewPw(String userid, String useremail);
	
	public int searchId(String user_id, String user_email);
	//아이디 중복체크
	public int idcheck(String user_id) throws ClassNotFoundException;
	//스터디 게시판 일반 지원하기
	public int applyNomalStudy(String user_id, String s_seq);
	
//	public int blameInsert(Blame blame,  );
}
