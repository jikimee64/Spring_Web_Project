package kr.or.ns.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import kr.or.ns.vo.Users;

@Service
public interface MyPageService {
	
	//회원정보 가져오기
	public Users getUsers(String userid);
	
	//회원정보(스킬) 가져오기
	public List<HashMap<String, String>> getSkill(String userid);
	
	//회원정보 수정
	public int MyPageUserEdit(Users user, HttpServletRequest request);
	
	//회원탈퇴
	public void userDelete(Users user, Principal principal);

}
