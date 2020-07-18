package kr.or.ns.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ns.page.PageMaker_Board;
import kr.or.ns.service.AjaxService;
import kr.or.ns.service.BoardService;
import kr.or.ns.service.MessageService;
import kr.or.ns.vo.Criteria_Board;

@RestController // controller + responsebody
@RequestMapping("/ajax/")
public class AjaxRestController {

	@Autowired
	private AjaxService service;

	@Autowired
	private BoardService bservice;

	@Autowired
	private MessageService mservice;

	// 아이디 찾기 -> 아이디,이메일 입력 후 인증받기 -> 존재하는 회원이면 이메일로 보내기 / 인증번호 생성후 전송
	@RequestMapping(value = "emailCheck.do", method = RequestMethod.POST)
	public int emailCheck(@RequestBody HashMap<String, Object> params) {

		// 정보넣어주기
		String username = (String) params.get("username");
		String useremail = (String) params.get("useremail");
		System.out.println("-----------가져온 정보 찍어보기----------------");
		System.out.println("username" + username);
		System.out.println("useremail" + useremail);
		System.out.println("-----------------------------------------------");
		// 결과값 확인
		int flag = service.emailCheck(username, useremail);

		return flag;

	}

	// 인증키 발급 하는 함수
	@RequestMapping(value = "makeAuthKey.do", method = RequestMethod.POST)
	public HashMap<String, String> makeAuthKey(@RequestBody HashMap<String, Object> params) {
		System.out.println("----------------------------------------------------------컨트롤러-시작-------");
		// 해쉬맵 생성
		HashMap<String, String> map = new HashMap<String, String>();
		String username = (String) params.get("username");
		String useremail = (String) params.get("useremail");
		// 이메일 보내기
		System.out.println("여기탔습니다");
		String key = service.emailSend(useremail);
		map.put("key", key);

		// ID 찾아오셈
		String id = service.findId(username, useremail);
		System.out.println("여기는 컨트롤러" + id + "여기는 컨트롤러");
		map.put("id", id);

		return map;
	}

	// 임시비밀번호를 만들기 전에 이메일과 아이디값 확인하는 함수
	@RequestMapping(value = "pwCheck.do", method = RequestMethod.POST)
	public int pwCheck(@RequestBody HashMap<String, Object> params) {
		System.out.println("----------------------------------------------------------컨트롤러-시작-------");
		String userid = (String) params.get("userId");
		String useremail = (String) params.get("userEmail");
		// 서비스단에서 있는 이메일과, 아이디 인지 확인
		int result = 0;
		result = service.searchId(userid, useremail);

		if (result == 0) {
			System.out.println("--id 비어있어 --");

			return result;

		} else {
			System.out.println("result : " + result);
			System.out.println("----------------------------------------------------------컨트롤러-중간-------");
			// service.makeNewPw(userid,useremail);
			System.out.println("----------------------------------------------------------컨트롤러-끝-------");
			System.out.println("이메일 발급후 리턴 전");
			System.out.println("result : " + result);
			return result;
		}

	}

	// 임시비밀번호 발급 하는 함수
	@RequestMapping(value = "makeNewPWD.do", method = RequestMethod.POST)
	public void makeNewPWD(@RequestBody HashMap<String, Object> params) {
		System.out.println("----------------------------------------------------------컨트롤러-시작-------");
		String userid = (String) params.get("userId");
		String useremail = (String) params.get("userEmail");
		service.makeNewPw(userid, useremail);

	}

	// 아이디 중복체크
	@RequestMapping(value = "idcheck.do", method = RequestMethod.POST)
	public int idcheck(String id) throws ClassNotFoundException {
		System.out.println(id + " : user_id 컨트롤러");
		int userid = service.idcheck(id);

		return userid;

	}

	// 일반스터디 지원하기
	@RequestMapping(value = "nomalstudy.do", method = RequestMethod.POST)
	public int applyNomalStudy(@RequestBody HashMap<String, Object> params, Principal principal) {

		// 아이디와 강의 글번호 받기
		String userid = principal.getName();
		String s_seq = (String) params.get("s_seq");

		// 확인용
		System.out.println("잘오나욤!!");
		System.out.println("아이디 : " + userid);
		System.out.println("글번호 : " + s_seq);

		// 인서트 결과 0,1,2 로 표시
		int result = service.applyNomalStudy(s_seq, userid);
		System.out.println("잘됐나 확인" + result);

		return result; // 0 또는 1 또는 2 리턴
	}

	// 신고하기(게시판)
	@RequestMapping(value = "blameinsert.do", method = RequestMethod.POST)
	public int blameInsert(@RequestBody HashMap<String, Object> params, Principal principal) {

		System.out.println("신고하기 컨트롤러");
		String current_userid = principal.getName(); // 현재 로그인한 사용자
		System.out.println(params);
		int result = service.blameInsert(params, current_userid); // 모달 내용과 로그인유저 정보 전달
		System.out.println("인서트 성공" + result);
		return result;

	}

	// 쪽지함 선택된 쪽지 삭제
	@RequestMapping(value = "deleteMessage.do", method = RequestMethod.POST)
	public List<HashMap<String, Object>> deleteMessage(@RequestBody HashMap<String, Object> params, Principal principal,
			Criteria_Board cri_b) {
		System.out.println("쪽지삭제 컨트롤러");
		System.out.println(params);

		int result = service.deleteMessage(params);

		// 쪽지 테이블에서 사용자 아이디와 일치하는 데이터 가져오기
		String user_id = principal.getName();

		// 페이징
		PageMaker_Board pageMakerb = new PageMaker_Board();
		pageMakerb.setCri_b(cri_b);
		pageMakerb.setTotalCount(mservice.getMyMessageCount(user_id));

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결========
		// 받은 쪽지 뿌려주기

		List<HashMap<String, Object>> messageList = null;
		HashMap<String, Object> map = null;

		try {
			map = new HashMap();
			map.put("user_id", user_id);
			map.put("cri_b", cri_b);

			System.out.println("아이디 : " + user_id);
			messageList = mservice.getMessageList(map);

			System.out.println("messageList:" + messageList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("1.다음버튼이 있니?: " + pageMakerb.isNext());
		System.out.println("2.이게 받은쪽지인가 : " + messageList.toString());

		System.out.println("3.받은 쪽지함으로 이동이동(연규가씀)");
		System.out.println("4.쪽지 삭제 성공");
		System.out.println("5.messageList: " + messageList);

		return messageList;
	}

	// 쪽지함에서 유저정보 모달 불러오기
	@RequestMapping(value = "userInfoModal.do", method = RequestMethod.POST)
	public List<HashMap<String, Object>> userInfoModal(@RequestBody HashMap<String, Object> params) {
		System.out.println("유저정보 오나요");
		System.out.println("유저정보:" + params);

		List<HashMap<String, Object>> list = null;

		list = service.userInfoModal(params);
		System.out.println("리스트에 유저정보 담기: " + list);
		return list;
	}

	// 이메일 중복체크
	@RequestMapping(value = "onlyEmailCheck.do", method = RequestMethod.POST)
	public int onlyEmailCheck(String user_email) throws ClassNotFoundException {
		System.out.println(user_email + " : user_id 컨트롤러");
		int result = service.onlyEmailCheck(user_email);

		return result;

	}

	// 마이페이지 모집중 스터디 비동기
	@RequestMapping(value = "recrutingStudy.do", method = RequestMethod.POST)
	List<HashMap<String, Object>> recrutingStudy(@RequestBody HashMap<String, Object> params) {
		System.out.println(params + " : 모집중 컨트롤러");
		List<HashMap<String, Object>> list = null;

		list = service.recrutingStudy(params);
		return list;

	}

	// 마이페이지 참여중 스터디 비동기
	@RequestMapping(value = "inStudy.do", method = RequestMethod.POST)
	List<HashMap<String, Object>> inStudy(@RequestBody HashMap<String, Object> params) {
		System.out.println(params + " : 참여중 컨트롤러");
		List<HashMap<String, Object>> list = null;

		list = service.inStudy(params);
		return list;

	}

	// 차트
	@RequestMapping(value = "mainChart.do", method = RequestMethod.POST)
	List<HashMap<String, Object>> mainChart() {
		System.out.println("차트데이터");
		List<HashMap<String, Object>> list = null;
		list = service.mainChart();
		return list;
	}

	// 지원현황 승인 후 승인완료 데이터 반환
	@RequestMapping(value = "accept.do", method = RequestMethod.POST)
	List<HashMap<String, Object>> accept(@RequestBody HashMap<String, Object> params, Principal principal) {
		List<HashMap<String, Object>> list = service.accept(params);
		HashMap<String, Object> map = new HashMap();
		map.put("user_id", principal.getName());
		list.add(map);
		System.out.println("우철이다!!! : " + list);
		return list;
	}

	// 승인 거절
	@RequestMapping(value = "reject.do", method = RequestMethod.POST)
	int reject(@RequestBody HashMap<String, Object> params) {
		int result = service.reject(params);
		System.out.println("거절 결과 : " + result);
		return result;
	}

	// 참가중인 스터디원 취소
	@RequestMapping(value = "cancel.do", method = RequestMethod.POST)
	List<HashMap<String, Object>> cancel(@RequestBody HashMap<String, Object> params, Principal principal) {
		List<HashMap<String, Object>> list = service.cancel(params);
		HashMap<String, Object> map = new HashMap();
		map.put("user_id", principal.getName());
		list.add(map);
		System.out.println("취소 결과 : " + list);
		return list;

	}

	// 북마크 삭제 후 받은 북마크 번호호 그대로 반환
	@RequestMapping(value = "deleteBookMark.do", method = RequestMethod.POST)
	String deleteBookMark(@RequestBody HashMap<String, Object> params, Principal principal) {
		String bookmark = (String) params.get("l_seq");
		String user_id = principal.getName();
		params.put("user_id", user_id);
		int a = service.deleteBookMark(params);
		System.out.println("북마크 삭제결과 : " + a);
		return bookmark;
	}

	public static HashMap<String, Object> paramsTemp = null;
	public static int filterSize = 0;
	
	// 스터디게시판 필터
	@RequestMapping(value = "studyBoardFilter.do", method = { RequestMethod.POST, RequestMethod.GET })
	public List studyBoardFilter(@RequestBody HashMap<String, Object> params, Criteria_Board cri_b) {
		System.out.println("테스트1");

		System.out.println("이건떠야됨 " + params.get("language"));
		
		paramsTemp = params;

		PageMaker_Board pageMakerb = new PageMaker_Board();
		pageMakerb.setCri_b(cri_b);

		List temp = new ArrayList();
		// 필터 개수
		List<HashMap<String,Object>> listSize = service.studyBoardFilterSize(params);
		List<HashMap<String, Object>> list = service.studyBoardFilter(params, cri_b);
		List<Map<String, Object>> onlineInfo = bservice.getOnlineStudyBoard();

		filterSize = listSize.size();
		
		pageMakerb.setTotalCount(listSize.size());
		System.out.println("제발.." + listSize);
		System.out.println("이거제발맞아라 : " + listSize.size());

		temp.add(list);
		temp.add(onlineInfo);
		temp.add(pageMakerb);

		System.out.println("우철 : " + temp);

		return temp;
	}

	// 스터디게시판 필터
	/*
	 * @RequestMapping(value = "studyBoardFilter22.do", method = RequestMethod.GET)
	 * public String studyBoardFilter( Criteria_Board cri_b, RedirectAttributes
	 * redirect) {
	 * 
	 * System.out.println("ㅎㅇ"); System.out.println("ㄴㄴ" + cri_b.getPage());
	 * System.out.println("ㄴㄴ" + cri_b.getPageStart()); System.out.println("ㄴㄴ" +
	 * cri_b.getPerPageNum());
	 * 
	 * PageMaker_Board pageMakerb = new PageMaker_Board();
	 * pageMakerb.setCri_b(cri_b);
	 * 
	 * redirect.addAttribute("cri_b", cri_b); //필터 개수
	 * 
	 * List<HashMap<String, Object>> listSize =
	 * service.studyBoardFilterSize(params); List<HashMap<String, Object>> list =
	 * service.studyBoardFilter(params, cri_b); List<Map<String, Object>> onlineInfo
	 * = bservice.getOnlineStudyBoard();
	 * 
	 * pageMakerb.setTotalCount(listSize.size()); System.out.println("이거제발맞아라 : " +
	 * listSize.size());
	 * 
	 * temp.add(list); temp.add(onlineInfo); temp.add(pageMakerb);
	 * 
	 * return "redirect:/ajax/studyBoardFilter.do"; }
	 */

	// 마이페이지 내가쓴 댓글 비동기
	@RequestMapping(value = "commentList.do", method = RequestMethod.POST)
	List<HashMap<String, Object>> commentList(@RequestBody HashMap<String, Object> params) {
		System.out.println(params + " : 댓글리스트  컨트롤러");
		List<HashMap<String, Object>> list = null;

		list = service.commentList(params);
		return list;

	}

	// 마이페이지 내가쓴 댓글 비동기
	@RequestMapping(value = "finishRecruit.do", method = RequestMethod.POST)
	public void finishRecruit(@RequestBody HashMap<String, Object> params) {
		String s_seq = (String) params.get("s_seq");
		System.out.println(s_seq);
		service.deleteWaitingUsers(s_seq);
		service.finishRecruit(s_seq);
	}

	// 일반스터디 지원취소하기
		@RequestMapping(value = "applycancel.do", method = RequestMethod.POST)
		public void applycancelNomalStudy(@RequestBody HashMap<String, Object> params, Principal principal) {

			// 아이디와 강의 글번호 받기
			String user_id = principal.getName();
			String s_seq = (String) params.get("s_seq");
			System.out.println("여기까지 잘 왔으면 쏘리질러!!!!!!!!");
			
			service.applycancelNomalStudy(s_seq, user_id);

		}
		
		
}
