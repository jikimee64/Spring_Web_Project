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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ns.page.PageMaker;
import kr.or.ns.page.PageMaker_Board;
import kr.or.ns.service.AjaxService;
import kr.or.ns.service.BoardService;
import kr.or.ns.service.LectureService;
import kr.or.ns.service.MessageService;
import kr.or.ns.service.MyPageService;
import kr.or.ns.vo.Criteria;
import kr.or.ns.vo.Criteria_Board;

@RestController // controller + responsebody
@RequestMapping("/ajax/")
public class AjaxRestController {

	@Autowired
	private AjaxService service;

	@Autowired
	private BoardService bservice;

	@Autowired
	private LectureService lservice;

	@Autowired
	private MessageService mservice;

	// 아이디 찾기 -> 아이디,이메일 입력 후 인증받기 -> 존재하는 회원이면 이메일로 보내기 / 인증번호 생성후 전송
	@RequestMapping(value = "emailCheck.do", method = RequestMethod.POST)
	public int emailCheck(@RequestBody HashMap<String, Object> params) {

		// 정보넣어주기
		String username = (String) params.get("username");
		String useremail = (String) params.get("useremail");
		// 결과값 확인
		int flag = service.emailCheck(username, useremail);
		

		return flag;

	}

	// 인증키 6자리 발급해서 사용자가 아이디 찾을때 사용
	@RequestMapping(value = "makeAuthKey.do", method = RequestMethod.POST)
	public HashMap<String, String> makeAuthKey(@RequestBody HashMap<String, Object> params) {
		// 해쉬맵 생성
		HashMap<String, String> map = new HashMap<String, String>();
		String username = (String) params.get("username");
		String useremail = (String) params.get("useremail");
		// 이메일 보내기
		String key = service.emailSend(useremail);
		map.put("key", key);

		// ID 찾아오셈
		String id = service.findId(username, useremail);
		map.put("id", id);

		return map;
	}

	// 임시비밀번호를 만들기 전에 이메일과 아이디값 확인하는 함수
	@RequestMapping(value = "pwCheck.do", method = RequestMethod.POST)
	public int pwCheck(@RequestBody HashMap<String, Object> params) {
		String userid = (String) params.get("userId");
		String useremail = (String) params.get("userEmail");
		// 서비스단에서 있는 이메일과, 아이디 인지 확인
		int result = 0;
		result = service.searchId(userid, useremail);

		if (result == 0) {

			return result;

		} else {
			// service.makeNewPw(userid,useremail);
			return result;
		}

	}

	// 임시비밀번호 발급 하는 함수
	@RequestMapping(value = "makeNewPWD.do", method = RequestMethod.POST)
	public void makeNewPWD(@RequestBody HashMap<String, Object> params) {
		String userid = (String) params.get("userId");
		String useremail = (String) params.get("userEmail");
		service.makeNewPw(userid, useremail);

	}

	// 아이디 중복체크
	@RequestMapping(value = "idcheck.do", method = RequestMethod.POST)
	public int idcheck(String id) throws ClassNotFoundException {
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

		// 인서트 결과 0,1, 로 표시
		int result = service.applyNomalStudy(s_seq, userid);

		return result; // 0 또는 1 리턴
	}

	// 신고하기(게시판)
	@RequestMapping(value = "blameinsert.do", method = RequestMethod.POST)
	public int blameInsert(@RequestBody HashMap<String, Object> params, Principal principal) {

		String current_userid = principal.getName(); // 현재 로그인한 사용자
		int result = service.blameInsert(params, current_userid); // 모달 내용과 로그인유저 정보 전달
		return result;

	}

	// 쪽지함 선택된 쪽지 삭제
	@RequestMapping(value = "deleteFromMessage.do", method = RequestMethod.POST)
	public List<HashMap<String, Object>> deleteFromMessage(@RequestBody HashMap<String, Object> params, Principal principal,
			Criteria_Board cri_b) {

		int result = service.deleteMessage(params);

		// 쪽지 테이블에서 사용자 아이디와 일치하는 데이터 가져오기
		String user_id = principal.getName();

		// 페이징
		PageMaker_Board pageMakerb = new PageMaker_Board();
		pageMakerb.setCri_b(cri_b);
		pageMakerb.setTotalCount(mservice.getFromMyMessageCount(user_id));

		// DAO받아오기 + 매퍼를 통한 인터페이스 연결========
		// 받은 쪽지 뿌려주기

		List<HashMap<String, Object>> messageList = null;
		HashMap<String, Object> map = null;

		try {
			map = new HashMap();
			map.put("user_id", user_id);
			map.put("cri_b", cri_b);

			messageList = mservice.getMessageList(map);


		} catch (Exception e) {
			e.printStackTrace();
		}



		return messageList;
	}
	
	
	// 쪽지함 선택된 쪽지 삭제(리턴필요없음)
		@RequestMapping(value = "deleteToMessage.do", method = RequestMethod.POST)
		public PageMaker_Board deleteToMessage(@RequestBody HashMap<String, Object> params, Principal principal,
				Criteria_Board cri_b) {

			int result = service.deleteMessage(params);

			// 쪽지 테이블에서 사용자 아이디와 일치하는 데이터 가져오기
			String user_id = principal.getName();

			// 페이징
			PageMaker_Board pageMakerb = new PageMaker_Board();
			pageMakerb.setCri_b(cri_b);
			pageMakerb.setTotalCount(mservice.getToMyMessageCount(user_id));

			// DAO받아오기 + 매퍼를 통한 인터페이스 연결========
			// 받은 쪽지 뿌려주기

			List<HashMap<String, Object>> messageList = null;
			HashMap<String, Object> map = null;

			try {
				map = new HashMap();
				map.put("user_id", user_id);
				map.put("cri_b", cri_b);

				messageList = mservice.getMessageList(map);


			} catch (Exception e) {
				e.printStackTrace();
			}



			return pageMakerb;
		}

	// 쪽지함에서 유저정보 모달 불러오기
	@RequestMapping(value = "userInfoModal.do", method = RequestMethod.POST)
	public List userInfoModal(@RequestBody HashMap<String, Object> params) {

		List list =  new ArrayList();
		
		List<HashMap<String, Object>> userInfoModal = service.userInfoModal(params);
		List<HashMap<String, Object>> userBoardModal = service.userBoardModal(params);
		
		list.add(userInfoModal);
		list.add(userBoardModal);
		return list;
	}

	// 이메일 중복체크
	@RequestMapping(value = "onlyEmailCheck.do", method = RequestMethod.POST)
	public int onlyEmailCheck(String user_email) throws ClassNotFoundException {
		int result = service.onlyEmailCheck(user_email);

		return result;

	}

	// 마이페이지 모집중 스터디 비동기// 스터디 정보랑 , 승인완료된 카운트 값까지 넘겨받음
	@RequestMapping(value = "recrutingStudy.do", method = RequestMethod.POST)
	List<HashMap<String, Object>> recrutingStudy(@RequestBody HashMap<String, Object> params) {
		List<HashMap<String, Object>> list = null;

		list = service.recrutingStudy(params);
		return list;

	}

	// 마이페이지 참여중 스터디 비동기// 스터디 정보랑 , 승인완료된 카운트 값까지 넘겨받음
	@RequestMapping(value = "inStudy.do", method = RequestMethod.POST)
	List<HashMap<String, Object>> inStudy(@RequestBody HashMap<String, Object> params) {
		List<HashMap<String, Object>> list = null;

		list = service.inStudy(params);

		return list;

	}

	// 차트JS
	@RequestMapping(value = "mainChart.do", method = RequestMethod.POST)
	List<HashMap<String, Object>> mainChart() {
		List<HashMap<String, Object>> list = null;
		list = service.mainChart();
		return list;
	}

	// 워드클라우드 차트
	@RequestMapping(value = "wordCloud.do", method = RequestMethod.POST)
	List<HashMap<String, Object>> wordCloud() {
		List<HashMap<String, Object>> list = null;
		list = service.wordCloud();
		return list;
	}

	// 지원현황 승인 후 승인완료 데이터 반환
	@RequestMapping(value = "accept.do", method = RequestMethod.POST)
	List<HashMap<String, Object>> accept(@RequestBody HashMap<String, Object> params, Principal principal) {
		List<HashMap<String, Object>> list = service.accept(params);
		HashMap<String, Object> map = new HashMap();
		map.put("user_id", principal.getName());
		list.add(map);
		return list;
	}

	// 승인 거절
	@RequestMapping(value = "reject.do", method = RequestMethod.POST)
	int reject(@RequestBody HashMap<String, Object> params) {
		int result = service.reject(params);
		return result;
	}

	// 참가중인 스터디원 취소
	@RequestMapping(value = "cancel.do", method = RequestMethod.POST)
	List<HashMap<String, Object>> cancel(@RequestBody HashMap<String, Object> params, Principal principal) {
		List<HashMap<String, Object>> list = service.cancel(params);
		HashMap<String, Object> map = new HashMap();
		map.put("user_id", principal.getName());
		list.add(map);
		return list;

	}

	// 북마크 삭제 후 받은 북마크 번호호 그대로 반환
	@RequestMapping(value = "deleteBookMark.do", method = RequestMethod.POST)
	String deleteBookMark(@RequestBody HashMap<String, Object> params, Principal principal) {
		String bookmark = (String) params.get("l_seq");
		String user_id = principal.getName();
		params.put("user_id", user_id);
		int a = service.deleteBookMark(params);
		return bookmark;
	}

	public static HashMap<String, Object> paramsTemp = null;
	public static int filterSize = 0;

	// 스터디게시판 필터
	@RequestMapping(value = "studyBoardFilter.do", method = { RequestMethod.POST, RequestMethod.GET })
	public List studyBoardFilter(@RequestBody HashMap<String, Object> params, Criteria_Board cri_b) {

		paramsTemp = params;

		PageMaker_Board pageMakerb = new PageMaker_Board();
		pageMakerb.setCri_b(cri_b);

		List temp = new ArrayList();
		// 필터 개수
		List<HashMap<String, Object>> listSize = service.studyBoardFilterSize(params);
		List<HashMap<String, Object>> list = service.studyBoardFilter(params, cri_b);
		List<Map<String, Object>> onlineInfo = bservice.getOnlineStudyBoard();

		filterSize = listSize.size();

		pageMakerb.setTotalCount(listSize.size());

		temp.add(list);
		temp.add(onlineInfo);
		temp.add(pageMakerb);


		return temp;
	}

	// 마이페이지 내가쓴 댓글 비동기
	@RequestMapping(value = "commentList.do", method = RequestMethod.POST)
	List<HashMap<String, Object>> commentList(@RequestBody HashMap<String, Object> params) {
		List<HashMap<String, Object>> list = null;

		list = service.commentList(params);
		return list;

	}

	// 방장이 모집마감 버튼 눌러서 스터디 모집 마감하기 (인원수 초과시 불가능 미만일경우는 가능)
	@RequestMapping(value = "finishRecruit.do", method = RequestMethod.POST)
	public int finishRecruit(@RequestBody HashMap<String, Object> params) {
		String s_seq = (String) params.get("s_seq");
		//해당글에 승인 완료된 인원수 
		int result = service.checkA_staCount(s_seq);
		
		if(result != 0) {
			
			service.deleteWaitingUsers(s_seq);
			service.finishRecruit(s_seq);
			return result;
		}else {
			
			return result;
		}
		
	}

	// 스터디 게시판에서 지원취소하기 버튼을 누르면 작동
	@RequestMapping(value = "applycancel.do", method = RequestMethod.POST)
	public void applycancelNomalStudy(@RequestBody HashMap<String, Object> params, Principal principal) {

		// 아이디와 강의 글번호 받기
		String user_id = principal.getName();
		String s_seq = (String) params.get("s_seq");

		service.applycancelNomalStudy(s_seq, user_id);

	}

	public static HashMap<String, Object> paramsTemp2 = null;
	public static int filterSize2 = 0;

	// 강의 게시판 필터
	@RequestMapping(value = "courseBoardFilter.do", method = { RequestMethod.POST, RequestMethod.GET })
	public List courseBoardFilter(@RequestBody HashMap<String, Object> params, Criteria cri_b, Principal principal) {

		paramsTemp2 = params;

		PageMaker pageMakerb = new PageMaker();
		pageMakerb.setCri(cri_b);

		List temp = new ArrayList();

		// 필터 개수
		List<HashMap<String, Object>> listSize = service.courseBoardFilterSize(params);
		List<HashMap<String, Object>> list = service.courseBoardFilter(params, cri_b);

		filterSize2 = listSize.size();

		pageMakerb.setTotalCount(listSize.size());

		String user_id = principal.getName();
		List<Integer> seqlist = new ArrayList<Integer>();
		seqlist = lservice.getCheckedL_seq(user_id);
		temp.add(list);
		temp.add(pageMakerb);
		temp.add(seqlist);


		return temp;
	}
	
	
	// 유ㅗ저사진 가져오기
	@RequestMapping(value = "userInfoChat.do", method = RequestMethod.POST)
	public List<HashMap<String, Object>> userInfoChat(@RequestBody HashMap<String, Object> params, String user_id) {

		List<HashMap<String, Object>> list = null;
		
		list = service.userInfoChat(params);
		return list;
	}

	// 워드클라우드 차트
	
	
	
	
	//검색자동완성    (쪽지쓸때 아이디란에 글자를 쓰면 DB안의 아이디 목록을 가져와서 자동완성 시켜준다 )
	@RequestMapping(value = "getAutoKeyword.do", method = RequestMethod.POST)
	public List<HashMap<String, Object>> getAutoKeyword(@RequestBody HashMap<String, Object> params) {
		String keyword = (String) params.get("keyword");
		List<HashMap<String, Object>> list = null;
		list = service.getAutoKeyword(keyword);
		
		return list;
	}

}
