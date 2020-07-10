package kr.or.ns.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ns.vo.Criteria;
import kr.or.ns.vo.Criteria_Select;

public interface LectureService {
	
	//온라인 게시글 총 게시글 수
	public int getLectureCount();
	
	//온라인 게시글 페이징 목록
	public List<Map<String, Object>> getLectureList(Criteria cri);
	
	//온라인 게시글 좋아요
	public void heartinsert(String user_id, String l_seq);

	//스터디모집-온라인강의 스터디 개설시 북마크 총 갯수
	public int getBookmarkCount(String userid);

	//북마크 목록
	//public List<Map<String, Object>> getBookmarkList(Criteria_Select cri_s);

	public List<HashMap<String, Object>> getBookmarkList(Criteria_Select cri_s, String userid);
	
	//북마크 목록 페이징 시도
//	public List<HashMap<?, ?>> getBookmarkList(Criteria_Select cri_s, String userid);
}
