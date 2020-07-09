package kr.or.ns.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
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

import kr.or.ns.page.PageMaker;
import kr.or.ns.service.LectureServiceImpl;
import kr.or.ns.vo.Criteria;
import kr.or.ns.vo.Study;

@Controller
@RequestMapping("/lecture/")
public class LectureController {

	@Autowired
	private LectureServiceImpl service;

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

	@RequestMapping("board_Select_Online_Bookmark.do")
	public String boardSelectOnlinePage() {
		System.out.println("온라인강의 선택페이지(북마크)로 이동이동(연규가씀)");

		return "user/board/board_Select_Online_Bookmark";
	}

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