package kr.or.ns.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ns.service.MainServiceImpl;

@Controller
@RequestMapping("/user/")
public class MainController {

	@Autowired
	private MainServiceImpl mainservice;
	
	//메인 화면에서 모집중인 스터디 목록 뿌려주는 함수
	@RequestMapping("main.do")
	public String mainPage(Model model) {
		
		List<Map<String, Object>> mainList = mainservice.getNewListStudy();
		model.addAttribute("mainList", mainList);
		
		return "user/main"; 
	}
}
