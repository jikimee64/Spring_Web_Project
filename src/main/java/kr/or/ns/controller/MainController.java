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
	
	
	@RequestMapping("main.do")
	public String mainPage(Model model) {
		System.out.println("메인으로 이동이동(연규가씀)");
		
		List<Map<String, Object>> mainList = mainservice.getNewListStudy();
		model.addAttribute("mainList", mainList);
		System.out.println("우철이는 천재빡빢이 : " + mainList);
	
		return "user/main"; 
	}
}
