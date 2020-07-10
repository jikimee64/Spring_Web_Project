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
	public String courseListPage(Criteria cri, Model model) {
		System.out.println("강좌페이지로 이동이동(연규가씀)");
		System.out.println(cri.getPage());
		PageMaker pageMaker = new PageMaker();
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

		System.out.println("미네미네미네미네미네미넴니ㅔ " + pageMaker.isNext());
		System.out.println(list.toString());
		System.out.println("컨트롤러2");

		return "user/board/course_List"; // study_List.html
	}

	
	
	//스터디 개설 온라인강의 선택---------------------------------------------------------
	@RequestMapping("board_Select_Online_Bookmark.do")
	public String boardSelectOnlinePage(Criteria_Select cri_s, Model model, Principal principal) {
		System.out.println("온라인강의 선택페이지(북마크)로 이동이동(연규가씀)");
		
		//북마크 테이블에서 사용자 아이디와 일치하는 데이터 가져오기 
		String users = principal.getName();
		System.out.println("현재 유저정보:" + users);
		
		//페이징
		PageMaker_Select pageMakers = new PageMaker_Select();
		pageMakers.setCri_s(cri_s);
		pageMakers.setTotalCount(service.getBookmarkCount(users));
		
		// DAO받아오기 + 매퍼를 통한 인터페이스 연결========
		//마이페이지에서 북마크 들고오기
		List<Map<String,Object>> mypageBookmarkList = null;
		mypageBookmarkList = service3.myPagelist(users);
		model.addAttribute("mypageBookmarkList", mypageBookmarkList);
		System.out.println("북마크리스트 컨트롤러 잘 받아왔는가" + mypageBookmarkList);
		
		//페이징 들고오기
		List<HashMap<String, Object>> bookMarkList = null;
		bookMarkList = service.getBookmarkList(cri_s,users);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@RequestMapping(value = "writing_Course_Study.do", method = RequestMethod.GET)
	public String writingCourseStudyPage() {
		System.out.println("온라인saas강의 선택페이지(북마크)에서 다음페이지인 강의전용 글쓰기페이지로 이동이동(연규가씀)");

		return "user/board/writing_Course_Study";
	}

	@RequestMapping(value = "writing_Course_Study.do", method = RequestMethod.POST)
	public String writingCourseStudyPage(Study study, HttpServletRequest request) {

		List<CommonsMultipartFile> files = study.getFiles();
		List<String> filenames = new ArrayList<String>(); // 파일명관리
		System.out.println("?? " + files);

		if (files != null && files.size() > 0) { // 최소 1개의 업로드가 있다면
			for (CommonsMultipartFile multifile : files) {
				String filename = multifile.getOriginalFilename();
				System.out.println("파일업로드 : " + filename);
				String path = request.getServletContext().getRealPath("/studyboard/upload");

				String fpath = path + "\\" + filename;

				if (!filename.equals("")) { // 실 파일 업로드
					try {
						FileOutputStream fs = new FileOutputStream(fpath);
						fs.write(multifile.getBytes());
						fs.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				filenames.add(filename); // 파일명을 별도 관리 (DB insert)
				System.out.println("fs" + filename);
			}

		}

//		  System.out.println("제목 : " + study.getTitle());
//		  System.out.println("내용 : " + study.getContent());
//		  System.out.println("언어" + study.getSelectlan());
//		  System.out.println("모집인원" + study.getSelectPeo());
//		  System.out.println("지역선택" + study.getSelectloc());
//		  System.out.println("난이도" + study.getSelectlev());
//		  System.out.println("모집마감일" + study.getSelectend());

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

	// 스터디 상세보기(하나 다시만들어줘라!!)
	// BoardServiceImpl의 getStudy똑같이 만등러줘라!!
	@RequestMapping("writing_Course_Study_Detail.do")
	public String writingCourseStudyDetailPage(String s_seq, String page, String perPageNum, Model model) {
		System.out.println("강의게시판에서 리스트에 있는거 클릭시 디테일 페이지로 이동이동(연규가씀)");

		Map<String, Object> study = service2.getStudy(s_seq);
		model.addAttribute("study", study);
		model.addAttribute("page", page);
		model.addAttribute("perPageNum", perPageNum);

		System.out.println(page);
		System.out.println(perPageNum);

		return "user/board/writing_Course_Study_Detail"; // writing_Course_Study_Detail.html

	}

//	@RequestMapping("course_List.do")
//	public String courseListPage(Model m odel) {
//		System.out.println("강좌페이지로 이동이동(연규가씀)");
//		List<Map<String,Object>> list = null;
//		list = service.getStudyBoardList(cri);
//		model.addAttribute("list",list); //view까지 전달(forward)
//		model.addAttribute("pageMaker",pageMaker); 
//		
//		System.out.println(list.toString());
//		System.out.println("찍어보자2");
//		return "/user/board/study_List"; //study_List.html
//		
//		
//		return "/user/board/course_List";
//	}
//	
}