package kr.or.ns.util;

import java.io.StringWriter;
import java.security.Principal;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.google.protobuf.Message;

public class Mailer {
	// JavaMailSenderImpl을 class로지정

	private JavaMailSender mailSender;

	private VelocityEngine velocityEngine;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	//.vm파일과 키값을 합쳐 html태그로 메일 전송
	public void sendMail(Mail mail, String key) {

		MimeMessage message = mailSender.createMimeMessage();
		// JavaMailSender 인터페이스에서  createMimeMessage()를 이용해서 주소 받기
		try {
			message.setFrom(new InternetAddress(mail.getMailFrom()));
			message.addRecipient(RecipientType.TO, new InternetAddress(mail.getMailTo()));
			message.setSubject(mail.getMailSubject());
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		try {
			System.out.println("getFrom : " + message.getFrom());
			System.out.println("setSubject : " + message.getSubject());
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}

		Template template = velocityEngine.getTemplate(mail.getTemplateName(), "UTF-8");

		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("key", key);

		StringWriter stringWriter = new StringWriter();

		template.merge(velocityContext, stringWriter);

		try {
			message.setText(stringWriter.toString(), "utf-8");
			message.setContent(stringWriter.toString(), "text/html; charset=MS949");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		mailSender.send(message);
	}
}
