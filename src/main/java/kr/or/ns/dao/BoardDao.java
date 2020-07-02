package kr.or.ns.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.or.ns.vo.Criteria;
import kr.or.ns.vo.Lecture;


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
	
//////////////////////	
	
	////강의 게시물 개수
	public int getLectureCount(String field, String query) throws ClassNotFoundException, SQLException;
	
	//강의 전체 게시물
	public List<Lecture> getLectureNotices(int page, String field, String query) throws ClassNotFoundException, SQLException;
	
	//강의 게시물 삭제
	public int deleteLecture(String seq) throws ClassNotFoundException, SQLException;
	
	//강의 게시물 수정
	public int updateLecture(Lecture lecture) throws ClassNotFoundException, SQLException;
	
	//강의 게시물 상세
	public Lecture getLectureNoticeDetail(String seq) throws ClassNotFoundException, SQLException;
	
	//강의 게시물 입력
	public int insertLecture(Lecture lecture) throws ClassNotFoundException, SQLException;


}
