package kr.or.ns.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ns.export.WriteListToExcelFile;
import kr.or.ns.service.ManagerServiceImpl;
import kr.or.ns.vo.Users;

@Controller
@RequestMapping("/manager/")
public class ManagerController {
	
	/*
	 * @Autowired private ManagerService service;
	 */
	
	
	@RequestMapping("index.do")
	public String indexPage() {
		System.out.println("어드민대문이동");

		return "manager/index"; 
	}
	
	@RequestMapping("board/member_Detail.do")
	public String memberDetailPage() {
		System.out.println("sss");

		return "manager/board/member_Detail"; 
	}
	
	
	
	
	
	
	//회원관리 목록
	@RequestMapping("board/member_Management.do")
	public String memberManagementPage() {
		System.out.println("어드민 회원관리 테이블페이지이동");

		return "manager/board/member_Management"; 
	}
	
	
	//회원관리 목록 엑셀뽑기
	public String excelMemberView(Model model) {
		System.out.println("회원 목록을 excel로 뽑아요");
		
		List<Users> memberList = null;
		memberList = service.getMemberList();  //회원목록 
		
		model.addAttribute("memberList",memberList); //view까지 전달
		/* WriteListToExcelFile.writeMemberListToFile("cordova.xls", memberList); */
        //cordova는 대체 무엇인고
        
        return "manager/board/member_Management"; 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("board/report_Management.do")
	public String reportManagementPage() {
		System.out.println("어드민 회원관리 테이블페이지이동");

		return "manager/board/report_Management"; 
	}
}
