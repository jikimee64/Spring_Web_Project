package kr.or.ns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ns.service.BoardServiceImpl;

@Controller
@RequestMapping("/board/")
public class LectureController {
	
	
	@Autowired
	private BoardServiceImpl service;
	
	

	@RequestMapping("course_List.do")
	public String courseListPage() {
		System.out.println("강좌페이지로 이동이동(연규가씀)");

		return "/user/board/course_List";
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