package kr.or.ns.controller;



import java.security.Principal;
import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import kr.or.ns.page.PageMaker_Board;
import kr.or.ns.service.BoardServiceImpl;

import kr.or.ns.vo.Criteria_Board;
import kr.or.ns.vo.Study;


/*
클래스명 : BoardController
버전 정보 v.1.1
마지막 업데이트 날짜 : 2020 - 07 - 02
작업자 : 박민혜

study_List 목록뿌리기 작업

*/

@Controller
@RequestMapping("/board/")
public class BoardController {

	@Autowired
	private BoardServiceImpl service;

	// 스터디목록 + 페이징
	@RequestMapping("study_List.do")
	public String studyListPage(Criteria_Board cri_b, Model model) throws ClassNotFoundException, SQLException {
		System.out.println("스터디리스트페이지로 이동이동(연규가씀)");

		PageMaker_Board pageMakerb = new PageMaker_Board();
		pageMakerb.setCri_b(cri_b);

		// 서비스를 안가는ㄴㄴㄴ구먼........................

		System.out.println("서비스 가냐");
		pageMakerb.setTotalCount(service.getStudyBoardCount());

		System.out.println("서비스 갔다오냐");

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결
		List<Map<String, Object>> list = null;
		list = service.getStudyBoardList(cri_b);
		model.addAttribute("list", list); // view까지 전달(forward)
		System.out.println("출력해라!!" + list);
		System.out.println("리스트 크기 : " + list.size());

		model.addAttribute("pageMakerb", pageMakerb);
		
		System.out.println("우철우철 : " + pageMakerb.toString());
		
		System.out.println("pageMagerb오냐 " + pageMakerb.isNext());

		System.out.println(list.toString());
		System.out.println("컨트롤러2");

		return "user/board/study_List"; // study_List.html
	}

	// 스터디 글 등록
	@RequestMapping(value = "register.do", method = RequestMethod.POST)
	public String boardRegister(Study study, HttpServletRequest request, Principal principal) {

		System.out.println("넘어온 데이터 " + study.toString());

		try {
			// 서비스가서 DB에 등록
			service.studyReg(study, request, principal);
		} catch (Exception e) {
			System.out.println("컨트롤러 에러");
			System.out.println(e.getMessage());

		}
		System.out.println("리턴 전...");

		//return "user/board/study_List";
		 return "redirect:/board/study_List.do";
		// /index.htm
		// return "redirect:index.do"; // /index.htm
		// return "redirect:study_List.do"; // /index.htm

		/*
		 * return "redirect:/index.do"; // /index.htm
		 */ // 주의사항
		// 요청 주소 ...아래처럼 ..
		// http://localhost:8090/SpringMVC_Basic06_WebSite_Annotation_JdbcTemplate/index.htm
		// return "redirect:noticeDetail.htm?seq="+n.getSeq();
	}

	@RequestMapping("board_Select.do")
	public String boardSelectPage() {
		System.out.println("온라인인지 일반인지 셀렉트하는 페이지로 이동이동(연규가씀)");

		return "user/board/board_Select";
	}

	@RequestMapping("writing_Normal_Study.do")
	public String writingNormalStudyPage() {
		System.out.println("셀렉트하는 페이지에서 일반전용 글쓰기 페이지로 이동이동(연규가씀)");

		return "user/board/writing_Normal_Study";
	}

	@RequestMapping("writing_Normal_Study_Detail.do")
	public String writingNormalStudyDetailPage(String s_seq, String page, String perPageNum, Model model, Principal principal) {

		Map<String, Object> study = service.getStudy(s_seq);
		model.addAttribute("study", study);
		model.addAttribute("page", page);
		model.addAttribute("perPageNum", perPageNum);

		int count = service.getReplyCnt(s_seq);
		model.addAttribute("count", count);
		model.addAttribute("sessionid", principal.getName());

		System.out.println("목록 -> 일반 : " + study);

		System.out.println("일반게시판에서 리스트에 있는거 클릭시 디테일 페이지로 이동이동(연규가씀)");

		return "user/board/writing_Normal_Study_Detail";
	}

	//글 수정 페이지 이동
	@RequestMapping("writing_Normal_Study_Edit.do")
	public String writingNormalStudyEditPage(String s_seq, Model model) {
		System.out.println("일반게시판 상세페이지에서 본인이 쓴글을 수정하는 페이지로 이동이동(연규가씀)");
		Map<String, Object> study = service.getStudy(s_seq);
		model.addAttribute("study", study);
		System.out.println("study" + study);
		
		return "user/board/writing_Normal_Study_Edit";
	}
	
	//글 수정 로직
	@RequestMapping(value= "writing_Normal_Study_Edit.do", method = RequestMethod.POST)
	public String writingNormalStudyEdit() {
		System.out.println("일반게시판 상세페이지에서 본인이 쓴글을 수정하는 페이지로 이동이동(연규가씀)");

		return "user/board/writing_Normal_Study_Edit";
	}

	@RequestMapping("writing_Normal_Study_Delete.do")
	public String writingNormalStudyDelete(Criteria_Board cri_b, Model model, String s_seq)
			throws ClassNotFoundException, SQLException {
		System.out.println("스터디리스트페이지로 이동이동(연규가씀)");

		//게시글 삭제
		int result = service.delete(s_seq);

		PageMaker_Board pageMakerb = new PageMaker_Board();
		pageMakerb.setCri_b(cri_b);

		pageMakerb.setTotalCount(service.getStudyBoardCount());

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결
		List<Map<String, Object>> list = null;
		list = service.getStudyBoardList(cri_b);
		model.addAttribute("list", list); // view까지 전달(forward)
		model.addAttribute("pageMakerb", pageMakerb);

		return "user/board/study_List";
	}

	@RequestMapping("board_Support_Status.do")
	public String boardSupportStatusPage() {
		System.out.println("일반게시판 상세페이지에서 본인이 쓴글을 수정하는 페이지로 이동이동(연규가씀)");

		return "user/board/board_Support_Status";
	}

	
}
