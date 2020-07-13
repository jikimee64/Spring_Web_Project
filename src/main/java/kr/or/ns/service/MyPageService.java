package kr.or.ns.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import kr.or.ns.vo.BookMark;
import kr.or.ns.vo.Users;

public interface MyPageService {

	// 회원정보 가져오기
	public Users getUsers(String userid);

	// 회원정보(스킬) 가져오기
	public List<HashMap<String, String>> getSkill(String userid);

	// 회원정보 수정
	public int MyPageUserEdit(Users user, HttpServletRequest request);

	// 회원탈퇴
	public void userDelete(String user);

	// 마이페이지(북마크 가져오기)
	public List<Map<String, Object>> myPagelist(String userid);

	// 마이페이지(사용자 정보 가져오기)
	public Users userInfo(String user);

	// 마이페이지(북마크 갯수 카운트)
	public int bookmarkCount(String users);

	// 마이페이지(댓글 갯수 카운트)
	public int commentCount(String users);

	// 마이페이지(스터디 게시판 게시글 카운트)
	public int s_boardCount(String users);

	// 마이페이지(스터디 리스트)
	public List<HashMap<String, Object>> myPageStudyList(String userid);
	
	//마이페이지 참여유저 현황 보여주기
	public List<HashMap<String, Object>> studyStatus(String name);
	
	//
	public String getRole(String user_id, String s_seq);

}
