package kr.or.ns.dao;

import kr.or.ns.vo.Users;

public interface MyPageDao {
	
	//회원정보 수정
	public int MyPageUserEdit(Users user);
	
	//회원정보 가져오기
	public Users getUsers(String uid);
}
