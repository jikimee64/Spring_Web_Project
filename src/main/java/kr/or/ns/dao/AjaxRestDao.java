package kr.or.ns.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ns.vo.Users;

import kr.or.ns.vo.Users;

public interface AjaxRestDao {
	//이름과 이메일 받아서 존재하는 회원인지 확인
	public int emailCheck(HashMap<String, String> map) ;
	
	//이름과 이메일 받아서 회원정보 받기
	public Users searchId(HashMap<String, String> map);
	
	//임시비밀번호로 업데이트 
	public void updatePw(Users vo);
	
	//받아온 아이디, 이메일로 존재하는 이메일 인지 확인
	public int checkEmail(HashMap<String, String> map);
	
	//중복체크
	public int idcheck(String user_id);	
	
	//스터디 그룹 인서트
	public int insertStudyGroup(HashMap<String, String> map);
	
	//신고하기(게시판)
	public int insertBlame(HashMap<String,Object> map);
	
	//신고하기(쪽지)
	public int insertBlame_Message(HashMap<String,Object> map);
	
	//선택된 쪽지 삭제하기
	public int delete_Message(HashMap<String,Object> map);
	
	//유저정보 모달창에 띄우기
	public List<HashMap<String, Object>> getUserInfo(String user_id);
	
}