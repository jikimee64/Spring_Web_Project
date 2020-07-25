package kr.or.ns.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.itextpdf.text.log.SysoLogger;
import com.sun.media.jfxmedia.logging.Logger;

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

	// 스터디목록 + 페이징
	@RequestMapping("study_List.do")
	public String studyListPage(Criteria_Board cri_b, Model model, 
			HttpServletRequest request,
			@RequestParam(value="searchType",required = false) String searchType,
			@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam(value="root",required=false) String root
			) throws ClassNotFoundException, SQLException {
		
		System.out.println(keyword +"keyword를 보드 컨트롤러에서 찍어봅니다. 룰루랄라");
		PageMaker_Board pageMakerb = new PageMaker_Board();
		pageMakerb.setCri_b(cri_b);
	
		/////////////////////////////////////////////////////////////////////////////
		System.out.println(keyword+"courseListPage  에서 찍어보기 ");
		List<String> keywordCollec = new ArrayList();
		if(keyword != null) {
		String[] ccc = keyword.split("\\+");
		for( int i=0; i<ccc.length; i++) {
		keywordCollec.add(ccc[i]);
		}
		}
		System.out.println("keywordCollec    "+keywordCollec+ "  이렇게 나와요");
		///////////////////////////////////////////////////////////////////////////////
		
		List<Map<String, Object>> onlineInfo = service.getOnlineStudyBoard();
		System.out.println("온라인 강의 : " + onlineInfo);

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결
		List<Map<String, Object>> list = null;
		HashMap<String, Object> map = null;
		
		cri_b.setKeyword(keyword);
		cri_b.setSearchType(searchType);
		
		String redirectStr = "";
		Map<String, Object> redirect = (Map<String, Object>) RequestContextUtils.getInputFlashMap(request);
		System.out.println("이게왜널? " + redirect);
		if(redirect != null) {
			redirectStr = (String)redirect.get("root");
			System.out.println("호호ss: " + redirectStr);
		}
		
		System.out.println("keyword : " + keyword);
		System.out.println("searchType : " + searchType);
		if(root != null) {
			HashMap<String, Object> map4 = new HashMap();
			map4.put("keyword",keywordCollec );
			list = service.getStudyBoardList(cri_b,map4);
			System.out.println("호호 : " + redirectStr);
			if(keyword == null) { //처음동기식으로 왔을때
				System.out.println("처음엔 여길..");
				pageMakerb.setTotalCount(service.getStudyBoardCount());
			}else if(root.equals("search")){ //검색만 했을때(검색결과에 대한 사이즈 필요,리밋X)
				System.out.println("..");
				List<Map<String, Object>> listSize = service.getStudyBoardListSize(cri_b,map4);
				pageMakerb.setTotalCount(listSize.size());
				model.addAttribute("type", "Search");
				model.addAttribute("searchType", cri_b.getSearchType());
				model.addAttribute("keyword", cri_b.getKeyword());
			}
			
		}else {
			System.out.println("===============나 지금 엘스문 탄다 ===========");
			System.out.println("filterSize: " + AjaxRestController.filterSize);
			System.out.println("paramsTemp : " + AjaxRestController.paramsTemp);
			System.out.println("필터된 후엔 여길..");
			map = AjaxRestController.paramsTemp;
			map.put("keyword",keywordCollec );
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
			System.out.println("필터링+검색 사이즈 : " + listSizeSearch.size());
			pageMakerb.setTotalCount(listSizeSearch.size());
			AjaxRestController.filterSize = 0;
		}
		System.out.println("===============나 지금 리턴하러간다 ===========");
		/* pageMakerb.setTotalCount(list.size()); */
		model.addAttribute("list", list); // view까지 전달(forward)
		model.addAttribute("onlineInfo", onlineInfo); // view까지 전달(forward)
		System.out.println("넘기는 onlineInfo : " + onlineInfo);
		model.addAttribute("pageMakerb", pageMakerb);
		
		return "user/board/study_List"; // study_List.html
	}

	private List<Map<String, Object>> getStudyBoardListSize(Criteria_Board cri_b) {
		// TODO Auto-generated method stub
		return null;
	}

	@RequestMapping("study_FilterList.do")
	public String studyListFilterPage(Criteria_Board cri_b, Model model) throws ClassNotFoundException, SQLException {

		System.out.println("받은 page :" + cri_b.getPage());
		System.out.println("받은 perpagenum :" + cri_b.getPerPageNum());
		
		PageMaker_Board pageMakerb = new PageMaker_Board();
		pageMakerb.setCri_b(cri_b);

		// 서비스를 안가는ㄴㄴㄴ구먼........................

		// study_board_online에있는 모든정보도 보내야하나?

		List<Map<String, Object>> onlineInfo = service.getOnlineStudyBoard();

		HashMap<String, Object> map = AjaxRestController.paramsTemp;
		int filterSize = AjaxRestController.filterSize;

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결
		List<HashMap<String, Object>> list = aservice.studyBoardFilter(map, cri_b);
		
		System.out.println("전체필터개수 : " + list.size());
		
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
		
		System.out.println("시작 : " + pageMakerb.getStartPage());
		System.out.println("끝 : " + pageMakerb.getEndPage());
		
		model.addAttribute("type", "filter");
		

		return "user/board/study_List"; // study_List.html
	}

	// 일반컨텐츠(스터디 게시판 글 등록)
	@RequestMapping(value = "register.do", method = RequestMethod.POST)
	/* @ResponseBody */
	public String boardRegister(Study study, HttpServletRequest request, Principal principal,
			RedirectAttributes redirectAttr) {

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
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
		redirectAttr.addAttribute("root","header");
		// return "user/board/study_List";
		
		//return ""
		//return "sss";
		
		// /index.htm
		return "redirect:/board/study_List.do"; // /index.htm
		 //return ""; // /index.htm

		/*
		 * return "redirect:/index.do"; // /index.htm
		 */ // 주의사항
		// 요청 주소 ...아래처럼 ..
		// http://localhost:8090/SpringMVC_Basic06_WebSite_Annotation_JdbcTemplate/index.htm
		// return "redirect:noticeDetail.htm?seq="+n.getSeq();
	}

	// 일반컨텐츠(스터디 게시판 글 편집)
	@RequestMapping(value = "writing_Normal_Study_Edit.do", method = RequestMethod.POST)
	public String writingNormalStudyEdit(String page, String perPageNum, RedirectAttributes redirect, Study study, Principal principal,
			HttpServletRequest request) {
		System.out.println("일반게시글수정@@");
		System.out.println("넘어온 데이터22 " + study.toString());

		try {
			// 서비스가서 DB에 등록
			System.out.println("서비스는 잘가냐 ?");
			service.studyNormalEdit(study, request, principal);
		} catch (Exception e) {
			System.out.println("컨트롤러 에러");
			System.out.println(e.getMessage());
		}

		System.out.println("리턴 전ㄴㄴㄴㄴ...");

		redirect.addAttribute("page", page);    
		redirect.addAttribute("perPageNum", perPageNum);  
		redirect.addAttribute("s_seq", study.getS_seq());

		return "redirect:writing_Common_Study_Detail.do";
	}

	// 온라인컨텐츠(스터디 게시판 글 등록)
	@RequestMapping(value = "registerOnline.do", method = {RequestMethod.POST, RequestMethod.GET})
	 @ResponseBody 
	public String registerOnline(RedirectAttributes redirectAttr,
			Study study, Principal principal, HttpServletRequest request) {

		System.out.println("넘어온 데이터 " + study.toString());
		System.out.println("넘어온 강의번호 " + study.getL_seq_temp());
			
		study.setL_seq(Integer.parseInt(study.getL_seq_temp()));
		
		try {
			// 서비스가서 DB에 등록
			System.out.println("서비스는 잘가냐 ?");
			service.studyOnlineReg(study, request, principal);

		} catch (Exception e) {
			System.out.println("컨트롤러 에러");
			System.out.println(e.getMessage());

		}
		System.out.println("리턴 전...!!");
		redirectAttr.addAttribute("root","header");
		return "";
		// return "user/board/study_List";
		//return "redirect:/board/study_List.do";
		 //return "";
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

	// 상세보기
	@RequestMapping("writing_Common_Study_Detail.do")
	public String writingNormalStudyDetailPage(String s_seq, String page, String perPageNum, Model model,
			Principal principal) {

		System.out.println("s_seq 값 : " + s_seq);
		System.out.println("page 값 : " + page);
		System.out.println("perPageNum 값 : " + perPageNum);
		
		System.out.println("게시판 디테일 페이지 입니다.");
		String user_id = principal.getName();
		Likes like = new Likes();
		like.setS_seq(Integer.parseInt(s_seq));
		like.setUser_id(user_id);
		// 좋아요 0/1 중 뭔지 알아오기
		int heart = service.heartnum(like);
		
		//지원했는지 여부//insert 정보넘길 맵생성
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("s_seq", s_seq);
		map.put("user_id", user_id);
		int apply = service.checkApply(map);

		try {
			Map<String, Object> study = service.getStudy(s_seq);
			Map<String, Object> onlineInfo = service.onlineDetailInfo(s_seq);

			System.out.println("onlineinfo : " + onlineInfo);

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
			System.out.println("목록 -> 일반 ************: " + study);


			System.out.println("1.컨트롤러 댓글:" + commentList.toString());


			System.out.println("일반게시판에서 리스트에 있는거 클릭시 디테일 페이지로 이동이동(연규가씀)");
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
		System.out.println("일반게시판 상세페이지에서 본인이 쓴글을 수정하는 페이지로 이동이동(연규가씀)");
		Map<String, Object> study = service.getStudy(s_seq);
		model.addAttribute("study", study);
		model.addAttribute("page", page);
		model.addAttribute("perPageNum", perPageNum);
		System.out.println("study" + study);

		return "user/board/writing_Normal_Study_Edit";
	}

	// 스터디 게시판 글 삭제
	@RequestMapping("writing_Common_Study_Delete.do")
	public String writingNormalStudyDelete(String page, String perPageNum, Model model, String s_seq, RedirectAttributes redirect)
			throws ClassNotFoundException, SQLException {
		System.out.println("스터디리스트페이지로 이동이동(연규가씀)");
		//Criteria_Board cri_b = new Criteria_Board();
		// 게시글 삭제
		int result = service.delete(s_seq);
		System.out.println(result + "개가 되었습니다.");
		//PageMaker_Board pageMakerb = new PageMaker_Board();
		//pageMakerb.setCri_b(cri_b);

		
		//여기도 잠시 수정...삭제가 될랑가?
//		pageMakerb.setTotalCount(service.getStudyBoardCount(cri_b));
		//pageMakerb.setTotalCount(service.getStudyBoardCount());

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결
		//List<Map<String, Object>> list = null;
		//list = service.getStudyBoardList(cri_b);
		//model.addAttribute("list", list); // view까지 전달(forward)
		//model.addAttribute("pageMakerb", pageMakerb);
		if(result%10==0) {
			int ksk = Integer.parseInt(page);
			ksk--;
			page = Integer.toString(ksk);
		}
		
		redirect.addAttribute("root", "root"); 
		redirect.addAttribute("page", page);    
		redirect.addAttribute("perPageNum", perPageNum);  
		//redirect.addAttribute("list", list);  
		//redirect.addAttribute("pageMakerb", pageMakerb);

		return "redirect:/board/study_List.do";
		
		//return "user/board/study_List";
	}

	@RequestMapping("board_Support_Status.do")
	public String boardSupportStatusPage() {
		System.out.println("일반게시판 상세페이지에서 본인이 쓴글을 수정하는 페이지로 이동이동(연규가씀)");

		return "user/board/board_Support_Status";
	}

	// 좋아요 넣는 로직
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
		System.out.println("user" + user);
		
		String s_seq = (String) params.get("s_seq");
		String r_content = (String) params.get("r_content");
		String r_nickname = user.getNickname();
		System.out.println("nickname" + r_nickname);
		
		Comment cm = new Comment();
		cm.setS_seq(Integer.parseInt(s_seq));
		cm.setR_content(r_content);
		cm.setR_name(user_id);
		cm.setR_nickname(r_nickname);
		
		service.commentInsert(cm);

		List<Map<String, Object>> commentList = service.getComment(s_seq);
		System.out.println("댓글리스트: " + commentList);
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

		// service.commentDelete(cm);
		service.updateR_exists(cm);

		List<Map<String, Object>> commentList = service.getComment(s_seq);
		return commentList;

	}

	// 댓글 가져오는 함수
	@RequestMapping(value = "SelectCommentList.do", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getCommentList(@RequestBody Map<String, Object> params, Principal principal)
			throws IOException {
		String s_seq = (String) params.get("s_seq");

		List<Map<String, Object>> commentList = service.getComment(s_seq);
		System.out.println("----commentList 찍어보기----" + commentList);
		System.out.println("0. 코멘트리스트 찍어보기:" + commentList);

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
		System.out.println(cm.toString());
		service.commentUpdate(cm);

		System.out.println("수정 로직 입성!");

		List<Map<String, Object>> commentList = service.getComment(s_seq);
		return commentList;
	}

	// 댓글 갯수 세기
	@RequestMapping(value = "countComment.do", method = RequestMethod.POST)
	@ResponseBody
	public int countComment(@RequestBody Map<String, Object> params, Principal principal) throws IOException {
		String s_seq = (String) params.get("s_seq");

		Comment cm = new Comment();
		cm.setS_seq(Integer.parseInt(s_seq));
		int result = service.countComment(cm);

		System.out.println("**********************************************");

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
		cm.setR_nickname(r_nickname); //닉네임

		int r_refer = service.getP_refer(r_seq); // 부모글의 r_refer
		cm.setR_refer(r_refer);// 그룹번호

		service.reCommentInsert(cm); // 인서트 하러가기

		List<Map<String, Object>> commentList = service.getComment(s_seq);

		return commentList;
	}

	// 마이페이지에서 상세보기 들어가기 (s_seq 만 잇으면 될듯?)
	@RequestMapping("my_Writing_Common_Study_Detail.do")
	public String myWritingStudyDetailPage(String s_seq, Model model, Principal principal) {

		System.out.println("게시판 디테일 페이지 ㅇ입니다.");
		String user_id = principal.getName();
		Likes like = new Likes();
		like.setS_seq(Integer.parseInt(s_seq));
		like.setUser_id(user_id);
		// 좋아요 0/1 중 뭔지 알아오기
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
			System.out.println("우철이는 : " + commentList);
			System.out.println("목록 -> 일반 ************: " + study);

			System.out.println("일반게시판에서 리스트에 있는거 클릭시 디테일 페이지로 이동이동(연규가씀)");

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
		// PrintWriter out = response.getWriter();
		// 업로드할 폴더 경로
		String realFolder = "C:\\summernote\\";
		// String realFolder =
		// request.getServletContext().getRealPath("/summernote/upload/");

		System.out.println("업로드할 폴더경로 찍어봅니다.");
		System.out.println(realFolder);
		UUID uuid = UUID.randomUUID(); // 랜덤한키 생성해주는 객체

		String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String storedFileName = uuid.toString().replaceAll("-", "") + fileExtension;
		System.out.println("우철 : " + storedFileName);
		// String filePath = "C:\\Users\\ksks7\\OneDrive\\바탕
		// 화면\\FinalProject\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp2\\wtpwebapps\\nosangStudy\\board\\profileUpload\\";
		// String filePath = "file:\\C:\\Users\\ksks7\\OneDrive\\바탕
		// 화면\\FinalProject\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp2\\wtpwebapps\\nosangStudy\\board\\profileUpload\\";

		File f = new File(realFolder + storedFileName);
		// File f = new File(filepath);
		if (!f.exists()) {
			f.mkdirs(); // 존재하지 않으면 경로에 폴더를 생성해서 만들어준다.
		}
		Boolean a = f.isAbsolute();
		System.out.println(a);
		Boolean b = f.canExecute();
		System.out.println(b);

		// FileOutputStream fs = new FileOutputStream(filePath + "\\"+
		// file.getOriginalFilename());//
		// fs.write(file.getBytes());//

		file.transferTo(f);
		// out.println(filePath + storedFileName);
		// out.println("profileUpload/"+email+"/"+str_filename);

		int as = storedFileName.lastIndexOf(".");
		String bs = storedFileName.substring(0, as);
		System.out.println("아오 : " + bs);

		response.setContentType("text/html;charset=utf-8");
		System.out.println("sss");
		out.println("/filepath/" + storedFileName);
		out.close();
	}

}