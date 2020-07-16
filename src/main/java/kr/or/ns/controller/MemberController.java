package kr.or.ns.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.protobuf.TextFormat.ParseException;

import kr.or.ns.service.MemberService;
import kr.or.ns.vo.Users;

@Controller
@RequestMapping("/member/")
public class MemberController {

	/* NaverLoginBO */
	private NaverLoginBO naverLoginBO;
	private String apiResult = null;

	@Autowired
	private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO;
	}

	@Autowired
	MemberService service;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping(value = "login.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginPage(@RequestParam(value = "errormsg", required = false) Object errormsg,
			HttpServletRequest request, Users user, HttpSession session) {

		if (errormsg != null) {
			request.setAttribute("errormsgname", (String) errormsg);
		

		// 스프링 시큐리티 수동 로그인을 위한 작업//
		// 로그인 세션에 들어갈 권한을 설정
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority("ROLE_USER"));

		SecurityContext sc = SecurityContextHolder.getContext();
		// 아이디, 패스워드, 권한을 설정. 아이디는 Object단위로 넣어도 무방하며
		// 패스워드는 null로 하여도 값이 생성.
		sc.setAuthentication(new UsernamePasswordAuthenticationToken(user, null, list));
		session = request.getSession(true);

		// 위에서 설정한 값을 Spring security에서 사용할 수 있도록 세션에 설정
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
		// 스프링 시큐리티 수동 로그인을 위한 작업 끝//

		// 로그인 유저 정보 가져와서 세션객체에 저장
		//Users users = ls.normalLogin(user);
		System.out.println("유저네임: " + user.getUser_id());

		session = request.getSession();
		session.setAttribute("user", users);
		// 로그인 유저 정보 가져와서 세션객체에 저장 끝//
		}
		

		return "user/member/login";
	}

	// 네이버 로그인 성공시 callback호출 메소드
	@RequestMapping(value = "join.do", method = RequestMethod.GET)
	public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session)
			throws IOException, ParseException, org.json.simple.parser.ParseException {
		System.out.println("여기는 callback");
		OAuth2AccessToken oauthToken;
		System.out.println("우철입니다 : " + session);
		System.out.println("code " + code);
		System.out.println("state " + state);

		oauthToken = naverLoginBO.getAccessToken(session, code, state);
		// 1. 로그인 사용자 정보를 읽어온다.
		apiResult = naverLoginBO.getUserProfile(oauthToken); // String형식의 json데이터
		/**
		 * apiResult json 구조 {"resultcode":"00", "message":"success",
		 * "response":{"id":"33666449","nickname":"shinn****","age":"20-29","gender":"M","email":"sh@naver.com","name":"\uc2e0\ubc94\ud638"}}
		 **/

		// 2. String형식인 apiResult를 json형태로 바꿈
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(apiResult);
		JSONObject jsonObj = (JSONObject) obj;

		// 3. 데이터 파싱
		// Top레벨 단계 _response 파싱
		JSONObject response_obj = (JSONObject) jsonObj.get("response");
		// response의 nickname값 파싱
		String nickname = (String) response_obj.get("nickname");
		String name = (String) response_obj.get("name");
		String email = (String) response_obj.get("email");
		System.out.println("res::" + response_obj);
		System.out.println(nickname);
		// 4.파싱 닉네임 세션으로 저장
		model.addAttribute("unickname", nickname); // 세션 생성
		model.addAttribute("uname", name);
		model.addAttribute("uemail", email);
		model.addAttribute("result", apiResult);

		// System.out.println("이건뭐야" + apiResult);

		return "user/member/join";
	}

	// 소셜회원가입처리
	@RequestMapping(value = "join.do", method = RequestMethod.POST)
	public String socialJoin(@RequestParam(value = "file", required = false) MultipartFile ipload, Users users,
			HttpServletRequest request) throws IOException {

		// users.setUser_pwd(this.bCryptPasswordEncoder.encode(users.getUser_pwd()));

		try {
			System.out.println("여긴오니...?");
			service.socialjoininsert(users, request);
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return "redirect:/index.do";
		// /index.htm
		// 주의사항
		// 요청 주소 ...아래처럼 ..
		// http://localhost:8090/SpringMVC_Basic06_WebSite_Annotation_JdbcTemplate/index.htm
		// return "redirect:noticeDetail.htm?seq="+n.getSeq();
	}

	@RequestMapping("loginFail.do")
	public String loginFailPage(HttpServletRequest request, RedirectAttributes redirect) {
		Object errormsg = request.getAttribute("errormsgname");
		redirect.addAttribute("errormsg", errormsg);
		return "redirect:/member/login.do";
	}

	@RequestMapping("menujoin.do")
	public String menujoin(HttpSession session, Model model) {
		System.out.println("회원가입메뉴로 이동(연규가씀)");

		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		// ------여기까지 네이버----//

		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 */
		model.addAttribute("url", naverAuthUrl);
		System.out.println("maverAuthUrl" + naverAuthUrl);

		return "user/member/menujoin";
	}

	@RequestMapping("normaljoin.do")
	public String joinPage() {
		System.out.println("회원가입으로 이동이동(연규가씀)");

		return "user/member/join";
	}

	// 일반회원가입처리
	@RequestMapping(value = "normaljoin.do", method = RequestMethod.POST)
	public String joininsert(@RequestParam(value = "file", required = false) MultipartFile ipload, Users users,
			HttpServletRequest request) throws IOException {

		users.setUser_pwd(this.bCryptPasswordEncoder.encode(users.getUser_pwd()));

		try {
			System.out.println("여긴오니...?");
			service.joininsert(users, request);

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return "redirect:/index.do"; // /index.htm
		// 주의사항
		// 요청 주소 ...아래처럼 ..
		// http://localhost:8090/SpringMVC_Basic06_WebSite_Annotation_JdbcTemplate/index.htm
		// return "redirect:noticeDetail.htm?seq="+n.getSeq();
	}

	@RequestMapping("find_Id.do")
	public String findIdPage() {
		System.out.println("아이디 찾기로 이동이동(연규가씀)");
		return "user/member/find_Id";
	}

	@RequestMapping("find_Passward.do")
	public String findPasswardPage() {
		System.out.println("비밀번호 찾기로 이동이동(연규가씀)");
		return "user/member/find_Passward";
	}

}
