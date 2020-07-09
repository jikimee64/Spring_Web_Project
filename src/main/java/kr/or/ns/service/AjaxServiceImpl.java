package kr.or.ns.service;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import kr.or.ns.dao.AjaxRestDao;
import kr.or.ns.util.Mail;
import kr.or.ns.util.MailHandler;
import kr.or.ns.util.Mailer;
import kr.or.ns.util.Tempkey;
import kr.or.ns.vo.Users;
import sun.print.resources.serviceui;

@Service
public class AjaxServiceImpl implements AjaxService {
	@Autowired
	private SqlSession sqlsession;

	@Inject
	private JavaMailSender mailSender;
	
	@Autowired
	private Mailer mailer;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	/////////////////////////////////////////////////////////////////// 이름과 이메일 받아서 존재하는 회원인지 확인
	@Override
	public int emailCheck(String user_name, String user_email) {
		AjaxRestDao dao = sqlsession.getMapper(AjaxRestDao.class);
		int result = 0;
		HashMap<String, String> map = new HashMap<String, String>();
		System.out.println(user_name);
		System.out.println(user_email);
		
		map.put("user_name", user_name);
		map.put("user_email", user_email);
		result = dao.emailCheck(map);
		return result;
	}

	//////////////////////////////////////////////////////////////////// (암호키)이메일 전송하는 서비스
	@Override
	public String emailSend(String user_email) {
		String key = new Tempkey().getKey(6, false);
		System.out.println("#############################        이메일 전송서비스 왔습니다");
		
		try {
			System.out.println("트라이 구문 타러왔어요");
			Mail mail = new Mail();
			mail.setMailFrom("nosangcoding@gmail.com");
			mail.setMailTo(user_email);
			mail.setMailSubject("[이메일 인증번호 --노상코딩단]");
			mail.setTemplateName("forID.vm");
		 mailer.sendMail(mail,key);
			System.out.println("트라이 구문 타고갑니다");
			
		} catch (Exception e) {
			System.out.println("오류 나셨어요");
			System.out.println(e.getMessage());
		}
		System.out.println("키는 반환합니다");
		return key;
	}

//////////////////////////////////////////////////////////////////////id 찾아주는 함수
	@Override
	public String findId(String user_name, String user_email) {
		System.out.println("아이디 찾으러 왔다 ");
		System.out.println(user_name + user_email+"user_name + user_email");
		AjaxRestDao dao = sqlsession.getMapper(AjaxRestDao.class);
		
		HashMap<String, String> map = new HashMap();
		
		map.put("user_name", user_name);
		map.put("user_email", user_email);
		
		Users vo = dao.searchId(map);
		String id = vo.getUser_id();
		System.out.println("********************"+ id +"********************");
		return id;
	}
	
	
//////////////////////////////////////////////////////////////////////임시비밀번호 발급해주는 로직 
	@Override
	public void makeNewPw(String userid, String useremail) {
		//입력받은 id, email 있는지 확인
		AjaxRestDao dao = sqlsession.getMapper(AjaxRestDao.class);
		Users vo = new Users();
		vo.setUser_id(userid);
		vo.setUser_email(useremail);
		HashMap<String, String> map = new HashMap();
		map.put("user_id", userid);
		map.put("user_email", useremail);
		
					String key = new Tempkey().getKey(10, false);
					String temp_pw = key;
					vo.setUser_pwd(this.bCryptPasswordEncoder.encode(temp_pw));
					dao.updatePw(vo);
					
					try {
						Mail mail = new Mail();
						mail.setMailFrom("nosangcoding@gmail.com");
						mail.setMailTo(vo.getUser_email());
						mail.setMailSubject("[임시비밀번호 발급 --노상코딩단]");
						mail.setTemplateName("forPWD.vm");
						
						mailer.sendMail(mail,key);
						
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
	
//////////////////////////////////////////////////////////////////////ID 중복체크 해주는 로직
	public int idcheck(String user_id) throws ClassNotFoundException {
		AjaxRestDao dao = sqlsession.getMapper(AjaxRestDao.class);
		int result = dao.idcheck(user_id);	
		System.out.println("result:" + result);
		
		return result;
	}

	
//////////////////////////////////////////////////////////////////////받아온 아이디, 이메일로 id 확인	
	@Override
	public int searchId(String user_id, String user_email) {
		
		System.out.println("  아이디로 아이디 찾으러 왔다 ");
		System.out.println(user_id + user_email+"user_name + user_email");
		AjaxRestDao dao = sqlsession.getMapper(AjaxRestDao.class);
		
		HashMap<String, String> map = new HashMap();
		
		map.put("user_id", user_id);
		map.put("user_email", user_email);
		int result = 0;
		result = dao.checkEmail(map);
		
		System.out.println(result+"결과 찍어보기  dao 에서 가져온 반환값 ");
		return result;
	}
}
