package kr.or.ns.service;

import java.util.HashMap;
import java.util.List;

import kr.or.ns.vo.Criteria_Board;

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
	
	//마이페이지 모집중 스터디 비동기
	public List<HashMap<String, Object>> recrutingStudy(HashMap<String, Object> params);
	
	//마이페이지 참여중 스터디 비동기
	public List<HashMap<String, Object>> inStudy(HashMap<String, Object> params);
	
	//차트
	public List<HashMap<String, Object>> mainChart();
	
	//지원현황 승인
	public List<HashMap<String, Object>> accept(HashMap<String, Object> params);
	
	//지원현황 거절
	public int reject(HashMap<String, Object> params);
	
	//참가중인 스터디원 취소
	public List<HashMap<String, Object>> cancel(HashMap<String, Object> params);
	
	//북마크 취소
	public int deleteBookMark(HashMap<String, Object> params);
		
	//스터디게시판 필터
	public List<HashMap<String, Object>> studyBoardFilter(HashMap<String, Object> params, Criteria_Board cri_b);
	
	//스터디게시판 필터사이즈체크용
	public List<HashMap<String, Object>> studyBoardFilterSize(HashMap<String, Object> params);
		
	
	//마이페이지 내가 쓴 댓글 비동기
	public List<HashMap<String, Object>> commentList(HashMap<String, Object> params);

	//모집마감
	public void finishRecruit(String s_seq);

	//모집마감으로 변경시 승인대기중 회원목록 삭제 
	public void deleteWaitingUsers(String s_seq);

	//스터디 지원한거 취소하기 
	public void applycancelNomalStudy(String s_seq, String user_id);
	

}