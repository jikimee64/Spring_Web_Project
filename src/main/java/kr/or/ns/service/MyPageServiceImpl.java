package kr.or.ns.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.ns.dao.MyPageDao;
import kr.or.ns.vo.Users;

@Repository
public class MyPageServiceImpl implements MyPageService {

	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	// 회원 가져오기
	@Override
	public Users getUsers(String userid) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		Users user = dao.getUsers(userid);
		return user;
	}

	public List<HashMap<String, String>> getSkill(String userid) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		List<HashMap<String, String>> list = dao.getSkill(userid);
		System.out.println("우철 : " + list);
		return list;
	}

	// 회원정보 수정
	@Override
	@Transactional
	public int MyPageUserEdit(Users users, HttpServletRequest request) {

		System.out.println("서비스오나요");
		System.out.println("유저정보" + users.getUser_id());
		System.out.println("기타 : " + users.getIntroduce());

		String filename = null;
		String path = null;
		String fpath = null;
		FileOutputStream fs = null;

		CommonsMultipartFile imagefile = users.getFile();
		if (!imagefile.isEmpty()) {
			System.out.println("받아온 이미지파일이름" + imagefile);
			filename = users.getFile().getOriginalFilename();
			System.out.println("파일 이름 : " + filename);
			path = request.getServletContext().getRealPath("/userboard/upload");
			fpath = path + "\\" + filename;
			users.setProfile_img(filename);
			try {
				fs = new FileOutputStream(fpath);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				fs.write(users.getFile().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			users.setProfile_img("member.png");
		}

		int result = 0;
		int result2 = 0;

		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);

		HashMap<String, String> map = new HashMap();
		map.put("user_id", users.getUser_id());
		map.put("skill_name", "java");
		map.put("skill_level", users.getJava());

		HashMap<String, String> map2 = new HashMap();
		map2.put("user_id", users.getUser_id());
		map2.put("skill_name", "python");
		map2.put("skill_level", users.getPython());

		HashMap<String, String> map3 = new HashMap();
		map3.put("user_id", users.getUser_id());
		map3.put("skill_name", "html_css");
		map3.put("skill_level", users.getHtml_css());

		HashMap<String, String> map4 = new HashMap();
		map4.put("user_id", users.getUser_id());
		map4.put("skill_name", "javascript");
		map4.put("skill_level", users.getJavascript());

		HashMap<String, String> map5 = new HashMap();
		map5.put("user_id", users.getUser_id());
		map5.put("skill_name", "sql");
		map5.put("skill_level", users.getSql());

		try {
			result = dao.MyPageUserEdit(users);
			System.out.println("회원강비 결과 : " + result);
			result2 = dao.editskill(map);
			result2 = dao.editskill(map2);
			result2 = dao.editskill(map3);
			result2 = dao.editskill(map4);
			result2 = dao.editskill(map5);
			System.out.println("정상적인 처리 일 때 출력되는 부분 insert 정상, update 정상");
		} catch (Exception e) {
			System.out.println("둘 중에 하나라도 문제가 생기면 예외가 떨어지는 부분" + e.getMessage());
		}

		return result;
	}

	// 회원 탈퇴
	@Override
	public void userDelete(String user) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		dao.userDelete(user);
		System.out.println("탈퇴오는거겠지");
	}

}
