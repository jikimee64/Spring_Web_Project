package kr.or.ns.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.ns.dao.MyPageDao;
import kr.or.ns.vo.BookMark;
import kr.or.ns.vo.Users;

@Service
public class MyPageServiceImpl implements MyPageService {

	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	// 마이페이지(북마크)
	public List<Map<String, Object>> myPagelist(String userid) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		List<Map<String, Object>> myPagelist = dao.getBookMark(userid);
		return myPagelist;
	}

	// 회원 가져오기
	@Override
	public Users getUsers(String userid) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		Users user = dao.getUsers(userid);
		return user;
	}
	//회원정보 스킬 가져오기
	public List<HashMap<String, String>> getSkill(String userid) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		List<HashMap<String, String>> list = dao.getSkill(userid);
		return list;
	}

	// 회원정보 수정
	@Override
	@Transactional
	public int MyPageUserEdit(Users users, HttpServletRequest request) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);

		String filename = null;
		String path = null;
		String fpath = null;
		FileOutputStream fs = null;

		Users user = dao.getUsers(users.getUser_id());
		String origin_profile = user.getProfile_img();

		CommonsMultipartFile imagefile = users.getFile();

		if (!imagefile.isEmpty()) {
			filename = users.getFile().getOriginalFilename();
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
			if (!origin_profile.equals("member.png")) {
				users.setProfile_img(origin_profile);
			}
		}

		// true일시 세션이 있으면 기존꺼 사용 아니면 세션을새로 만듬
		HttpSession session = request.getSession(true);
		session.setAttribute("currentUser", users);

		int result = 0;
		int result2 = 0;

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
	}

	// 사용자 정보 가져오기
	@Override
	public Users userInfo(String user) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		Users man = dao.getUsers(user);
		return man;
	}

	// 북마크 갯수
	@Override
	public int bookmarkCount(String users) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		int count = dao.getbkCount(users);
		return count;
	}

	// 댓글 갯수
	@Override
	public int commentCount(String users) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		int count = dao.getcmCount(users);
		return count;
	}

	// 게시글 갯수
	@Override
	public int s_boardCount(String users) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		int count = dao.getsbCount(users);
		return count;
	}

	// 마이페이지(스터디리스트)
	@Override
	public List<HashMap<String, Object>> myPageStudyList(String user_id) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		List<HashMap<String, Object>> myPageStudyList = dao.myPageStudyList(user_id);
		return myPageStudyList;
	}
	

	@Override
	public List<HashMap<String, Object>> studyStatus(String userid) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		List<HashMap<String, Object>> myPageStudyList = dao.studyStatus(userid);
		return myPageStudyList;
	}

	@Override
	public HashMap<String, Object> getRole(HashMap<String, Object> map) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		HashMap<String, Object> getRole = dao.getRole(map);
		return getRole;
	}

	@Override
	public int join_study(String users) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		int count = dao.join_study(users);
		return count;
	}

	@Override
	public int recruit_study(String users) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		int count = dao.recruit_study(users);
		return count;
	}

	// 글 번호로 알아보는 모집 상태
	@Override
	public String getStatus(String s_seq) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		String result = dao.getStatus(s_seq);
		return result;
	}

}
