package kr.or.ns.service;

import java.util.List;

import kr.or.ns.vo.Users;

public interface ManagerService {

	//회원목록 가져오기 select All
	public List<Users> getMemberList();
}
