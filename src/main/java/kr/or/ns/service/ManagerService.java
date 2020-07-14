package kr.or.ns.service;

import java.util.HashMap;
import java.util.List;

import kr.or.ns.vo.Blame;
import kr.or.ns.vo.Message;
import kr.or.ns.vo.Users;

public interface ManagerService {
	
	//회원정보 목록가져오기
	public List<Users> getMemberList();
	
	//유저  상세보기 정보 가져오기(실력 제외)
	public Users getUsers(String user_id);
	
	//유저 실력 가져오기
	public List<HashMap<String, String>> getSkill(String user_id);
	
	//유저 삭제하기
	public String memberDel(String user_id);
	
	//신고게시판 목록가져오기
	public List<HashMap<String, Object>> getBlameList();
	
	//계정 정지
	public int stopMember(String user_id);
	
	//계정 정지 해제
	public int restoreMember(String user_id);
	
	//신고번호에 따른 상세내용 뿌려주기
	public HashMap<String, Object> getDetailDeclare(String bl_seq);
	
	//신고관리(정상신고)
	public int blameYes(String bl_seq, String bl_target_id);
	
	//신고관리(신고X)
	public int blameNo(String bl_seq);
	
	public HashMap<String, Object> messageGet(String m_seq);

	//총 회원 수
	public int membercount();
	
	//가장 많이 스터디가 개설된 지역
	public String bestLocation();
	
	//가장 많이 선택받은 언어
	public String bestLanguage();
	
	//처리안된 신고 갯수
	public int blameCount();

	

}
