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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.ns.dao.BoardDao;
import kr.or.ns.vo.Comment;
import kr.or.ns.vo.Criteria;
import kr.or.ns.vo.Criteria_Board;
import kr.or.ns.vo.Likes;
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

	// 스터디 글 등록(일반 컨텐츠)
	public int studyReg(Study study, HttpServletRequest request, Principal principal) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		System.out.println("조ㅓㅁ와라조모!!!!");
		System.out.println("철이는 나빠ㅣ용" + study.getImage());
		
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
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("삽입 에러");
		}
		
		return 0;
	}
	
	
	
	// 스터디 글 등록(온라인 컨텐츠)
	public int studyOnlineReg(Study study, HttpServletRequest request, Principal principal) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		System.out.println("조ㅓㅁ와라조모!!!!");
		System.out.println("철이는 나빠ㅣ용" + study.getImage());
		
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
							filename = "defaultfile"; 
						}else if(i==1) {
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
		study.setC_seq(1); //온라인강의 등록폼이니까 일반 컨텐츠 정적 부여
		study.setFilesrc(filenames.get(0));
		System.out.println("1: " + study.getFilesrc());
		study.setFilesrc2(filenames.get(1));
		System.out.println("1: " + study.getFilesrc2());
		try {
			System.out.println("여긴오니?ㄴㄴㄴ");
			System.out.println("우철 : " + study);
			int result = dao.studyReg(study);
			System.out.println("여긴오니22?");
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("삽입 에러");
		}
		
		return 0;
	}

	// 스터디 글 상세보기
//	public Map<String, Object> getStudy(String s_seq) {
//		BoardDao dao = sqlsession.getMapper(BoardDao.class);
//		Map<String, Object> study = dao.getStudy(s_seq);
//
//		return study;
//	}
//	
	
	
	
	// 스터디 글 상세보기 트랜잭션
//	@Override
//	@Transactional
		public Map<String, Object> getStudy(String s_seq) {
			BoardDao dao = sqlsession.getMapper(BoardDao.class);
			Map<String, Object> study = null;
			
			
		try {
			study = dao.getStudy(s_seq);
			dao.updateReadNum(s_seq);
			System.out.println("정상");
			
		} catch (Exception e) {
			
			System.out.println("문제발생"+e.getMessage());
			throw e;
			
		}
			

			return study;
		}

	
	//조회수 증가
//	public void updateReadNum(Integer s_seq) {
//	}
//	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	//좋아요 게시판에 넣기 
	public void heartinsert(String user_id, String s_seq) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		Likes like = new Likes();
		like.setS_seq(Integer.parseInt(s_seq));
		like.setUser_id(user_id);
		
		
		try {
			//id&l_seq 값으로 북마크 유무 체크
			int result =  dao.heartcheck(like);
			System.out.println( "---------------------   result 찍어보기 -----------------------");
			System.out.println(result);
			System.out.println( "---------------------------------------------------------------");
			 if(result == 0 ) {
				 //없으면 insert
				 System.out.println("insert 넣으러 왔어요"); 
				 like.setLike_check(1);
				 dao.heartinsert(like);
			 }else { 
					 //있으면 update
					 System.out.println("update 갑니다."); 
					 
					 if(dao.heartnum(like) == 0 ) {
						 //1 넣으러 왔어요
						 System.out.println("1 넣으러 왔어요");
						 like.setLike_check(1);
						 dao.heartupdate(like);
					 }else {
						 //0 넣으러 왔어요
						 System.out.println("0 넣으러 왔어요");
						 like.setLike_check(0);
						 dao.heartupdate(like);
					 }
			 }
			 
		} catch (Exception e) {
			System.out.println("오류 났어요.");
			System.out.println(e.getMessage());
		}
		
		
	}
	//하트 0/1 컬럼 확인
	public int heartnum(Likes like) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		int heart = 0;
		try {
			
			heart = dao.heartcheck(like);
			if(heart != 0) {
				 heart = dao.heartnum(like);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return heart;
	}
	
	//해당글의 좋아요 갯수 가져오기 
	public int getLikeCnt(String s_seq) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		int result = dao.getLikeCnt(Integer.parseInt(s_seq));
		return result;
	}

	//댓글 등록하기 
	public void commentInsert(Comment cm) {
		System.out.println("commentInsert 왔어요");
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		dao.commentInsert(cm);
	}

	//해당글의 댓글 select
	public List<Map<String, Object>> getComment(String s_seq) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		List<Map<String, Object>> list = dao.getComment(Integer.parseInt(s_seq));
		System.out.println("select 하고 리턴갑니다(서비스)");
		return list;
	}

	//해당글의 댓글 delete
	public void commentDelete(Comment cm) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		dao.commentDelete(cm);
	}

	//댓글 수정해주는거
	public void commentUpdate(Comment cm) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		dao.commentUpdate(cm);
	}

	//댓글갯수
	public int countComment(Comment cm) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		int result = dao.countComment(cm);
		
		return result;
	}

	//대댓글  insert
	public void reCommentInsert(Comment cm) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		
		System.out.println("reCommentInsert 서비스 입성");

		//1. refer,depth 조회 
		System.out.println(cm);
		Map<String, Integer> map = dao.getReferDepth(cm);
		System.out.println(map);
		int r_depth = map.get("r_depth");
		cm.setR_depth(r_depth);
		cm.setR_refer(map.get("r_refer"));
		
		//2. step 조회 
		
		int result =  dao.getMaxStep(cm);
		System.out.println(result);
		
		//3.depth 가 1보다 작으면 ++
		
		if (r_depth < 1) {
			r_depth++;
		}
		cm.setR_depth(r_depth);
		
		//4.insert
		dao.reCommentInsert(cm);  
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
