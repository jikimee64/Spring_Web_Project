package kr.or.ns.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;


import kr.or.ns.page.PageMaker_Board;
import kr.or.ns.service.AjaxService;
import kr.or.ns.service.BoardService;
import kr.or.ns.service.MyPageService;
import kr.or.ns.vo.Comment;
import kr.or.ns.vo.Criteria_Board;
import kr.or.ns.vo.Likes;
import kr.or.ns.vo.Study;
import kr.or.ns.vo.Users;

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
	private BoardService service;

	@Autowired
	private AjaxService aservice;

	@Autowired
	private MyPageService mservice;

/*
	*
    * @Method 설명 : 동기식으로 기존 스터디 목록 뿌려주기 or 필터 후 static으로 선언한 HashMap에 담긴 카테고리 조건값을 가져와 필터 후 데이터를 기준으로 검색 하는 함수  
    * @param searchType
    * @param keyword
    * @param root
    * @return view 주소
*/
	@RequestMapping("study_List.do")
	public String studyListPage(Criteria_Board cri_b, Model model, HttpServletRequest request,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "root", required = false) String root) throws ClassNotFoundException, SQLException {

		PageMaker_Board pageMakerb = new PageMaker_Board();
		pageMakerb.setCri_b(cri_b);

		List<String> keywordCollec = new ArrayList();
		if (keyword != null) {
			String[] ccc = keyword.split("\\+");
			for (int i = 0; i < ccc.length; i++) {
				keywordCollec.add(ccc[i]);
			}
		}

		List<Map<String, Object>> onlineInfo = service.getOnlineStudyBoard();

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결
		List<Map<String, Object>> list = null;
		HashMap<String, Object> map = null;

		cri_b.setKeyword(keyword);
		cri_b.setSearchType(searchType);

		String redirectStr = "";
		Map<String, Object> redirect = (Map<String, Object>) RequestContextUtils.getInputFlashMap(request);
		if (redirect != null) {
			redirectStr = (String) redirect.get("root");
		}

		if (root != null) {
			HashMap<String, Object> map4 = new HashMap();
			map4.put("keyword", keywordCollec);
			list = service.getStudyBoardList(cri_b, map4);
			if (keyword == null) { // 처음동기식으로 왔을때
				pageMakerb.setTotalCount(service.getStudyBoardCount());
			} else if (root.equals("search")) { // 검색만 했을때(검색결과에 대한 사이즈 필요,리밋X)
				List<Map<String, Object>> listSize = service.getStudyBoardListSize(cri_b, map4);
				pageMakerb.setTotalCount(listSize.size());
				model.addAttribute("type", "Search");
				model.addAttribute("searchType", cri_b.getSearchType());
				model.addAttribute("keyword", cri_b.getKeyword());
			}

		} else {
			map = AjaxRestController.paramsTemp;
			map.put("keyword", keywordCollec);
			list = service.getStudyBoardListFilter(cri_b, map);
			List<Map<String, Object>> listSizeSearch = service.getStudyBoardListFilterSize(cri_b, map);
			model.addAttribute("ingOrDone", map.get("ingOrDone"));
			model.addAttribute("level", map.get("level"));
			model.addAttribute("language", map.get("language"));
			model.addAttribute("local", map.get("local"));
			model.addAttribute("studyContent", map.get("studyContent"));
			model.addAttribute("type", "FS");
			model.addAttribute("searchType", cri_b.getSearchType());
			model.addAttribute("keyword", cri_b.getKeyword());
			pageMakerb.setTotalCount(listSizeSearch.size());
			AjaxRestController.filterSize = 0;
		}
		model.addAttribute("list", list); // view까지 전달(forward)
		model.addAttribute("onlineInfo", onlineInfo); // view까지 전달(forward)
		model.addAttribute("pageMakerb", pageMakerb);

		return "user/board/study_List"; // study_List.html
	}

	/*
	    * @Method 설명 : 필터 후 static으로 선언한 HashMap에 담긴 카테고리 조건값을 가져와 지정한 카테고리에 해당하는 데이터를 가져오는 함수   
	    * @param cri_b
	    * @param model
	    * @return view 주소
    */
	@RequestMapping("study_FilterList.do")
	public String studyListFilterPage(Criteria_Board cri_b, Model model) throws ClassNotFoundException, SQLException {

		PageMaker_Board pageMakerb = new PageMaker_Board();
		pageMakerb.setCri_b(cri_b);

		List<Map<String, Object>> onlineInfo = service.getOnlineStudyBoard();

		HashMap<String, Object> map = AjaxRestController.paramsTemp;
		int filterSize = AjaxRestController.filterSize;

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결
		List<HashMap<String, Object>> list = aservice.studyBoardFilter(map, cri_b);

		pageMakerb.setTotalCount(filterSize);

		model.addAttribute("list", list); // view까지 전달(forward)
		model.addAttribute("onlineInfo", onlineInfo); // view까지 전달(forward)
		model.addAttribute("pageMakerb", pageMakerb);

		model.addAttribute("ingOrDone", map.get("ingOrDone"));
		model.addAttribute("level", map.get("level"));
		model.addAttribute("language", map.get("language"));
		model.addAttribute("local", map.get("local"));
		model.addAttribute("studyContent", map.get("studyContent"));

		model.addAttribute("type", "Search");

		model.addAttribute("type", "filter");

		return "user/board/study_List"; // study_List.html
	}

	// 일반컨텐츠(스터디 게시판 글 등록)
	@RequestMapping(value = "register.do", method = RequestMethod.POST)
	public String boardRegister(Study study, HttpServletRequest request, Principal principal,
			RedirectAttributes redirectAttr) {

		try {
			service.studyReg(study, request, principal);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		redirectAttr.addAttribute("root", "header");

		return "redirect:/board/study_List.do"; 
	}

	// 일반컨텐츠(스터디 게시판 글 편집)
	@RequestMapping(value = "writing_Normal_Study_Edit.do", method = RequestMethod.POST)
	public String writingNormalStudyEdit(String page, String perPageNum, RedirectAttributes redirect, Study study,
			Principal principal, HttpServletRequest request) {

		try {
			// 서비스가서 DB에 등록
			service.studyNormalEdit(study, request, principal);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		redirect.addAttribute("page", page);
		redirect.addAttribute("perPageNum", perPageNum);
		redirect.addAttribute("s_seq", study.getS_seq());

		return "redirect:writing_Common_Study_Detail.do";
	}

	// 온라인컨텐츠(스터디 게시판 글 등록)
	@RequestMapping(value = "registerOnline.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String registerOnline(RedirectAttributes redirectAttr, Study study, Principal principal,
			HttpServletRequest request) {

		study.setL_seq(Integer.parseInt(study.getL_seq_temp()));

		try {
			service.studyOnlineReg(study, request, principal);

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		redirectAttr.addAttribute("root", "header");
		return "";
	}

	//게시판 등록 전 일반 or 온라인 강의 컨텐츠 등록 페이지
	@RequestMapping("board_Select.do")
	public String boardSelectPage() {
		return "user/board/board_Select";
	}

	//일반 컨텐츠 스터디 등록 페이지 이동
	@RequestMapping("writing_Normal_Study.do")
	public String writingNormalStudyPage() {
		return "user/board/writing_Normal_Study";
	}

	// 스터디 게시글 상세보기
	@RequestMapping("writing_Common_Study_Detail.do")
	public String writingNormalStudyDetailPage(String s_seq, String page, String perPageNum, Model model,
			Principal principal) {

		String user_id = principal.getName();
		Likes like = new Likes();
		like.setS_seq(Integer.parseInt(s_seq));
		like.setUser_id(user_id);
		int heart = service.heartnum(like);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("s_seq", s_seq);
		map.put("user_id", user_id);
		int apply = service.checkApply(map);

		try {
			Map<String, Object> study = service.getStudy(s_seq);
			Map<String, Object> onlineInfo = service.onlineDetailInfo(s_seq);

			model.addAttribute("study", study);
			model.addAttribute("onlineInfo", onlineInfo);
			model.addAttribute("page", page);
			model.addAttribute("perPageNum", perPageNum);
			model.addAttribute("apply", apply);

			List<Map<String, Object>> commentList = service.getComment(s_seq);
			int count = service.getReplyCnt(s_seq);
			model.addAttribute("count", count);
			model.addAttribute("user_id", user_id);
			model.addAttribute("heart", heart);
			model.addAttribute("commentList", commentList);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "user/board/writing_Common_Study_Detail";
	}

	//	파일 다운로드 
	@RequestMapping("FileDownload.do")
	public ModelAndView FileDownload(String filesrc, HttpServletRequest request, Model model) {

		String filePath = request.getSession().getServletContext().getRealPath("/studyboard/upload/");

		File downloadFile = null;

		downloadFile = new File(filePath + filesrc);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("FileDownload"); // 뷰의 이름
		mv.addObject("downloadFile", downloadFile); // 뷰로 보낼 데이터 값

		return mv;
	}

	// 글 수정 페이지 이동
	@RequestMapping("writing_Normal_Study_Edit.do")
	public String writingNormalStudyEditPage(String s_seq, String page, String perPageNum, Model model) {
		Map<String, Object> study = service.getStudy(s_seq);
		model.addAttribute("study", study);
		model.addAttribute("page", page);
		model.addAttribute("perPageNum", perPageNum);

		return "user/board/writing_Normal_Study_Edit";
	}

	// 스터디 게시판 글 삭제
	@RequestMapping("writing_Common_Study_Delete.do")
	public String writingNormalStudyDelete(String page, String perPageNum, Model model, String s_seq,
			RedirectAttributes redirect) throws ClassNotFoundException, SQLException {
		int result = service.delete(s_seq);
		if (result % 10 == 0) {
			int ksk = Integer.parseInt(page);
			ksk--;
			page = Integer.toString(ksk);
		}

		redirect.addAttribute("root", "root");
		redirect.addAttribute("page", page);
		redirect.addAttribute("perPageNum", perPageNum);

		return "redirect:/board/study_List.do";

	}

	// 하트 누르면 좋아요 insert 하기
	@RequestMapping(value = "heart.do", method = RequestMethod.POST)
	@ResponseBody
	public int heartinsert(@RequestBody Map<String, Object> params, Principal principal) throws IOException {
		String user_id = principal.getName();
		String s_seq = (String) params.get("s_seq");

		try {
			service.heartinsert(user_id, s_seq);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// 해당 게시글 좋아요 총 갯수 반환
		int result = service.getLikeCnt(s_seq);
		return result;
	}

	// 댓글 넣는 로직
	@RequestMapping(value = "InsertComment.do", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> commentInsert(@RequestBody Map<String, Object> params, Principal principal)
			throws IOException {
		String user_id = principal.getName();
		Users user = mservice.getUsers(user_id);

		String s_seq = (String) params.get("s_seq");
		String r_content = (String) params.get("r_content");
		String r_nickname = user.getNickname();

		Comment cm = new Comment();
		cm.setS_seq(Integer.parseInt(s_seq));
		cm.setR_content(r_content);
		cm.setR_name(user_id);
		cm.setR_nickname(r_nickname);

		service.commentInsert(cm);

		List<Map<String, Object>> commentList = service.getComment(s_seq);
		return commentList;
	}

	// 댓글 지우는 로직
	@RequestMapping(value = "DeleteComment.do", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> commentDelete(@RequestBody Map<String, Object> params, Principal principal)
			throws IOException {
		String user_id = principal.getName();
		String s_seq = (String) params.get("s_seq");
		String r_seq = (String) params.get("r_seq");

		Comment cm = new Comment();
		cm.setS_seq(Integer.parseInt(s_seq));
		cm.setR_seq(Integer.parseInt(r_seq));

		service.updateR_exists(cm);

		List<Map<String, Object>> commentList = service.getComment(s_seq);
		return commentList;

	}

	// 댓글목록 가져오는 함수
	@RequestMapping(value = "SelectCommentList.do", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getCommentList(@RequestBody Map<String, Object> params, Principal principal)
			throws IOException {
		String s_seq = (String) params.get("s_seq");

		List<Map<String, Object>> commentList = service.getComment(s_seq);

		return commentList;

	}

	// 댓글 수정 로직
	@RequestMapping(value = "UpdateComment.do", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> commentUpdate(@RequestBody Map<String, Object> params, Principal principal)
			throws IOException {
		String s_seq = (String) params.get("s_seq");
		String r_content = (String) params.get("r_content");
		String r_seq = (String) params.get("r_seq");

		Comment cm = new Comment();
		cm.setS_seq(Integer.parseInt(s_seq));
		cm.setR_seq(Integer.parseInt(r_seq));
		cm.setR_content(r_content);
		service.commentUpdate(cm);

		List<Map<String, Object>> commentList = service.getComment(s_seq);
		return commentList;
	}

	// 해당글의 댓글 갯수 세기
	@RequestMapping(value = "countComment.do", method = RequestMethod.POST)
	@ResponseBody
	public int countComment(@RequestBody Map<String, Object> params, Principal principal) throws IOException {
		String s_seq = (String) params.get("s_seq");

		Comment cm = new Comment();
		cm.setS_seq(Integer.parseInt(s_seq));
		int result = service.countComment(cm);

		return result;
	}

	// "대" 댓글 넣는 로직
	@RequestMapping(value = "insertReComment.do", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> reCommentInsert(@RequestBody Map<String, Object> params, Principal principal)
			throws IOException {
		String user_id = principal.getName(); // 유저 아이디
		Users user = mservice.getUsers(user_id);
		String s_seq = (String) params.get("s_seq"); // 글 번호
		String r_seq = (String) params.get("r_seq"); // 부모글 번호
		String r_content = (String) params.get("r_content"); // 대댓글 내용
		String r_nickname = user.getNickname();

		Comment cm = new Comment();
		cm.setR_name(user_id); // 아이디
		cm.setS_seq(Integer.parseInt(s_seq)); // 글번호
		cm.setR_content(r_content); // 대댓글내용
		cm.setR_nickname(r_nickname); // 닉네임

		int r_refer = service.getP_refer(r_seq); // 부모글의 r_refer
		cm.setR_refer(r_refer);// 그룹번호

		service.reCommentInsert(cm); // 인서트 하러가기

		List<Map<String, Object>> commentList = service.getComment(s_seq);

		return commentList;
	}

	// 마이페이지에서 상세보기 들어가기 (s_seq 만 잇으면 될듯?)
	@RequestMapping("my_Writing_Common_Study_Detail.do")
	public String myWritingStudyDetailPage(String s_seq, Model model, Principal principal) {

		String user_id = principal.getName();
		Likes like = new Likes();
		like.setS_seq(Integer.parseInt(s_seq));
		like.setUser_id(user_id);
		int heart = service.heartnum(like);

		// 트랜잭션 처리
		try {
			Map<String, Object> study = service.getStudy(s_seq);
			Map<String, Object> onlineInfo = service.onlineDetailInfo(s_seq);
			model.addAttribute("study", study);
			model.addAttribute("onlineInfo", onlineInfo);
			model.addAttribute("page", 1);
			model.addAttribute("perPageNum", 10);

			List<Map<String, Object>> commentList = service.getComment(s_seq);
			int count = service.getReplyCnt(s_seq);
			model.addAttribute("count", count);
			model.addAttribute("sessionid", user_id);
			model.addAttribute("heart", heart);
			model.addAttribute("commentList", commentList);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "user/board/writing_Common_Study_Detail";
	}

	// 썸머노트 이미지 넣는 함수
	@RequestMapping(value = "ProfileImage.do", method = RequestMethod.POST)
	@ResponseBody
	public void profileUpload(MultipartFile file, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		PrintWriter out = response.getWriter();
		String realFolder = "C:\\summernote\\";

		UUID uuid = UUID.randomUUID(); // 랜덤한키 생성해주는 객체

		String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String storedFileName = uuid.toString().replaceAll("-", "") + fileExtension;

		File f = new File(realFolder + storedFileName);
		if (!f.exists()) {
			f.mkdirs(); // 존재하지 않으면 경로에 폴더를 생성해서 만들어준다.
		}
		Boolean a = f.isAbsolute();
		Boolean b = f.canExecute();

		file.transferTo(f);

		int as = storedFileName.lastIndexOf(".");
		String bs = storedFileName.substring(0, as);

		response.setContentType("text/html;charset=utf-8");
		out.println("/filepath/" + storedFileName);
		out.close();
	}

}