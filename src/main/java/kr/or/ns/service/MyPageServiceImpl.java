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
		System.out.println("마이페이지 서비스 잘 오는가" + myPagelist);

		return myPagelist;
	}

	// 회원 가져오기
	@Override
	public Users getUsers(String userid) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		System.out.println("서비스 : " + userid);
		Users user = dao.getUsers(userid);
		System.out.println("서비스2 : " + user);
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

		// true일시 세션이 있으면 기존꺼 사용 아니면 세션을새로 만듬
		HttpSession session = request.getSession(true);
		session.setAttribute("currentUser", users);

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
		System.out.println("--------------" + count + "--------------");
		return count;
	}

	// 댓글 갯수
	@Override
	public int commentCount(String users) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		int count = dao.getcmCount(users);
		System.out.println("--------------" + count + "--------------");
		return count;
	}

	// 게시글 갯수
	@Override
	public int s_boardCount(String users) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		int count = dao.getsbCount(users);
		System.out.println("--------------" + count + "--------------");
		return count;
	}

	// 마이페이지(스터디리스트)
	@Override
	public List<HashMap<String, Object>> myPageStudyList(String user_id) {
		System.out.println("불러와야 한다");
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		List<HashMap<String, Object>> myPageStudyList = dao.myPageStudyList(user_id);
		System.out.println("이거 되는거니...?" + myPageStudyList);
		
		
		return myPageStudyList;
	}

	@Override
	public List<HashMap<String, Object>> studyStatus(String userid) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		List<HashMap<String, Object>> myPageStudyList = dao.studyStatus(userid);
		System.out.println("이거 되는거니...?" + myPageStudyList);

		return myPageStudyList;
	}

	@Override
	public HashMap<String, Object> getRole(HashMap<String, Object> map) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		HashMap<String, Object> getRole = dao.getRole(map);
		System.out.println(getRole);
		return getRole;
	}

	@Override
	public int join_study(String users) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		int count = dao.join_study(users);
		System.out.println("--------------" + count + "--------------");
		return count;
	}

	@Override
	public int recruit_study(String users) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		int count = dao.recruit_study(users);
		System.out.println("--------------" + count + "--------------");
		return count;
	}

	//글 번호로 알아보는 모집 상태
	@Override
	public String getStatus(String s_seq) {
		MyPageDao dao = sqlsession.getMapper(MyPageDao.class);
		String result = dao.getStatus(s_seq);
		return result;
	}


	

}
