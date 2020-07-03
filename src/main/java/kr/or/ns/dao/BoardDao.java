package kr.or.ns.dao;

import java.util.List;
import java.util.Map;

import kr.or.ns.vo.Criteria;


/*
인터페이스명 : BoardDao
버전 정보 v.1.0
마지막 업데이트 날짜 : 2020 - 07 - 01
작업자 : 박민혜

study_List 목록뿌리기 작업

*/

public interface BoardDao {
	
	//페이징 스터디 글목록
	public List<Map<String, Object>> getStudyBoardList(Criteria cri);

	//총 스터디게시글 수
	public int getStudyBoardCount();
	



}
