package kr.or.ns.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ns.vo.Users;

public interface MyPageDao {
	
	//회원정보 수정
	public int MyPageUserEdit(Users user);
	
	//회원정보 수정(스킬)
	public int editskill(HashMap<String, String> mo) throws ClassNotFoundException, SQLException;
	
	//회원정보 가져오기
	public Users getUsers(String uid);
	
	//회원정보 가져오기(스킬)
	public List<HashMap<String, String>> getSkill(String userid);

	//회원탈퇴
	public void userDelete(String userid);
	
	//마이페이지(북마크 가져오기)
	public List<Map<String, Object>> getBookMark(String userid);
	

}
