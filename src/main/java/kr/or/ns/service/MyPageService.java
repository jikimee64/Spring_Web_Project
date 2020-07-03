package kr.or.ns.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ns.vo.Users;

@Service
public interface MyPageService {
	
	
	//회원정보 가져오기
	public Users getUsers(String userid);
	
	//회원정보(스킬) 가져오기
	public List<HashMap<String, String>> getSkill(String userid);
	
	//회원정보 수정
	public void MyPageUserEdit(Users user);
}
