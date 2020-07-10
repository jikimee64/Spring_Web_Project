package kr.or.ns.service;

import java.io.FileOutputStream;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.ns.dao.BoardDao;
import kr.or.ns.vo.Criteria;
import kr.or.ns.vo.Criteria_Board;
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

	// 페이징 스터디 글목록
	public List<Map<String, Object>> getStudyBoardList(Criteria_Board cri_b) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		List<Map<String, Object>> list = dao.getStudyBoardList(cri_b);

		return list;
	}

	// 총 스터디게시글 수
	public int getStudyBoardCount() throws ClassNotFoundException, SQLException {
		System.out.println("serviceImpl오냐");

		// 여기까지는 오네
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		System.out.println("매퍼갔다오냐");

		int cnt = dao.getStudyBoardCount();
		System.out.println(cnt + "cnt찍냐");
		return cnt;
	}

	// 스터디 글 등록
	public int studyReg(Study study, HttpServletRequest request, Principal principal) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		
		List<CommonsMultipartFile> files = study.getFiles();
		List<String> filenames = new ArrayList<String>(); // 파일명관리
		System.out.println("?? " + files);
		int count = 0;
		
		if (files != null && files.size() > 0) { // 최소 1개의 업로드가 있다면
			for (CommonsMultipartFile multifile : files) {
				String filename = multifile.getOriginalFilename();
				System.out.println("파일업로드 : " + filename);
				String path = request.getServletContext().getRealPath("/studyboard/upload");

				String fpath = path + "\\" + filename;

				if (!filename.equals("")) { // 실 파일 업로드
					try {
						FileOutputStream fs = new FileOutputStream(fpath);
						fs.write(multifile.getBytes());
						fs.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else { //filename이 공백일시넘 어옴 
					for(int i = count; i < count+1; i++) {
						if(i==0) {
							filename = "study_board_default.jpg"; 
						}else if(i==1) {
							filename = "defaultfile"; 
						}else {
							filename = "defaultfile2"; 
						}
					}
				}
				count++;
				filenames.add(filename); // 파일명을 별도 관리 (DB insert)
				System.out.println("fs" + filename);
			}
		}
		study.setUser_id(principal.getName());
		study.setC_seq(2); //일반강의 등록폼이니까 일반 컨텐츠 정적 부여
		study.setImage(filenames.get(0));  
		System.out.println("1: " + study.getImage());
		study.setFilesrc(filenames.get(1));
		System.out.println("1: " + study.getFilesrc());
		study.setFilesrc2(filenames.get(2));
		System.out.println("1: " + study.getFilesrc2());
		try {
			System.out.println("여긴오니?ㄴㄴㄴ");
			System.out.println("우철 : " + study);
			int result = dao.studyReg(study);
			System.out.println("여긴오니22?");
		}catch(Exception e) {
			System.out.println("삽입 에러");
			e.getMessage();
		}
		
		return 0;
	}

	// 스터디 글 상세보기
	public Map<String, Object> getStudy(String s_seq) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		Map<String, Object> study = dao.getStudy(s_seq);

		return study;
	}

	// 스터디 글 삭제
	public int delete(String s_seq) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);

		System.out.println("아오 :ㅣ " + s_seq);
		int count = dao.delete(s_seq);
		return count;

	}

	// 댓글 개수
	public int getReplyCnt(String s_seq) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		int count = dao.getReplyCnt(s_seq);

		return count;
	}

}
