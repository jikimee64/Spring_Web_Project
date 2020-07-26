package kr.or.ns.dao;

import java.util.HashMap;
import java.util.List;

import kr.or.ns.vo.Blame;
import kr.or.ns.vo.Users;

public interface ManagerDao {

	public List<Users> getMemberList();

	public Users getUsers(String user_id);

	public List<HashMap<String, String>> getSkill(String user_id);

	public int memberDelete(String user_id);

	public List<HashMap<String, Object>> getBlameList();
	
	public int stopMember(String user_id);
	
	public int restoreResetMember(String user_id);
	
	public int restoreMember(String user_id);
	
	public HashMap<String, Object> getDetailDeclare(String bl_seq);
	
	public int blameYes(String bl_seq);
	
	public int blameTargetUp(String bl_target_id);
	
	public int blameNo(String bl_seq);

	public HashMap<String, Object> messageGet(String m_seq);
	
	public int memberCount();
	
	public String bestLocation();
	
	public int getLectureCount();

	public int blameCount();

}
