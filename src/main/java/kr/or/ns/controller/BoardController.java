package kr.or.ns.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.ns.page.PageMaker_Board;
import kr.or.ns.service.BoardServiceImpl;
import kr.or.ns.vo.Comment;
import kr.or.ns.vo.Criteria_Board;
import kr.or.ns.vo.Likes;
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

		pageMakerb.setTotalCount(service.getStudyBoardCount());

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결
		List<Map<String, Object>> list = null;
		list = service.getStudyBoardList(cri_b);
		model.addAttribute("list", list); // view까지 전달(forward)
		model.addAttribute("pageMakerb", pageMakerb);

		return "user/board/study_List"; // study_List.html
	}

	// 일반컨텐츠(스터디 게시판 글 등록)
	@RequestMapping(value = "register.do", method = RequestMethod.POST)
	public String boardRegister(Study study, HttpServletRequest request, Principal principal) {

		System.out.println("넘어온 데이터 " + study.toString());

		try {
			// 서비스가서 DB에 등록
			System.out.println("서비스는 잘가냐 ?");
			service.studyReg(study, request, principal);
		} catch (Exception e) {
			System.out.println("컨트롤러 에러");
			System.out.println(e.getMessage());

		}
		System.out.println("리턴 전...");

		// return "user/board/study_List";
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

	// 온라인컨텐츠(스터디 게시판 글 등록)
	@RequestMapping(value = "registerOnline.do", method = RequestMethod.POST)
	public String registerOnline(Study study, Principal principal, HttpServletRequest request) {

		System.out.println("온라인입니다 고갱님^^");
		System.out.println("넘어온 데이터 " + study.toString());

		try {
			// 서비스가서 DB에 등록
			System.out.println("서비스는 잘가냐 ?");
			service.studyOnlineReg(study, request, principal);
		} catch (Exception e) {
			System.out.println("컨트롤러 에러");
			System.out.println(e.getMessage());

		}
		System.out.println("리턴 전...");

		// return "user/board/study_List";
		return "redirect:/board/study_List.do";
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

	@RequestMapping("writing_Common_Study_Detail.do")
	public String writingNormalStudyDetailPage(String s_seq, String page, String perPageNum, Model model,
			Principal principal) {
		String user_id = principal.getName();
		Likes like = new Likes();
		like.setS_seq(Integer.parseInt(s_seq));
		like.setUser_id(user_id);
		//좋아요 0/1 중 뭔지 알아오기
		int heart = service.heartnum(like);
				
		Map<String, Object> study = service.getStudy(s_seq);
		model.addAttribute("study", study);
		model.addAttribute("page", page);
		model.addAttribute("perPageNum", perPageNum);
		
		 List<Map<String,Object>> commentList = service.getComment(s_seq); 
		int count = service.getReplyCnt(s_seq);
		model.addAttribute("count", count);
		model.addAttribute("sessionid", user_id);
		model.addAttribute("heart", heart);
		model.addAttribute("commentList", commentList);
		System.out.println("우철이는 : " + commentList);
		System.out.println("목록 -> 일반 ************: " + study);

		System.out.println("일반게시판에서 리스트에 있는거 클릭시 디테일 페이지로 이동이동(연규가씀)");

		return "user/board/writing_Common_Study_Detail";
	}

	// 글 수정 페이지 이동
	@RequestMapping("writing_Normal_Study_Edit.do")
	public String writingNormalStudyEditPage(String s_seq, Model model) {
		System.out.println("일반게시판 상세페이지에서 본인이 쓴글을 수정하는 페이지로 이동이동(연규가씀)");
		Map<String, Object> study = service.getStudy(s_seq);
		model.addAttribute("study", study);
		System.out.println("study" + study);

		return "user/board/writing_Normal_Study_Edit";
	}

	// 글 수정 로직
	@RequestMapping(value = "writing_Normal_Study_Edit.do", method = RequestMethod.POST)
	public String writingNormalStudyEdit() {
		System.out.println("일반게시판 상세페이지에서 본인이 쓴글을 수정하는 페이지로 이동이동(연규가씀)");

		return "user/board/writing_Normal_Study_Edit";
	}

	@RequestMapping("writing_Normal_Study_Delete.do")
	public String writingNormalStudyDelete(Criteria_Board cri_b, Model model, String s_seq)
			throws ClassNotFoundException, SQLException {
		System.out.println("스터디리스트페이지로 이동이동(연규가씀)");

		// 게시글 삭제
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
	//좋아요 넣는 로직
	@RequestMapping(value = "heart.do", method = RequestMethod.POST)
	@ResponseBody
	public int heartinsert(@RequestBody Map<String, Object> params, Principal principal) throws IOException {
		String user_id = principal.getName();
		String s_seq = (String) params.get("s_seq");
		System.out.println(user_id);
		System.out.println(s_seq);

		try {
			service.heartinsert(user_id, s_seq);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//해당 게시글 좋아요 총 갯수 반환
		int result = service.getLikeCnt(s_seq);
		return result;
	}
	
	//댓글 넣는 로직
	@RequestMapping(value = "InsertComment.do" , method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> commentInsert(@RequestBody Map<String, Object> params, Principal principal) throws IOException {
		String user_id = principal.getName();
		String s_seq = (String) params.get("s_seq");
		String r_content = (String) params.get("r_content");
		
		Comment cm = new Comment();
		cm.setS_seq(Integer.parseInt(s_seq));
		cm.setR_content(r_content);
		cm.setR_name(user_id);
		
		service.commentInsert(cm);

		
		List<Map<String,Object>> commentList = service.getComment(s_seq); 
		
		return commentList;
	}
	
	//댓글 지우는 로직
	@RequestMapping(value = "DeleteComment.do" , method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> commentDelete(@RequestBody Map<String, Object> params, Principal principal) throws IOException {
		String user_id = principal.getName();
		String s_seq = (String) params.get("s_seq");
		String r_seq = (String) params.get("r_seq");

		Comment cm = new Comment();
		cm.setS_seq(Integer.parseInt(s_seq));
		cm.setR_seq(Integer.parseInt(r_seq));
		
		service.commentDelete(cm);
		
		List<Map<String,Object>> commentList = service.getComment(s_seq); 
		return commentList;

	}
	
	//댓글 가져오는 함수
	@RequestMapping(value = "SelectCommentList.do" , method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getCommentList(@RequestBody Map<String, Object> params, Principal principal) throws IOException {
		String s_seq = (String) params.get("s_seq");
		
		
		List<Map<String,Object>> commentList = service.getComment(s_seq); 
		System.out.println("----commentList 찍어보기----"+commentList);
		
		return commentList;
		
	}
	
	//댓글 수정 로직
		@RequestMapping(value = "UpdateComment.do" , method = RequestMethod.POST)
		@ResponseBody
		public List<Map<String,Object>> commentUpdate(@RequestBody Map<String, Object> params, Principal principal) throws IOException {
			String s_seq = (String) params.get("s_seq");
			String r_content = (String) params.get("r_content");
			String r_seq = (String) params.get("r_seq");
			
			Comment cm = new Comment();
			cm.setS_seq(Integer.parseInt(s_seq));
			cm.setR_seq(Integer.parseInt(r_seq));
			cm.setR_content(r_content);
			System.out.println(cm.toString());
			service.commentUpdate(cm);
			
			System.out.println("수정 로직 입성!");

			List<Map<String,Object>> commentList = service.getComment(s_seq); 
			return commentList;
		}
		
		//댓글 갯수 세기
		@RequestMapping(value = "countComment.do" , method = RequestMethod.POST)
		@ResponseBody
		public int countComment(@RequestBody Map<String, Object> params, Principal principal) throws IOException {
			String s_seq = (String) params.get("s_seq");
			
			Comment cm = new Comment();
			cm.setS_seq(Integer.parseInt(s_seq));
			int result = service.countComment(cm);
			
			System.out.println("**********************************************");
			
			return result;
		}
	

}