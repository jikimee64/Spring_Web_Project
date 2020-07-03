package kr.or.ns.util;


import java.io.StringWriter;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class Mailer {
		private MailSender mailSender;
		private VelocityEngine velocityEngine;
		
	
		public void setMailSender(MailSender mailSender) {
			this.mailSender = mailSender;
		}
	 
		public void setVelocityEngine(VelocityEngine velocityEngine) {
			this.velocityEngine = velocityEngine;
		}
	 
		public void sendMail(Mail mail,String key) {
			
			SimpleMailMessage message = new SimpleMailMessage();
			
			message.setFrom(mail.getMailFrom());
			message.setTo(mail.getMailTo());
			message.setSubject(mail.getMailSubject());
	 
			/*
			 * Template template = velocityEngine.getTemplate("./templates/" +
			 * mail.getTemplateName());
			 */
			Template template = velocityEngine.getTemplate(mail.getTemplateName(), "UTF-8");
	 
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("key", key);
	 
			StringWriter stringWriter = new StringWriter();
	 
			
			template.merge(velocityContext, stringWriter);
			
			message.setText(stringWriter.toString());
			
			mailSender.send(message);
		}
	}

