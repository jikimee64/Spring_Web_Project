package kr.or.ns.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import kr.or.ns.dao.AjaxRestDao;
import kr.or.ns.util.MailHandler;
import kr.or.ns.util.Tempkey;

@Service
public class AjaxServiceImpl implements AjaxService {
	@Autowired
	private SqlSession sqlsession;

	@Inject
	private JavaMailSender mailSender;

	// 이름과 이메일 받아서 존재하는 회원인지 확인
	public int emailCheck(String user_name, String user_email) {
		AjaxRestDao dao = sqlsession.getMapper(AjaxRestDao.class);
		int result = 0;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_name", user_name);
		map.put("user_email", user_email);
		result = dao.emailCheck(map);
		return result;
	}

	// 이메일 전송하는 서비스
	public String emailSend(String user_email) {
		String key = new Tempkey().getKey(6, false);

		try {

			MailHandler sendMail = new MailHandler(mailSender);

			// 제목
			sendMail.setSubject("[이메일 인증번호]");
			// 내용
			sendMail.setText(new StringBuffer().append("<h1>메일인증번호입니다</h1>").append("발품에 가입해주셔서 감사합니다. <br/>")
					.append("고객님의 인증번호는 [ " + key + " ] 입니다.").toString());
			// 보낸사람
			sendMail.setFrom("balpoom@balpoom.com", ".노상");
			// 받는사람
			sendMail.setTo(user_email);
			sendMail.send();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	
	public int idcheck(String user_id) throws ClassNotFoundException {
		System.out.println("여기오나");
		AjaxRestDao dao = sqlsession.getMapper(AjaxRestDao.class);
		int result = dao.idcheck(user_id);	
		System.out.println("result" + result);
		return result;
	}
}
