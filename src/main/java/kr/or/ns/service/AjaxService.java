package kr.or.ns.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface AjaxService {
	public int emailCheck(String user_name, String user_email);
	public String emailSend(String useremail);
	public String findId(String user_name, String user_email);
	public void makeNewPw(String userid, String useremail);

	
	public int idcheck(String user_id) throws ClassNotFoundException;

}
