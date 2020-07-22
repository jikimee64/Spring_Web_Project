package kr.or.ns.dao;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.or.ns.vo.Comment;
import kr.or.ns.vo.Criteria;
import kr.or.ns.vo.Criteria_Board;
import kr.or.ns.vo.Likes;
import kr.or.ns.vo.Study;


/*
인터페이스명 : BoardDao
버전 정보 v.1.0
마지막 업데이트 날짜 : 2020 - 07 - 01
작업자 : 박민혜

study_List 목록뿌리기 작업

*/

public interface BoardDao {
	
	//페이징 스터디 글목록(필터 적용)
	public List<Map<String, Object>> getStudyBoardListFilter(HashMap<String, Object> params);

	//페이징 스터디 글목록
	public List<Map<String, Object>> getStudyBoardList(Criteria_Board cri_b);
	
	//페이징 스터디 글목록
	public List<Map<String, Object>> getStudyBoardListSize(Criteria_Board cri_b);
	
	//study_board_online 게시판 정보 가져오기
	public List<Map<String, Object>> getOnlineStudyBoard();
	
	//강의글 번호 select 
	public String getL_SEQ(String s_seq);
	
	
	//총 스터디게시글 수
	public int getStudyBoardCount();
	
	//총 스터디게시글 수
//	public int getStudyBoardCount(Criteria_Board cri_b);
	
	//스터디 글 등록(공통)
	public int studyReg(Study study);
	
	//스터디 글 편집
	public int studyEdit(Study study);
	
	//스터디 글 상세보기
	public Map<String, Object> getStudy(String s_seq);
	
	//스터디 글 삭제
	public int delete(String s_seq);
	
	//댓글 개수
	public int getReplyCnt(String s_seq);
	
	//라이크테이블에 넣기 
	public void heartinsert(Likes like);
	
	//좋아요 유무 확인
	public int heartcheck(Likes like);
	
	//좋아요의 0/1 컬럼 확인
	public int heartnum(Likes like);
	
	//좋아요의 0/1 컬럼에 업데이트
	public void heartupdate(Likes like);
	
	//해당글의 좋아요 총 갯수 확인
	public int getLikeCnt(int s_seq);

	//댓글 insert 하는 함수
	public void commentInsert(Comment cm);

	//해당글의 댓글 select
	public List<Map<String, Object>> getComment(int parseInt);

	//해당글의 댓글 delete
	public void commentDelete(Comment cm);

	//댓글 update
	public void commentUpdate(Comment cm);

	//댓글 count
	public int countComment(Comment cm);

	//조회수
	public int updateReadNum(String s_seq);
	
	//대댓글 insert
	public void reCommentInsert(Comment cm);

	//Maxstep 조회하는 함수
	public int getMaxStep(Comment cm);

	//MaxRefer 조회하는 함수(댓글 인서트에서 사용)
	public int getMaxRefer();

	//부모 refer가져오기
	public int getP_refer(String r_seq);

	//r_exists 컬럼을 n으로 업데이트
	public void updateR_exists(Comment cm);
	
	//StudyBoardOnline에 강의 글번호 넣기
	public int insertStudyBoardOnline(HashMap<String, Object> map);

	//스터지모임에 지원했는지 여부 
	public int checkApply(HashMap<String, String> map);

	//아이디와 글번호로 게시글 정보 가져오기 
	public Study getInfos(String user_id, int s_seq);



}
