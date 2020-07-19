package kr.or.ns.service;

import java.io.FileOutputStream;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.ns.dao.BoardDao;
import kr.or.ns.dao.LectureDao;
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
	
	//study_board_online 게시판 정보 가져오기(목록에서 온라인강의 컨텐츠 정보 부려줄용)
	public List<Map<String, Object>> getOnlineStudyBoard(){
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		List<Map<String, Object>> list = dao.getOnlineStudyBoard();
		return list;
	}
	
	
	//l_seq를 뽑은 다음 강의정보 select
	@Transactional
	public Map<String, Object> onlineDetailInfo(String s_seq){
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		LectureDao dao2 = sqlsession.getMapper(LectureDao.class);
		 Map<String, Object> map = null;
		try {
			String l_seq = dao.getL_SEQ(s_seq);
			System.out.println("오오오 : " + l_seq);
			map = dao2.getLecture(l_seq);
		} catch (Exception e) {
			System.out.println("둘 중에 하나라도 문제가 생기면 예외가 떨어지는 부분" + e.getMessage());
			throw e; // 예외를 다시 돌려줌. 그리고 이 예외가 발생하는 시점에 transactionManager가 감시를 하다가 rollback 처리를 한다.
		}
		return map;

	}

	// 총 스터디게시글 수
	public int getStudyBoardCount() throws ClassNotFoundException, SQLException {
		System.out.println("총 게시글수임-serviceImpl오냐");

		// 여기까지는 오네
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		System.out.println("총게시글 수임-매퍼갔다오냐");

		int cnt = dao.getStudyBoardCount();
		System.out.println(cnt + ":총게시글수임-cnt찍냐");
		return cnt;
	}
	
	
	
	
	// 총 스터디게시글 수
//	public int getStudyBoardCount(Criteria_Board cri_b) throws ClassNotFoundException, SQLException {
//		System.out.println("serviceImpl오냐");
//
//		// 여기까지는 오네
//		BoardDao dao = sqlsession.getMapper(BoardDao.class);
//		System.out.println("매퍼갔다오냐");
//
//		int cnt = dao.getStudyBoardCount(cri_b);
//		System.out.println(cnt + "cnt찍냐");
//		return cnt;
//	}
	
	

	// 스터디 글 등록(일반 컨텐츠)
	public int studyReg(Study study, HttpServletRequest request, Principal principal) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		System.out.println("조ㅓㅁ와라조모!!!!");
		System.out.println("철이는 나빠ㅣ용" + study.getImage());
		
		List<CommonsMultipartFile> files = study.getFiles();
		List<String> filenames = new ArrayList<String>(); // 파일명관리
		System.out.println("?? " + files);
		System.out.println("-------- 1 ----------");
		int count = 0;
		System.out.println("-------- 2 ----------");
		String deadline = study.getSelectend();
		System.out.println("-------- 3 ----------");
		System.out.println(deadline);
		System.out.println("-------- 4 ----------");
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
	

	// 일반컨텐츠(스터디 게시판 글 편집) 
	@Override
	public int studyNormalEdit(Study study, HttpServletRequest request, Principal principal) {
		
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		System.out.println("스터디 일반 편집");
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
			int result = dao.studyEdit(study);
			System.out.println("여긴오니22?");
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("삽입 에러");
		}
		
		return 0;
		
	}
	
	
	@Transactional
	// 스터디 글 등록(온라인 컨텐츠)
	public int studyOnlineReg(Study study, HttpServletRequest request, Principal principal) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		System.out.println("조ㅓㅁ와라조모!!!!");
		System.out.println("철이는 나빠ㅣ용" + study.getImage());
		System.out.println(study.getL_seq());
		int l_seq = study.getL_seq();
		int s_seq = study.getS_seq();
	
		HashMap<String, Object> map = new HashMap();
		map.put("s_seq",s_seq);
		map.put("l_seq",l_seq);
		
		
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
		study.setC_seq(1); //온라인강의 등록폼이니까 온라인 컨텐츠 정적 부여
		study.setFilesrc(filenames.get(0));
		System.out.println("1: " + study.getFilesrc());
		study.setFilesrc2(filenames.get(1));
		System.out.println("1: " + study.getFilesrc2());
		try {
			System.out.println("여긴오니?ㄴㄴㄴ");
			System.out.println("우철 : " + study);
			int result = dao.studyReg(study);
			int result2 = dao.insertStudyBoardOnline(map);
			System.out.println("여긴오니22?");
			System.out.println("board_online에 들어갔는지?" + result2);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("삽입 에러");
		}
		
		return 0;
	}

	
	// 스터디 글 상세보기 트랜잭션
	@Transactional
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
	/*************************************    댓글   시작  **********************************************/
	
	//댓글 등록하기 
	public void commentInsert(Comment cm) {
		System.out.println("commentInsert 왔어요");
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		
		//refer 넣어주기
		int r_refer = dao.getMaxRefer();
		cm.setR_refer(r_refer+1);
		dao.commentInsert(cm);
	}

	//해당글의 댓글 select
	public List<Map<String, Object>> getComment(String s_seq) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		List<Map<String, Object>> list = dao.getComment(Integer.parseInt(s_seq));
		System.out.println("select 하고 리턴갑니다(서비스)");
		System.out.println("2.서비스 댓글:"+list.toString());
		
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

		//1. depth 1로 주기 
		cm.setR_depth(1);
		
		
		//2. step 조회 
		
		int r_step  =  dao.getMaxStep(cm);
		System.out.println(r_step);
		

		cm.setR_step(r_step+1);
		
		//4.insert
		dao.reCommentInsert(cm);  
	}

	//부모댓글 refer 가져오기 
	public int getP_refer(String r_seq) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		int r_refer = dao.getP_refer(r_seq);
		
		return r_refer;
	}

	//r_exists 컬럼을 n으로 업데이트
	@Override
	public void updateR_exists(Comment cm) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		dao.updateR_exists(cm);
	}

	@Override
	public int checkApply(HashMap<String, String> map) {
		BoardDao dao = sqlsession.getMapper(BoardDao.class);
		int result = dao.checkApply(map);
		return result;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
