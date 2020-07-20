package kr.or.ns.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ns.page.PageMaker_Board;
import kr.or.ns.vo.Criteria_Board;

@Controller
@RequestMapping("/chat/")
public class ChatController {
	
	// 스터디목록 + 페이징
		@RequestMapping("roomlist.do")
		public String studyListPage(Criteria_Board cri_b, Model model) throws ClassNotFoundException, SQLException {
			System.out.println("채팅 페이지로 이동이동(연규가씀)");

			

			return "chat/roomlist"; 
		}


}
