package kr.or.ns.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ns.vo.Criteria;



/*
인터페이스명 : BoardService
버전 정보 v.1.0
마지막 업데이트 날짜 : 2020 - 07 - 01
작업자 : 박민혜

study_List 목록뿌리기 작업

*/

public interface BoardService {

	
	//페이징 스터디 글목록
	public List<Map<String, Object>> getStudyBoardList(Criteria cri);
	
	//총 스터디게시글 수
	public int getStudyBoardCount() throws ClassNotFoundException, SQLException;
}
