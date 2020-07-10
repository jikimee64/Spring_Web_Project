package kr.or.ns.service;

import java.util.HashMap;
import java.util.List;

import kr.or.ns.vo.Blame;
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
	public List<Blame> getBlameList(String bl_seq);
	
}
