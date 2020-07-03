package kr.or.ns.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.ns.vo.Lecture;

public interface LectureDao {
//////////////////////
	
////강의 게시물 개수
public int getLectureCount(String field, String query) throws ClassNotFoundException, SQLException;

//강의 전체 게시물
public List<Lecture> getLectureList(int page, String field, String query) throws ClassNotFoundException, SQLException;

/*
 * //강의 게시물 삭제 public int deleteLecture(String seq) throws
 * ClassNotFoundException, SQLException;
 * 
 * //강의 게시물 수정 public int updateLecture(Lecture course) throws
 * ClassNotFoundException, SQLException;
 * 
 * //강의 게시물 상세 public Lecture getCourseLecture(String seq) throws
 * ClassNotFoundException, SQLException;
 * 
 * //강의 게시물 입력 public int insertLecture(Lecture course) throws
 * ClassNotFoundException, SQLException;
 */

}
