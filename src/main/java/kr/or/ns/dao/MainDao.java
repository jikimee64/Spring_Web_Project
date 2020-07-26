package kr.or.ns.dao;

import java.util.List;
import java.util.Map;

import kr.or.ns.vo.Criteria_Board;

public interface MainDao {
	
	//Main에서 뿌려줄 현재 모집중인 스터디 리스트를 가져오는 함수
	public List<Map<String, Object>> getNewListStudy();
}
