package kr.or.ns.service;

import java.util.List;
import java.util.Map;

import kr.or.ns.vo.Criteria;

public interface LectureService {
	
	
	public int getLectureCount();
	
	public List<Map<String, Object>> getLectureList(Criteria cri);
	
	public void heartinsert(String user_id, String l_seq);
}
