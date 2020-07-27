package kr.or.ns.controller;

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

	/*
	    * @Method 설명 : 동기식으로 기존 온라인 강의 목록 뿌려주기 or 필터 후 static으로 선언한 HashMap에 담긴 카테고리 조건값을 가져와 필터 후 데이터를 기준으로 검색 하는 함수  
	    * @param searchType
	    * @param keyword
	    * @param root
	    * @param language
	    * @return view 주소
	    */
	@RequestMapping("course_List.do")
	public String courseListPage(Criteria cri, Model model, Principal principal,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "root", required = false) String root,
			@RequestParam(value = "language", required = false) String language) {
		PageMaker pageMaker = new PageMaker();
		String user_id = principal.getName();
		pageMaker.setCri(cri);
		List<String> keywordCollec = new ArrayList();
		if(keyword != null) {
			String[] ccc = keyword.split("\\+");
				  for( int i=0; i<ccc.length; i++) {
					  keywordCollec.add(ccc[i]);
					  }
		}
		List<Map<String, Object>> list = null;
		List<HashMap<String, Object>> listSize = null;
		List<Map<String, Object>> listSizeSearch = null;
		HashMap<String, Object> map = null;

		HashMap<String, Object> map2 = new HashMap();
		List<String> arr = new ArrayList();
		arr.add(language); 

		cri.setKeyword(keyword);
		cri.setSearchType(searchType);

		if (language != null) { //main에서 왔을 때
			map2.put("language", arr);
			map2.put("perPageNum", cri.getPerPageNum());
			map2.put("pageStart", cri.getPageStart());
			
			List<HashMap<String, Object>> list2 = aservice.courseBoardFilter(map2, cri);
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
			
			HashMap<String, Object> map4 = new HashMap();
			map4.put("keyword",keywordCollec );
			list = service.getLectureList(cri, map4);
			
			if(keyword == null) {
				pageMaker.setTotalCount(service.getLectureCount());
			}else if(root.equals("search")){
				model.addAttribute("type", "Search");
				model.addAttribute("searchType", cri.getSearchType());
				model.addAttribute("keyword", cri.getKeyword());
				pageMaker.setTotalCount(service.getLectureListSize(cri, map4).size());
			}
			
			model.addAttribute("list", list); // view까지 전달(forward)
			model.addAttribute("pageMaker", pageMaker);
		} else {
			map = AjaxRestController.paramsTemp2;
			map.put("keyword",keywordCollec );
			list = service.getLectureListFilter(cri, map);
			listSizeSearch = service.getLectureListFilterSize(cri, map);
			model.addAttribute("price", map.get("price"));
			model.addAttribute("level", map.get("level"));
			model.addAttribute("language", map.get("language"));
			model.addAttribute("site", map.get("site"));
			
			model.addAttribute("type", "FS");
			model.addAttribute("searchType", cri.getSearchType());
			model.addAttribute("keyword", keyword);
			
			pageMaker.setTotalCount(listSizeSearch.size());
			AjaxRestController.filterSize2 = 0;
			model.addAttribute("list", list); // view까지 전달(forward)
			model.addAttribute("pageMaker", pageMaker);
		}

		List<Integer> seqlist = new ArrayList<Integer>();
		seqlist = service.getCheckedL_seq(user_id);
		model.addAttribute("seqlist", seqlist); // view까지 전달(forward)

		return "user/board/course_List"; // study_List.html
	}
	
		@RequestMapping("course_SearchList.do")
		public String courseSearchPage(Criteria cri, Model model, Principal principal) {
			PageMaker pageMaker = new PageMaker();
			String user_id = principal.getName();
			pageMaker.setCri(cri);

			HashMap<String, Object> map = AjaxRestController.paramsTemp2;
			int filterSize2 = AjaxRestController.filterSize2;

			pageMaker.setTotalCount(filterSize2);

			List<HashMap<String, Object>> list = null;
			list = aservice.courseBoardFilter(map, cri);
			model.addAttribute("list", list); // view까지 전달(forward)
			model.addAttribute("pageMaker", pageMaker);

			model.addAttribute("type", "Search");

			// 서비스, dao
			List<Integer> seqlist = new ArrayList<Integer>();
			seqlist = service.getCheckedL_seq(user_id);
			model.addAttribute("seqlist", seqlist); // view까지 전달(forward)

			return "user/board/course_List"; // study_List.html
		}

		/*
		    * @Method 설명 : 필터 후 static으로 선언한 HashMap에 담긴 카테고리 조건값을 가져와 지정한 카테고리에 해당하는 데이터를 가져오는 함수   
		    * @param cri_b
		    * @param model
		    * @return view 주소
	    */
	@RequestMapping("course_FilterList.do")
	public String courseListFilterPage(Criteria cri, Model model, Principal principal) {
		PageMaker pageMaker = new PageMaker();
		String user_id = principal.getName();
		pageMaker.setCri(cri);

		HashMap<String, Object> map = AjaxRestController.paramsTemp2;
		int filterSize2 = AjaxRestController.filterSize2;

		pageMaker.setTotalCount(filterSize2);

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결
		List<HashMap<String, Object>> list = null;
		list = aservice.courseBoardFilter(map, cri);
		model.addAttribute("list", list); // view까지 전달(forward)
		model.addAttribute("pageMaker", pageMaker);

		model.addAttribute("price", map.get("price"));
		model.addAttribute("level", map.get("level"));
		model.addAttribute("language", map.get("language"));
		model.addAttribute("site", map.get("site"));

		model.addAttribute("type", "filter");

		// 서비스, dao
		List<Integer> seqlist = new ArrayList<Integer>();
		seqlist = service.getCheckedL_seq(user_id);
		model.addAttribute("seqlist", seqlist); // view까지 전달(forward)

		return "user/board/course_List"; 
	}

	// 스터디 개설 온라인강의 게시글 등록전 북마크한 강의 불러오기 
	@RequestMapping("board_Select_Online_Bookmark.do")
	public String boardSelectOnlinePage(Criteria_Select cri_s, Model model, Principal principal) {

		// 북마크 테이블에서 사용자 아이디와 일치하는 데이터 가져오기
		String user_id = principal.getName();

		// 페이징
		PageMaker_Select pageMakers = new PageMaker_Select();
		pageMakers.setCri_s(cri_s);
		
		// 페이징 들고오기
		List<HashMap<String, Object>> bookMarkList = null;
		List<HashMap<String, Object>> bookMarkListSize = null;

		HashMap<String, Object> map = new HashMap();
		map.put("user_id", user_id);
		map.put("cri_s", cri_s);

		bookMarkList = service.getBookmarkList(map);
		
		bookMarkListSize = service.getBookmarkListSize(map);
		pageMakers.setTotalCount(bookMarkListSize.size());
		
		model.addAttribute("bookMarkList", bookMarkList); 
		model.addAttribute("pageMakers", pageMakers);

		return "user/board/board_Select_Online_Bookmark";
	}

	// 북마크선택페이지에서 -> 강의전용글쓰기 페이지
	@RequestMapping(value = "writing_Course_Study.do", method = RequestMethod.GET)
	public String writingCourseStudyPage(String l_seq, Model model) {
		HashMap<String, Object> map = service.getLecture(l_seq);
		model.addAttribute("lecture", map);

		return "user/board/writing_Course_Study";
	}

	//스터디 게시판 온라인 컨텐츠 수정페이지 이동
	@RequestMapping("writing_Course_Study_Edit.do")
	public String writingCourseStudyEditPage(String s_seq, String page, String perPageNum, Model model) {
		Map<String, Object> study = service2.getStudy(s_seq);
		Map<String, Object> onlineInfo = service2.onlineDetailInfo(s_seq);

		model.addAttribute("page", page);
		model.addAttribute("perPageNum", perPageNum);
		model.addAttribute("study", study);
		model.addAttribute("onlineInfo", onlineInfo);

		return "user/board/writing_Course_Study_Edit";
	}

	//스터디 게시판 온라인 컨텐츠 수정 
	@RequestMapping(value = "writing_Course_Study_Edit.do", method = RequestMethod.POST)
	public String writingCourseStudyEdit(String page, String perPageNum, RedirectAttributes redirect, Study study,
			Principal principal, HttpServletRequest request) {

		try {
			service.studyOnlineEdit(study, request, principal);
		} catch (Exception e) {
		}

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
		try {
			service.heartinsert(user_id, l_seq);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}