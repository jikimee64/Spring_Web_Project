package kr.or.ns.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ns.vo.Blame;
import kr.or.ns.vo.Message;
import kr.or.ns.vo.Users;

public interface AjaxService {
	
	//이메일 있는지 확인
	public int emailCheck(String user_name, String user_email);
	
	//이메일 전송
	public String emailSend(String useremail);
	
	//아이디찾기
	public String findId(String user_name, String user_email);
	
	//비밀번호 생성
	public void makeNewPw(String userid, String useremail);
	
	//아이디찾기(중복체크)
	public int searchId(String user_id, String user_email);

	// 아이디 중복체크
	public int idcheck(String user_id) throws ClassNotFoundException;

	// 스터디 게시판 일반 지원하기
	public int applyNomalStudy(String user_id, String s_seq);

	// 신고하기(게시판)
	public int blameInsert(HashMap<String, Object> params, String current_userid);

	// 선택된 쪽지 삭제하기
	public int deleteMessage(HashMap<String, Object> params);
	
	//유저정보 모달 뿌리기
	public List<HashMap<String, Object>> userInfoModal(HashMap<String, Object> params);
	
	//이메일 중복체크
	public int onlyEmailCheck(String user_email);
	

}