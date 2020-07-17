package kr.or.ns.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ns.vo.Users;

public interface MyPageDao {

	// 회원정보 수정
	public int MyPageUserEdit(Users user);

	// 회원정보 수정(스킬)
	public int editskill(HashMap<String, String> mo) throws ClassNotFoundException, SQLException;

	// 회원정보 가져오기
	public Users getUsers(String uid);

	// 회원정보 가져오기(스킬)
	public List<HashMap<String, String>> getSkill(String userid);

	// 회원탈퇴
	public void userDelete(String userid);

	// 마이페이지(북마크 가져오기)
	public List<Map<String, Object>> getBookMark(String userid);

	// 마이페이지(북마크 갯수 가져오기)
	public int getbkCount(String users);

	// 마이페이지(댓글 갯수 가져오기)
	public int getcmCount(String users);

	// 마이페이지(스터디게시판 게시글 갯수 가져오기
	public int getsbCount(String users);
	
	//마이페이지(참여중 스터디)
	public int join_study(String users);
	
	//마이페이지(참여중 스터디)
	public int recruit_study(String users);

	// 마이페이지(스터디 목록 가져오기)
	public List<HashMap<String, Object>> myPageStudyList(String userid);
	
	//마이페이지 스터디 현황
	public List<HashMap<String, Object>> studyStatus(String userid);

	public HashMap<String,Object> getRole(HashMap<String, Object> map);

	//글 번호로 알아오는 모집 상태
	public String getStatus(String s_seq);


}
