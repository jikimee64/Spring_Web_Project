package kr.or.ns.dao;

import java.security.Principal;
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
	
	//페이징 스터디 글목록
	public List<Map<String, Object>> getStudyBoardList(Criteria_Board cri_b);

	//총 스터디게시글 수
	public int getStudyBoardCount();
	
	//스터디 글 등록
	public int studyReg(Study study);

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

	//대댓글 insert
	public void reCommentInsert(Comment cm);

	//refer,depth 조회 하는 함수
	public Map<String, Integer> getReferDepth(Comment cm);

	//Maxstep 조회하는 함수
	public String getMaxStep(Comment cm);



}
