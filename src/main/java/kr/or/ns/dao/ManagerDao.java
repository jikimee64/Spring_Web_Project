package kr.or.ns.dao;

import java.util.HashMap;
import java.util.List;

import kr.or.ns.vo.Blame;
import kr.or.ns.vo.Users;

public interface ManagerDao {

	//현재 가입한 모든 회원 정보를 가져오는 함수
	public List<Users> getMemberList();

	//해당 회원의 정보만 가져오는 함수
	public Users getUsers(String user_id);

	//해당 회원의 스킬정보를 가져오는 함수
	public List<HashMap<String, String>> getSkill(String user_id);

	//회원삭제를 하는 함수
	public int memberDelete(String user_id);

	//접수된 모든신고정보를 불러오는 함수
	public List<HashMap<String, Object>> getBlameList();
	
	//계정정지하는 함수(enabled 1->0)_
	public int stopMember(String user_id);
	
	//계정복구할 회원의 경고횟수를 0으로 세팅하는 함수
	public int restoreResetMember(String user_id);
	
	//계정복구하는 함수(enabled 0->1)
	public int restoreMember(String user_id);
	
	//해당 신고에 대한 정보를 불러오는 함수
	public HashMap<String, Object> getDetailDeclare(String bl_seq);
	
	//
	public int blameYes(String bl_seq);
	
	//신고정상처리시 피신고자의 경고횟수를 1카운팅 해주는 함수
	public int blameTargetUp(String bl_target_id);
	
	//신고취소처리 하는 함수
	public int blameNo(String bl_seq);

	//해당 메시지의 대한 정보를 불러오는 함수
	public HashMap<String, Object> messageGet(String m_seq);
	
	//현재 가입한 총회원수를 불러오는 함수
	public int memberCount();
	
	//스터디 최다 개설 지역을 가져오는 함수
	public String bestLocation();
	
	//온라인 강의에 등록된 개수를 가져오는 함수
	public int getLectureCount();

	//처리해야할 신고건수를 불러오는 함수
	public int blameCount();

}
