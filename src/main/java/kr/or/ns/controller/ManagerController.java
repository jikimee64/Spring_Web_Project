package kr.or.ns.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	@RequestMapping("board/excelDown.do")
	public String excelDown(Model model, HttpServletResponse response) throws Exception {
		System.out.println("회원 목록을 excel로 뽑아요");

		List<Users> memberList = null;
		memberList = service.getMemberList(); //회원목록가져와서 memberList에 넣음
		
		model.addAttribute("memberList", memberList); // view까지 전달
		/* WriteListToExcelFile.writeMemberListToFile("cordova.xls", memberList); */
		// cordova는 대체 무엇인고
		
//		WriteListToExcelFile.writeMemberListToFile("cordova.xls", memberList);
		
		
		
		
		//회원목록 엑셀
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("회원목록");
		Row row = null;
		Cell cell = null;
		int rowNo = 0;
		
		
		//테이블 헤더용 스타일
		CellStyle headStyle = wb.createCellStyle();
		
		//가는 경계선 그림
		headStyle.setBorderTop(BorderStyle.THIN);
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBorderLeft(BorderStyle.THIN);
		headStyle.setBorderRight(BorderStyle.THIN);
		
		//배경색 노랑
		headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
		headStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
		
		//데이터는 가운데 정렬합니다.
		headStyle.setAlignment(HorizontalAlignment.CEDTER);
		
		//데이터용 경계 스타일 테두리만 지정
		CellStyle bodyStyle = wb.createCellStyle();
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		bodyStyle.setBorderRight(BorderStyle.THIN);
		
		//헤더 생성
		row = sheet.createRow(rowNo++);
		cell = row.createCell(0);
		cell.setCellStyle(headStyle);
		cell.setCellValue("아이디");
		
		cell = row.createCell(1);
		cell.setCellStyle(headStyle);
		cell.setCellValue("이메일");
		
		cell = row.createCell(2);
		cell.setCellStyle(headStyle);
		cell.setCellValue("닉네임");
		
		cell = row.createCell(3);
		cell.setCellStyle(headStyle);
		cell.setCellValue("신고횟수");
		
		
		//데이터 부분 생성
		for(Users users : memberList) {
			row = sheet.createRow(rowNo++);
			cell=row.createCell(0);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(users.getUser_id());
			
			cell=row.createCell(1);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(users.getUser_email());
			
			cell=row.createCell(2);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(users.getNickname());
			
			cell=row.createCell(3);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(users.getBlame_count());
		}
		
		//컨텐츠 타입과 파일명 지정
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition","attachment;filenale=test.xls");;
		
		//엑셀출력
		wb.write(response.getOutputStream());
		wb.close();
		
		
		
		
		
		
		
	
		return "manager/board/member_Management";
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
