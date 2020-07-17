package kr.or.ns.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ns.vo.Criteria_Board;
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

	//이메일 중복체크
	public int onlyEmailCheck(String user_email);
	
	//마이페이지 모집중 스터디 비동기
	public List<HashMap<String, Object>> recrutingStudy(String user_id);
	
	//마이페이지 참여중 스터디 비동기
	public List<HashMap<String, Object>> inStudy(String user_id);
	
	//메인페이지 차트
	public List<HashMap<String, Object>> mainChart();
	
	//지원현황 승인
	public int accept(HashMap<String, Object> params);
	
	//지원현황 승인 후 승인완료 데이터 뽑기
	public List<HashMap<String, Object>> acceptList(HashMap<String, Object> params);
	
	//지원현황 거절
	public int reject(HashMap<String, Object> params);
	
	//지원현황 취소
	public int cancel(HashMap<String, Object> params);
	
	//지원취소 후 승인대기중 데이터 뽑기
	public int deleteBookMark(HashMap<String, Object> params);public List<HashMap<String, Object>> cancelList(HashMap<String, Object> params);
	
	//스터디게시판 필터
	public List<HashMap<String, Object>> studyBoardFilter(HashMap<String, Object> params);
	
	//스터디게시판 필터후 사이즈만 반환역할
	public List<HashMap<String, Object>> studyBoardFilterSize(HashMap<String, Object> params);
	
	//마이페이지 내가 쓴 댓글
	public List<HashMap<String, Object>> commentList(String user_id);

	//모집마감
	public void finishRecruit(String s_seq);

	//모집마감으로 변경시 승인대기중 회원목록 삭제
	public void deleteWaitingUsers(String s_seq);
}