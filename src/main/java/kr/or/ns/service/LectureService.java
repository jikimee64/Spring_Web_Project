package kr.or.ns.service;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.or.ns.vo.Criteria;
import kr.or.ns.vo.Criteria_Select;
import kr.or.ns.vo.Study;

public interface LectureService {
	
	//온라인 게시글 총 게시글 수
	public int getLectureCount();
	
	//온라인 게시글 페이징 목록
	public List<Map<String, Object>> getLectureList(Criteria cri);
	
	//온라인 게시글 페이징 목록(필터링)
	public List<Map<String, Object>> getLectureListFilter(Criteria cri, HashMap<String, Object> map);
	
	public List<Map<String, Object>> getLectureListFilterSize(Criteria cri, HashMap<String, Object> params);
	
	//온라인 컨텐츠(스터디 게시판 글 편집)
	public int studyOnlineEdit(Study study, HttpServletRequest request, Principal principal);
	
	//온라인 게시글 상세(1개)
	public HashMap<String, Object> getLecture(String l_seq);
	
	//온라인 게시글 좋아요
	public void heartinsert(String user_id, String l_seq);

	//스터디모집-온라인강의 스터디 개설시 북마크 총 갯수
	public int getBookmarkCount(String userid);

	//북마크 목록 페이징 
	public List<HashMap<String, Object>> getBookmarkList(HashMap<String, Object> map);

	//l_seq 를 가져오는 함수
	public List<Integer> getCheckedL_seq(String user_id);

	
	
}
