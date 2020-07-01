package kr.or.ns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board/")
public class BoardController {

	@RequestMapping("course_List.do")
	public String courseListPage() {
		System.out.println("강좌페이지로 이동이동(연규가씀)");
		
		return "/user/board/course_List"; 
	}
	
	@RequestMapping("study_List.do")
	public String studyListPage() {
		System.out.println("스터디리스트페이지로 이동이동(연규가씀)");
		
		return "/user/board/study_List"; 
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
