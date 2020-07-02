package kr.or.ns.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ns.page.PageMaker;
import kr.or.ns.service.BoardServiceImpl;
import kr.or.ns.vo.Criteria;




/*
클래스명 : BoardController
버전 정보 v.1.0
마지막 업데이트 날짜 : 2020 - 07 - 01
작업자 : 박민혜

study_List 목록뿌리기 작업

*/







@Controller
@RequestMapping("/board/")
public class BoardController {
	
	
	@Autowired
	private BoardServiceImpl service;
	
	

	@RequestMapping("course_List.do")
	public String courseListPage() {
		System.out.println("강좌페이지로 이동이동(연규가씀)");
		
		return "/user/board/course_List"; 
	}
	
	
	
	
	
	
	//스터디목록 + 페이징
	@RequestMapping("study_List.do")
	public String studyListPage(Criteria cri, Model model) throws ClassNotFoundException, SQLException {
		System.out.println("스터디리스트페이지로 이동이동(연규가씀)");
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		
		
		//서비스를 안가는ㄴㄴㄴ구먼........................
		
		System.out.println("서비스 가냐");
		pageMaker.setTotalCount(service.getStudyBoardCount());
		
		System.out.println("서비스 갔다오냐");
		
		//DAO받아오기 + 매퍼를 통한 인터페이스 연결
		List<Map<String,Object>> list = null;
		list = service.getStudyBoardList(cri);
		model.addAttribute("list",list); //view까지 전달(forward)
		model.addAttribute("pageMaker",pageMaker); 
		
		System.out.println(list.toString());
		System.out.println("찍어보자2");
		return "/user/board/study_List"; //study_List.html
	}
	
	
	
	
	
	
	
	
	
	@RequestMapping("board_Select.do")
	public String boardSelectPage() {
		System.out.println("온라인인지 일반인지 셀렉트하는 페이지로 이동이동(연규가씀)");
		
		return "/user/board/board_Select"; 
	}
	
	@RequestMapping("board_Select_Online_Bookmark.do")
	public String boardSelectOnlinePage() {
		System.out.println("온라인강의 선택페이지(북마크)로 이동이동(연규가씀)");
		
		return "/user/board/board_Select_Online_Bookmark"; 
	}
	
	@RequestMapping("writing_Course_Study.do")
	public String writingCourseStudyPage() {
		System.out.println("온라인강의 선택페이지(북마크)에서 다음페이지인 강의전용 글쓰기페이지로 이동이동(연규가씀)");
		
		return "/user/board/writing_Course_Study"; 
	}
	@RequestMapping("writing_Normal_Study.do")
	public String writingNormalStudyPage() {
		System.out.println("셀렉트하는 페이지에서 일반전용 글쓰기 페이지로 이동이동(연규가씀)");
		
		return "/user/board/writing_Normal_Study"; 
	}
	

	@RequestMapping("writing_Course_Study_Detail.do")
	public String writingCourseStudyDetailPage() {
		System.out.println("강의게시판에서 리스트에 있는거 클릭시 디테일 페이지로 이동이동(연규가씀)");
		
		return "/user/board/writing_Course_Study_Detail"; 
	}
	@RequestMapping("writing_Normal_Study_Detail.do")
	public String writingNormalStudyDetailPage() {
		System.out.println("일반게시판에서 리스트에 있는거 클릭시 디테일 페이지로 이동이동(연규가씀)");
		
		return "/user/board/writing_Normal_Study_Detail"; 
	}
	@RequestMapping("writing_Course_Study_Edit.do")
	public String writingCourseStudyEditPage() {
		System.out.println("강의게시판 상세페이지에서 본인이 쓴글을 수정하는 페이지로 이동이동(연규가씀)");
		
		return "/user/board/writing_Course_Study_Edit"; 
	}
	@RequestMapping("writing_Normal_Study_Edit.do")
	public String writingNormalStudyEditPage() {
		System.out.println("일반게시판 상세페이지에서 본인이 쓴글을 수정하는 페이지로 이동이동(연규가씀)");
		
		return "/user/board/writing_Normal_Study_Edit"; 
	}
}
