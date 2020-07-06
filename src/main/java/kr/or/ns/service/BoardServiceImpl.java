package kr.or.ns.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ns.dao.BoardDao;
import kr.or.ns.vo.Criteria;
import kr.or.ns.vo.Study;

/*
클래스명 : BoardServiceImpl
버전 정보 v.1.0
마지막 업데이트 날짜 : 2020 - 07 - 01
작업자 : 박민혜

study_List 목록뿌리기 작업

*/
@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private SqlSession sqlsession;

	//페이징 스터디 글목록
	public List<Map<String, Object>> getStudyBoardList(Criteria cri) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		List<Map<String,Object>> list = dao.getStudyBoardList(cri);
		
		return list;
	}

	
	
	
	//총 스터디게시글 수
	public int getStudyBoardCount() throws ClassNotFoundException, SQLException {
		System.out.println("serviceImpl오냐");
		
		//여기까지는 오네
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		System.out.println("매퍼갔다오냐");
		
		int cnt = dao.getStudyBoardCount();
		System.out.println(cnt+"cnt찍냐");
		return cnt;
	}
	
	
	
	public int studyReg(Study study, HttpServletRequest request) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		return 0;
	}

	
	
	
	//스터디 글 상세보기
	public Study getStudy(String s_seq) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		Study study = dao.getStudy(s_seq);
		
		return study;
	}

	

}
