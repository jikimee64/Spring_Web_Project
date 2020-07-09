package kr.or.ns.dao;

import java.util.HashMap;
import java.util.List;

import kr.or.ns.vo.Users;

public interface ManagerDao {

	public List<Users> getMemberList();

	public Users getUsers(String user_id);

	public List<HashMap<String, String>> getSkill(String user_id);

	public int memberDelete(String user_id);

}
