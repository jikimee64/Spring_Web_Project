package kr.or.ns.service;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import kr.or.ns.vo.Comment;
import kr.or.ns.vo.Criteria;
import kr.or.ns.vo.Criteria_Board;
import kr.or.ns.vo.Likes;
import kr.or.ns.vo.Study;



/*
인터페이스명 : BoardService
버전 정보 v.1.0
마지막 업데이트 날짜 : 2020 - 07 - 01
작업자 : 박민혜

study_List 목록뿌리기 작업

*/

public interface BoardService {
	
	//페이징 스터디 글목록
	public List<Map<String, Object>> getStudyBoardList(Criteria_Board cri_b);
	
	//총 스터디게시글 수
	public int getStudyBoardCount() throws ClassNotFoundException, SQLException;
	
	//스터디 모집 글 작성하기
	public int studyReg(Study study, HttpServletRequest request, Principal principal);
	
	//온라인 컨텐츠(스터디 게시판 글 등록)
	public int studyOnlineReg(Study study, HttpServletRequest request, Principal principal);
	
	//일반 컨텐츠(스터디 게시판 글 편집)
	public int studyNormalEdit(Study study, HttpServletRequest request, Principal principal);
	
	//스터디 글 상세보기
	public Map<String, Object> getStudy(String s_seq);
	
	//스터디 글 삭제
	public int delete(String s_seq);
	
	//댓글 개수
	public int getReplyCnt(String s_seq);
	
	//좋아요 테이블에 좋아요 넣기 
	public void heartinsert(String user_id, String s_seq);
	
	//좋아요 0/1컬럼 체크
	public int heartnum(Likes like);
	
	//해당글의 좋아요 총 갯수 가져오기
	public int getLikeCnt(String s_seq);
	
	//댓글 등록하기 기능
	public void commentInsert(Comment cm);
	
	//해당글의 댓글 select
	public List<Map<String, Object>> getComment(String s_seq); 

	//해당글의 댓글 delete
	public void commentDelete(Comment cm) ;
	
	//댓글 수정해주는거
	public void commentUpdate(Comment cm);
	
	//댓글갯수
	public int countComment(Comment cm);
	
	//대댓글  insert
	public void reCommentInsert(Comment cm);
	
	//부모댓글 refer 가져오기 
	public int getP_refer(String r_seq);

	//r_exists 컬럼을 n으로 업데이트 
	public void updateR_exists(Comment cm);
}
