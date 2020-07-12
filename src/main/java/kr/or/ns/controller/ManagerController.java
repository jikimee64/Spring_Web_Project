package kr.or.ns.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ns.export.WriteListToExcelFile;
import kr.or.ns.service.ManagerServiceImpl;
import kr.or.ns.vo.Blame;
import kr.or.ns.vo.Users;

@Controller
@RequestMapping("/manager/")
public class ManagerController {

	@Autowired
	private ManagerServiceImpl service;

	/*
	 * @Autowired private ManagerService service;
	 */

	@RequestMapping("index.do")
	public String indexPage() {
		System.out.println("어드민대문이동");

		return "manager/index";
	}

//	회원정보 아이디 클릭시 상세보기
	@RequestMapping("board/member_Detail.do")
	public String memberDetailPage(Model model, String user_id) {
		System.out.println("우철 : " + user_id);

		Users member = service.getUsers(user_id);
		List<HashMap<String, String>> skill = service.getSkill(user_id);
		System.out.println("일스킬이 찍히나요?");
		model.addAttribute("member", member);
		System.out.println("우철입니다 " + member);
		model.addAttribute("skill", skill);
		System.out.println("이스킬이 찍히나용? : " + skill);
		return "manager/board/member_Detail";

	}
//	회원정보 상세에서 삭제버튼클릭시 삭제하기
	@RequestMapping("board/memberDel.do")
	public String memberDel(String user_id) {
		System.out.println("삭제 컨트롤러 찍히나요?");
		return service.memberDel(user_id);
	}
	
	

	// 회원관리 목록
	@RequestMapping("board/member_Management.do")
	public String memberManagementPage(Model model) {
		System.out.println("어드민 회원관리 테이블페이지이동");

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결

		List<Users> memberList = null;

		memberList = service.getMemberList(); // 회원목록
		model.addAttribute("memberList", memberList); // view까지 전달
		System.out.println("멤버리스트" + memberList);

		return "manager/board/member_Management";
	}

	
	// 회원관리 목록 엑셀뽑기-----------------------------------------------------------
	@RequestMapping("board/excelView.do")
//	public String excelMemberView(Model model) throws Exception {
		public ModelAndView excelMemberView(HttpServletRequest request) throws Exception {
		List<Users> memberList = null;
		memberList = service.getMemberPoiList(); //회원목록가져와서 memberList에 넣음
		
		//배포경로에 엑셀을 만들어서 다운하는 
		WriteListToExcelFile.writeMemberListToFile("회원관리_목록.xls", memberList,request);
		
		
//		return "manager/board/member_Management";
		return new ModelAndView("fileDownloadView", "downloadFile","");
	}

	
	//----------------------------------------------------------------------------
	@RequestMapping("board/report_Management.do")
	public String reportManagementPage(Model model,String bl_seq,String btc_seq) {
		System.out.println("어드민 회원관리 테이블페이지이동");


		List<Blame> blameList = null;
		blameList = service.getBlameList(bl_seq); // 블레임리스트
		model.addAttribute("blameList", blameList); // view까지 전달

		return "manager/board/report_Management";
	}
}
