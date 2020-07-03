package kr.or.ns.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ns.page.PageMaker;
import kr.or.ns.service.LectureServiceImpl;
import kr.or.ns.vo.Criteria;

@Controller
@RequestMapping("/board/")
public class LectureController {
	
	
	@Autowired
	private LectureServiceImpl service;
	
	
	//스터디목록 + 페이징
	@RequestMapping("course_List.do")
	public String courseListPage(Criteria cri,Model model) {
		System.out.println("강좌페이지로 이동이동(연규가씀)");
		System.out.println(cri.getPage());
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.getLectureCount());
		
		//DAO받아오기 + 매퍼를 통한 인터페이스 연결
		System.out.println(cri.getPage());
		System.out.println(cri.getPageStart());
		System.out.println(cri.getPerPageNum());
		List<Map<String,Object>> list = null;
		list = service.getLectureList(cri);
		model.addAttribute("list",list); //view까지 전달(forward)
		model.addAttribute("pageMaker",pageMaker); 
		
		
		return "user/board/course_List"; //study_List.html
	}
	
	
	
	
	
	
//	@RequestMapping("course_List.do")
//	public String courseListPage(Model m odel) {
//		System.out.println("강좌페이지로 이동이동(연규가씀)");
//		List<Map<String,Object>> list = null;
//		list = service.getStudyBoardList(cri);
//		model.addAttribute("list",list); //view까지 전달(forward)
//		model.addAttribute("pageMaker",pageMaker); 
//		
//		System.out.println(list.toString());
//		System.out.println("찍어보자2");
//		return "/user/board/study_List"; //study_List.html
//		
//		
//		return "/user/board/course_List";
//	}
//	
}