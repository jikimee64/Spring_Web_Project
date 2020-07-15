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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.ns.page.PageMaker;
import kr.or.ns.page.PageMaker_Select;
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
	private MyPageService service3;

	// 스터디목록 + 페이징
	@RequestMapping("course_List.do")
	public String courseListPage(Criteria cri, Model model,Principal principal) {
		System.out.println("강좌페이지로 이동이동(연규가씀)");
		System.out.println(cri.getPage());
		PageMaker pageMaker = new PageMaker();
		String user_id = principal.getName();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.getLectureCount());

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결
		System.out.println(cri.getPage());
		System.out.println(cri.getPageStart());
		System.out.println(cri.getPerPageNum());
		List<Map<String, Object>> list = null;
		list = service.getLectureList(cri);
		model.addAttribute("list", list); // view까지 전달(forward)
		model.addAttribute("pageMaker", pageMaker);
		System.out.println("우철이는 : " + list);

		System.out.println(list.toString());

		//서비스, dao
		List<Integer> seqlist = new ArrayList<Integer>();
		seqlist = service.getCheckedL_seq(user_id);
		System.out.println("--------------seqlist 찍어보기 -----------");
		System.out.println(seqlist );
		System.out.println(seqlist.toString());
		System.out.println("--------------seqlist 찍어보기 -----------");
		model.addAttribute("seqlist", seqlist); // view까지 전달(forward)
		
		return "user/board/course_List"; // study_List.html
	}

	
	
	//스터디 개설 온라인강의 선택---------------------------------------------------------
		@RequestMapping("board_Select_Online_Bookmark.do")
		public String boardSelectOnlinePage(Criteria_Select cri_s, Model model, Principal principal) {
			System.out.println("온라인강의 선택페이지(북마크)로 이동이동(연규가씀)");
			
			//북마크 테이블에서 사용자 아이디와 일치하는 데이터 가져오기 
			String user_id = principal.getName();
			
			//페이징
			PageMaker_Select pageMakers = new PageMaker_Select();
			pageMakers.setCri_s(cri_s);
			pageMakers.setTotalCount(service.getBookmarkCount(user_id));
			
			// DAO받아오기 + 매퍼를 통한 인터페이스 연결========
			//마이페이지에서 북마크 들고오기
//			List<Map<String,Object>> mypageBookmarkList = null;
//			mypageBookmarkList = service3.myPagelist(users);
//			model.addAttribute("mypageBookmarkList", mypageBookmarkList);
//			System.out.println("북마크리스트 컨트롤러 잘 받아왔는가" + mypageBookmarkList);
			
			//페이징 들고오기
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
	//---------------------------------------------------------
	
	
	
	//스터디 개설 온라인강의 선택 페이징 시도---------------------------------------------------------
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
	//---------------------------------------------------------

	//북마크선택페이지에서 -> 강의전용글쓰기 페이지
	@RequestMapping(value = "writing_Course_Study.do", method = RequestMethod.GET)
	public String writingCourseStudyPage(String l_seq, Model model) {
		System.out.println("l_seq : " + l_seq);
		HashMap<String, Object> map = service.getLecture(l_seq);
		model.addAttribute("lecture", map);
		System.out.println("북마크->강의전용페이지" + map);
		
		return "user/board/writing_Course_Study";
	}



	@RequestMapping("writing_Course_Study_Edit.do")
	public String writingCourseStudyEditPage() {
		System.out.println("강의게시판 상세페이지에서 본인이 쓴글을 수정하는 페이지로 이동이동(연규가씀)");

		return "user/board/writing_Course_Study_Edit";
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