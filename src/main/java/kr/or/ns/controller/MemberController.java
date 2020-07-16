package kr.or.ns.controller;

import java.io.IOException;
import java.sql.SQLException;
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
import kr.or.ns.service.AjaxService;
import kr.or.ns.service.MemberService;
import kr.or.ns.service.MyPageService;
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
	AjaxService ajaxservice;

	@Autowired
	MyPageService mypageservice;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping(value = "normallogin.do", method = RequestMethod.GET)
	public String loginPage(@RequestParam(value = "errormsg", required = false) Object errormsg,
			HttpServletRequest request) {

		if (errormsg != null) {
			request.setAttribute("errormsgname", (String) errormsg);
		}
		return "user/member/login";
	}

	@RequestMapping(value = "login.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String socialloginPage(@RequestParam(value = "errormsg", required = false) Object errormsg,
			HttpServletRequest request, HttpSession session, Model model) {

		// 네이버 로그인 인증을 위한 url 생성
		String naverUrl = naverLoginBO.getAuthorizationUrl(session);
		
		//카카오 로그인 인증을 위한 url 생성

		// 메인이외의 페이지에서 로그인했을시 해당 페이지로 return시켜주기위한 url
		session.setAttribute("loginPage", request.getRequestURL().toString());

		// 넘어온 페이지
		session.setAttribute("returnUrl", (String) request.getHeader("REFERER"));
		
		// 네이버 넘어온 페이지
		model.addAttribute("naver_url", naverUrl); // 네이버 url\
		
		//카카오 url

		return "user/member/login";
	}

	// 네이버 회원가입 성공시 callback호출 메소드
	@RequestMapping(value = "join.do", method = RequestMethod.GET)
	public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session,
			HttpServletRequest request, Users user) throws SQLException, Exception {
		System.out.println("여기는 callback");
		OAuth2AccessToken oauthToken;

		oauthToken = naverLoginBO.getAccessToken(session, code, state);
		// 1. 로그인 사용자 정보를 읽어온다.
		apiResult = naverLoginBO.getUserProfile(oauthToken); // String형식의 json데이터

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
		System.out.println(email);

		String returnUrl = (String) session.getAttribute("returnUrl");
		System.out.println(returnUrl);

		// DB에 등록된 이메일인지 확인
		int check = 0;
		try {
			check = ajaxservice.idcheck(email);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (check == 0) {
			System.out.println("DB에 등록되지 않은 이메일");
			System.out.println("naver회원가입으로 이동");

			model.addAttribute("uemail", email);
			model.addAttribute("snsType", "naver");
			return "user/member/join"; // 여기 주소 확인해봐야될듯. 가입하지 않은 회원이면 회원가입으로 이동시켜주기

		}

		// 스프링 시큐리티 수동 로그인을 위한 작업//
		// 로그인 세션에 들어갈 권한을 설정
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority("ROLE_USER"));

		SecurityContext sc = SecurityContextHolder.getContext();
		// 아이디, 패스워드, 권한을 설정. 아이디는 Object단위로 넣어도 무방하며
		// 패스워드는 null로 하여도 값이 생성.
		sc.setAuthentication(new UsernamePasswordAuthenticationToken(email, null, list));
		session = request.getSession(true);

		// 위에서 설정한 값을 Spring security에서 사용할 수 있도록 세션에 설정
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
		// 스프링 시큐리티 수동 로그인을 위한 작업 끝//

		System.out.println("이미 가입된 회원");
		Users users = new Users();
		users = mypageservice.getUsers(email);
		session = request.getSession();
		session.setAttribute("currentUser", users);
		System.out.println("이미 가입된 회원의 정보" + users);

		/*
		 * if (returnUrl.equals((String) session.getAttribute("loginPage"))) {
		 * System.out.println("로그인페이지에서의 접근"); session.removeAttribute("loginPage");
		 * returnUrl = "/"; }
		 */

		return "redirect:../user/main.do";
	}

	// 소셜회원가입처리
	@RequestMapping(value = "join.do", method = RequestMethod.POST)
	public String socialJoin(@RequestParam(value = "file", required = false) MultipartFile ipload, Users users,
			HttpServletRequest request, HttpSession session) throws SQLException, Exception {

		int loginusers = service.socialjoininsert(users, request);
		System.out.println("유저네임: " + users.getUser_id());

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

		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 */
		model.addAttribute("url", naverAuthUrl);
		System.out.println("maverAuthUrl" + naverAuthUrl);

		return "user/member/menujoin";
	}

	@RequestMapping("normaljoin.do")
	public String joinPage() {
		System.out.println("회원가입으로dddd 이동이동(연규가씀)");

		return "user/member/join";
	}

	// 일반회원가입처리
	@RequestMapping(value = "normaljoin.do", method = RequestMethod.POST)
	public String joininsert(@RequestParam(value = "file", required = false) MultipartFile ipload, Users users,
			HttpServletRequest request) throws IOException {

		users.setUser_pwd(this.bCryptPasswordEncoder.encode(users.getUser_pwd()));

		try {
			System.out.println("일반여긴오니...?");
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
