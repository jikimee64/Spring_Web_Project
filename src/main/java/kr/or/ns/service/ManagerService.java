package kr.or.ns.service;

import java.util.HashMap;
import java.util.List;

import kr.or.ns.vo.Users;

public interface ManagerService {
	public List<Users> getMemberList();
	
	// 
	public Users getUsers(String user_id);
	
	public List<HashMap<String, String>> getSkill(String user_id);
}
