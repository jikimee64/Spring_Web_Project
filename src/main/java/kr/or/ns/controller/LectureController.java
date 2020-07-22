package kr.or.ns.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ns.page.PageMaker;
import kr.or.ns.page.PageMaker_Select;
import kr.or.ns.service.AjaxService;
import kr.or.ns.service.BoardService;
import kr.or.ns.service.LectureService;
import kr.or.ns.service.MyPageService;
import kr.or.ns.vo.Criteria;
import kr.or.ns.vo.Criteria_Select;
import kr.or.ns.vo.Study;

@Controller
@RequestMapping("/lecture/")
public class LectureController {

	@Autowired
	private LectureService service;

	@Autowired
	private BoardService service2;

	@Autowired
	private AjaxService aservice;

	@Autowired
	private MyPageService service3;

	// 스터디목록 + 페이징
	@RequestMapping("course_List.do")
	public String courseListPage(Criteria cri, Model model, Principal principal,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "root", required = false) String root,
			@RequestParam(value = "language", required = false) String language) {
		PageMaker pageMaker = new PageMaker();
		String user_id = principal.getName();
		pageMaker.setCri(cri);
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
		// DAO받아오기 + 매퍼를 통한 인터페이스 연결
		List<Map<String, Object>> list = null;
		List<HashMap<String, Object>> listSize = null;
		List<Map<String, Object>> listSizeSearch = null;
		HashMap<String, Object> map = null;

		// main Filter
		HashMap<String, Object> map2 = new HashMap();
		List<String> arr = new ArrayList();
		arr.add(language); 
		System.out.println("language : " + language);
		// main Filter

		cri.setKeyword(keyword);
		cri.setSearchType(searchType);

		if (language != null) { //main에서 왔을 때
			
			System.out.println("일로와라제발제발제발!!!!!!!!!!!!@@@");
			System.out.println("여기로 정해따 너 지금 여기타는 거맞지????    아니!!!!!!!");
			map2.put("language", arr);
			map2.put("perPageNum", cri.getPerPageNum());
			map2.put("pageStart", cri.getPageStart());
			List<HashMap<String, Object>> list2 = aservice.courseBoardFilter(map2, cri);
			System.out.println("아오짜짱ㄴㅇㅁ니ㅏㅇㅁ너ㅏㅐㅑㅣㅏ언미 : " + list);
			listSize = aservice.courseBoardFilterSize(map2);
			AjaxRestController.paramsTemp2 = map2;
			AjaxRestController.filterSize2 = listSize.size();
			model.addAttribute("language", map2.get("language"));
			model.addAttribute("type", "filter");
			pageMaker.setTotalCount(listSize.size());
			language = null;
			model.addAttribute("list", list2); // view까지 전달(forward)
			model.addAttribute("pageMaker", pageMaker);
		} else if (root != null) { //헤더에서 왔을때
			
			//language = null;
			System.out.println("동기");
			list = service.getLectureList(cri);
			System.out.println("호호호 : " + service.getLectureListSize(cri).size());
			
			if(keyword == null) {
				System.out.println("처음엔 제발 여기");
				pageMaker.setTotalCount(service.getLectureCount());
			}else if(root.equals("search")){
				System.out.println("필터X 검색했을때만 여기");
				//map = AjaxRestController.paramsTemp2;
				//model.addAttribute("price", map.get("price"));
				//model.addAttribute("level", map.get("level"));
				//model.addAttribute("language", map.get("language"));
				//model.addAttribute("site", map.get("site"));
				model.addAttribute("type", "Search");
				model.addAttribute("searchType", cri.getSearchType());
				model.addAttribute("keyword", cri.getKeyword());
				pageMaker.setTotalCount(service.getLectureListSize(cri).size());
			}
			
			//pageMaker.setTotalCount(service.getLectureListSize(cri).size());
			model.addAttribute("list", list); // view까지 전달(forward)
			model.addAttribute("pageMaker", pageMaker);
		} else {
			
			System.out.println("filterSize2: " + AjaxRestController.filterSize2);
			System.out.println("paramsTemp2 : " + AjaxRestController.paramsTemp2);
			System.out.println("필터된 후엔 여길..");
			map = AjaxRestController.paramsTemp2;
			map.put("keyword",keywordCollec );
			list = service.getLectureListFilter(cri, map);
			System.out.println("list : " + list.size());
			listSizeSearch = service.getLectureListFilterSize(cri, map);
			System.out.println("listSizeSearch : " + listSizeSearch.size());
			model.addAttribute("price", map.get("price"));
			model.addAttribute("level", map.get("level"));
			model.addAttribute("language", map.get("language"));
			model.addAttribute("site", map.get("site"));
			
			//
			model.addAttribute("type", "FS");
			model.addAttribute("searchType", cri.getSearchType());
			model.addAttribute("keyword", cri.getKeyword());
			//
			
			System.out.println("잘왔을텐데..? " + list);
			System.out.println("필터링+검색 사이즈 : " + list.size());
			pageMaker.setTotalCount(listSizeSearch.size());
			AjaxRestController.filterSize2 = 0;
			model.addAttribute("list", list); // view까지 전달(forward)
			model.addAttribute("pageMaker", pageMaker);
		}

//		model.addAttribute("list", list); // view까지 전달(forward)
//		model.addAttribute("pageMaker", pageMaker);

		//System.out.println(list.toString());
		

		// 서비스, dao
		List<Integer> seqlist = new ArrayList<Integer>();
		seqlist = service.getCheckedL_seq(user_id);
		model.addAttribute("seqlist", seqlist); // view까지 전달(forward)

		return "user/board/course_List"; // study_List.html
	}
	
	//
	
	
	
	// 온리 검색
		@RequestMapping("course_SearchList.do")
		public String courseSearchPage(Criteria cri, Model model, Principal principal) {
			System.out.println("강좌페이지로 이동이동(연규가씀)");
			System.out.println(cri.getPage());
			PageMaker pageMaker = new PageMaker();
			String user_id = principal.getName();
			pageMaker.setCri(cri);

			HashMap<String, Object> map = AjaxRestController.paramsTemp2;
			int filterSize2 = AjaxRestController.filterSize2;

			pageMaker.setTotalCount(filterSize2);

			// DAO받아오기 + 매퍼를 통한 인터페이스 연결
			System.out.println(cri.getPage());
			System.out.println(cri.getPageStart());
			System.out.println(cri.getPerPageNum());
			List<HashMap<String, Object>> list = null;
			list = aservice.courseBoardFilter(map, cri);
			model.addAttribute("list", list); // view까지 전달(forward)
			model.addAttribute("pageMaker", pageMaker);

//			model.addAttribute("price", map.get("price"));
//			model.addAttribute("level", map.get("level"));
//			model.addAttribute("language", map.get("language"));
//			model.addAttribute("site", map.get("site"));

			System.out.println("우철이는 : " + list);

			model.addAttribute("type", "Search");

			System.out.println(list.toString());

			// 서비스, dao
			List<Integer> seqlist = new ArrayList<Integer>();
			seqlist = service.getCheckedL_seq(user_id);
			/*
			 * System.out.println("--------------seqlist 찍어보기 -----------");
			 * System.out.println(seqlist ); System.out.println(seqlist.toString());
			 * System.out.println("--------------seqlist 찍어보기 -----------");
			 */
			model.addAttribute("seqlist", seqlist); // view까지 전달(forward)

			return "user/board/course_List"; // study_List.html
		}
	
	
	
	
	
	
	//

	// 스터디목록 + 페이징
	@RequestMapping("course_FilterList.do")
	public String courseListFilterPage(Criteria cri, Model model, Principal principal) {
		System.out.println("강좌페이지로 이동이동(연규가씀)");
		System.out.println(cri.getPage());
		PageMaker pageMaker = new PageMaker();
		String user_id = principal.getName();
		pageMaker.setCri(cri);

		HashMap<String, Object> map = AjaxRestController.paramsTemp2;
		int filterSize2 = AjaxRestController.filterSize2;

		pageMaker.setTotalCount(filterSize2);

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결
		System.out.println(cri.getPage());
		System.out.println(cri.getPageStart());
		System.out.println(cri.getPerPageNum());
		List<HashMap<String, Object>> list = null;
		list = aservice.courseBoardFilter(map, cri);
		model.addAttribute("list", list); // view까지 전달(forward)
		model.addAttribute("pageMaker", pageMaker);

		model.addAttribute("price", map.get("price"));
		model.addAttribute("level", map.get("level"));
		model.addAttribute("language", map.get("language"));
		model.addAttribute("site", map.get("site"));

		System.out.println("우철이는 : " + list);

		model.addAttribute("type", "filter");

		System.out.println(list.toString());

		// 서비스, dao
		List<Integer> seqlist = new ArrayList<Integer>();
		seqlist = service.getCheckedL_seq(user_id);
		/*
		 * System.out.println("--------------seqlist 찍어보기 -----------");
		 * System.out.println(seqlist ); System.out.println(seqlist.toString());
		 * System.out.println("--------------seqlist 찍어보기 -----------");
		 */
		model.addAttribute("seqlist", seqlist); // view까지 전달(forward)

		return "user/board/course_List"; // study_List.html
	}

	// 스터디 개설 온라인강의 선택---------------------------------------------------------
	@RequestMapping("board_Select_Online_Bookmark.do")
	public String boardSelectOnlinePage(Criteria_Select cri_s, Model model, Principal principal) {
		System.out.println("온라인강의 선택페이지(북마크)로 이동이동(연규가씀)");

		// 북마크 테이블에서 사용자 아이디와 일치하는 데이터 가져오기
		String user_id = principal.getName();

		// 페이징
		PageMaker_Select pageMakers = new PageMaker_Select();
		pageMakers.setCri_s(cri_s);
		pageMakers.setTotalCount(service.getBookmarkCount(user_id));

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결========
		// 마이페이지에서 북마크 들고오기
//			List<Map<String,Object>> mypageBookmarkList = null;
//			mypageBookmarkList = service3.myPagelist(users);
//			model.addAttribute("mypageBookmarkList", mypageBookmarkList);
//			System.out.println("북마크리스트 컨트롤러 잘 받아왔는가" + mypageBookmarkList);

		// 페이징 들고오기
		List<HashMap<String, Object>> bookMarkList = null;

		HashMap<String, Object> map = new HashMap();
		map.put("user_id", user_id);
		map.put("cri_s", cri_s);

		bookMarkList = service.getBookmarkList(map);
		System.out.println("우철이 천재 : " + bookMarkList);
//			bookMarkList = service.getBookmarkList(cri_s,users);
		model.addAttribute("bookMarkList", bookMarkList); // view까지 전달(forward)
		model.addAttribute("pageMakers", pageMakers);

		System.out.println("다음버튼이 있니?: " + pageMakers.isNext());
		System.out.println(bookMarkList.toString());
		System.out.println("북마크 컨트롤러 끝~");

		return "user/board/board_Select_Online_Bookmark";
	}
	// ---------------------------------------------------------

	// 스터디 개설 온라인강의 선택 페이징
	// 시도---------------------------------------------------------
//	@REQUESTMAPPING("BOARD_SELECT_ONLINE_BOOKMARK.DO")
//	PUBLIC STRING BOARDSELECTONLINEPAGE(CRITERIA_SELECT CRI_S, MODEL MODEL, PRINCIPAL PRINCIPAL) {
//		SYSTEM.OUT.PRINTLN("온라인강의 선택페이지(북마크)로 이동이동(연규가씀)");
//		
//		//북마크 테이블에서 사용자 아이디와 일치하는 데이터 가져오기 
//		STRING USERS = PRINCIPAL.GETNAME();
//		SYSTEM.OUT.PRINTLN("현재 유저정보:" + USERS);
//		
//		//페이징
//		PAGEMAKER_SELECT PAGEMAKERS = NEW PAGEMAKER_SELECT();
//		PAGEMAKERS.SETCRI_S(CRI_S);
//		PAGEMAKERS.SETTOTALCOUNT(SERVICE.GETBOOKMARKCOUNT(USERS));
//		
//		
//		
//		
//		// DAO받아오기 + 매퍼를 통한 인터페이스 연결========
//		
//		
//		
//		
//		
//		//마이페이지에서 북마크 들고오기
////		LIST<MAP<STRING,OBJECT>> MYPAGEBOOKMARKLIST = NULL;
////		MYPAGEBOOKMARKLIST = SERVICE3.MYPAGELIST(USERS);
////		MODEL.ADDATTRIBUTE("MYPAGEBOOKMARKLIST", MYPAGEBOOKMARKLIST);
////		SYSTEM.OUT.PRINTLN("북마크리스트 컨트롤러 잘 받아왔는가" + MYPAGEBOOKMARKLIST);
//		
//		//페이징 들고오기
//		LIST<HASHMAP<?, ?>> BOOKMARKLIST = NULL;
//		
//		BOOKMARKLIST = SERVICE.GETBOOKMARKLIST(CRI_S, USERS);
//		MODEL.ADDATTRIBUTE("BOOKMARKLIST", BOOKMARKLIST); // VIEW까지 전달(FORWARD)
//		MODEL.ADDATTRIBUTE("PAGEMAKERS", PAGEMAKERS);
//
//		SYSTEM.OUT.PRINTLN("다음버튼이 있니?: " + PAGEMAKERS.ISNEXT());
//		SYSTEM.OUT.PRINTLN(BOOKMARKLIST.TOSTRING());
//		SYSTEM.OUT.PRINTLN("북마크 컨트롤러 끝~");
//
//		
//		
//		
//		RETURN "USER/BOARD/BOARD_SELECT_ONLINE_BOOKMARK";
//	}
	// ---------------------------------------------------------

	// 북마크선택페이지에서 -> 강의전용글쓰기 페이지
	@RequestMapping(value = "writing_Course_Study.do", method = RequestMethod.GET)
	public String writingCourseStudyPage(String l_seq, Model model) {
		System.out.println("l_seq : " + l_seq);
		HashMap<String, Object> map = service.getLecture(l_seq);
		model.addAttribute("lecture", map);
		System.out.println("북마크->강의전용페이지" + map);

		return "user/board/writing_Course_Study";
	}

	@RequestMapping("writing_Course_Study_Edit.do")
	public String writingCourseStudyEditPage(String s_seq, String page, String perPageNum, Model model) {
		System.out.println("강의게시판 상세페이지에서 본인이 쓴글을 수정하는 페이지로 이동이동(연규가씀)");
		Map<String, Object> study = service2.getStudy(s_seq);
		Map<String, Object> onlineInfo = service2.onlineDetailInfo(s_seq);

		model.addAttribute("page", page);
		model.addAttribute("perPageNum", perPageNum);
		model.addAttribute("study", study);
		model.addAttribute("onlineInfo", onlineInfo);
		System.out.println("온라인편집.. : " + study);
		System.out.println("온라인강의정보 : " + onlineInfo);

		return "user/board/writing_Course_Study_Edit";
	}

	@RequestMapping(value = "writing_Course_Study_Edit.do", method = RequestMethod.POST)
	public String writingCourseStudyEdit(String page, String perPageNum, RedirectAttributes redirect, Study study,
			Principal principal, HttpServletRequest request) {

		System.out.println("온라인게시글수정@@");
		System.out.println("넘어온 데이터(온라인0 " + study.toString());

		try {
			// 서비스가서 DB에 등록
			System.out.println("서비스는 잘가냐 ?");
			service.studyOnlineEdit(study, request, principal);
		} catch (Exception e) {
			System.out.println("컨트롤러 에러");
			System.out.println(e.getMessage());
		}
		System.out.println("리턴 전ㄴㄴㄴㄴ...");

		redirect.addAttribute("page", page);
		redirect.addAttribute("perPageNum", perPageNum);
		redirect.addAttribute("s_seq", study.getS_seq());

		return "redirect:../board/writing_Common_Study_Detail.do";

	}

	// 사용자가 선택한 강의글 북마크 테이블에 추가하는 함수

	@RequestMapping(value = "heart.do", method = RequestMethod.POST)
	@ResponseBody
	public void heartinsert(@RequestBody Map<String, Object> params, Principal principal) throws IOException {
		String user_id = principal.getName();
		String l_seq = (String) params.get("l_seq");
		System.out.println(user_id);
		System.out.println(l_seq);
		try {
			service.heartinsert(user_id, l_seq);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}