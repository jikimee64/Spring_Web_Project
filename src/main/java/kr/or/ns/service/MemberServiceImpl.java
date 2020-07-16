package kr.or.ns.service;

import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import kr.or.ns.dao.MemberDao;
import kr.or.ns.vo.Users;

@Service
public class MemberServiceImpl implements MemberService {

	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	@Override
	@Transactional
	public int joininsert(Users users, HttpServletRequest request) throws Exception, SQLException {
		
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
			fs = new FileOutputStream(fpath);
			fs.write(users.getFile().getBytes());
			fs.close();
		} else {
			users.setProfile_img("member.png");
		}
		
	
		int result = 0;
		int result2 = 0;

		MemberDao dao = sqlsession.getMapper(MemberDao.class);

		List<HashMap<String, String>> list = new ArrayList();

		HashMap<String, String> map = new HashMap();
		map.put("user_id", users.getUser_id());
		map.put("skill_name", "java");
		map.put("skill_level", users.getJava());
		list.add(map);

		HashMap<String, String> map2 = new HashMap();
		map2.put("user_id", users.getUser_id());
		map2.put("skill_name", "python");
		map2.put("skill_level", users.getPython());
		list.add(map2);

		HashMap<String, String> map3 = new HashMap();
		map3.put("user_id", users.getUser_id());
		map3.put("skill_name", "html_css");
		map3.put("skill_level", users.getHtml_css());
		list.add(map3);

		HashMap<String, String> map4 = new HashMap();
		map4.put("user_id", users.getUser_id());
		map4.put("skill_name", "javascript");
		map4.put("skill_level", users.getJavascript());
		list.add(map4);

		HashMap<String, String> map5 = new HashMap();
		map5.put("user_id", users.getUser_id());
		map5.put("skill_name", "sql");
		map5.put("skill_level", users.getSql());
		list.add(map5);

		HashMap<String, Object> mo = new HashMap();
		mo.put("insertlist", list);
		
		System.out.println( "실력리스트"+ list);
		try {
			result = dao.joininsert(users);
			System.out.println("일반 회원가입 결과 : "  +result);
			result2 = dao.insertskill(mo);
			System.out.println("정상적인 처리 일 때 출력되는 부분 insert 정상, update 정상");
	
		} catch (Exception e) {
			System.out.println("둘 중에 하나라도 문제가 생기면 예외가 떨어지는 부분" + e.getMessage());
			throw e; // 예외를 다시 돌려줌. 그리고 이 예외가 발생하는 시점에 transactionManager가 감시를 하다가 rollback 처리를 한다.
		}

		return result;
	}
	
	
	//소셜 로그인 회원가입
	@Override
	public Users socialjoininsert(Users users, HttpServletRequest request) throws Exception, SQLException {
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
			fs = new FileOutputStream(path);
			fs.write(users.getFile().getBytes());
			fs.close();
		} else {
			users.setProfile_img("member.png");
		}
		
		Users result = null;
		int result2 = 0;

		MemberDao dao = sqlsession.getMapper(MemberDao.class);

		List<HashMap<String, String>> list = new ArrayList();

		HashMap<String, String> map = new HashMap();
		map.put("user_id", users.getUser_id());
		map.put("skill_name", "java");
		map.put("skill_level", users.getJava());
		list.add(map);

		HashMap<String, String> map2 = new HashMap();
		map2.put("user_id", users.getUser_id());
		map2.put("skill_name", "python");
		map2.put("skill_level", users.getPython());
		list.add(map2);

		HashMap<String, String> map3 = new HashMap();
		map3.put("user_id", users.getUser_id());
		map3.put("skill_name", "html_css");
		map3.put("skill_level", users.getHtml_css());
		list.add(map3);

		HashMap<String, String> map4 = new HashMap();
		map4.put("user_id", users.getUser_id());
		map4.put("skill_name", "javascript");
		map4.put("skill_level", users.getJavascript());
		list.add(map4);

		HashMap<String, String> map5 = new HashMap();
		map5.put("user_id", users.getUser_id());
		map5.put("skill_name", "sql");
		map5.put("skill_level", users.getSql());
		list.add(map5);

		HashMap<String, Object> mo = new HashMap();
		mo.put("insertlist", list);
		
		System.out.println( "실력리스트"+ list);
		try {
			result = dao.socialjoininsert(users);
			System.out.println("소셜회원가입비 결과 : "  +result);
			result2 = dao.insertskill(mo);
			System.out.println("정상적인 처리 일 때 출력되는 부분 insert 정상, update 정상");
	
		} catch (Exception e) {
			System.out.println("둘 중에 하나라도 문제가 생기면 예외가 떨어지는 부분" + e.getMessage());
			throw e; // 예외를 다시 돌려줌. 그리고 이 예외가 발생하는 시점에 transactionManager가 감시를 하다가 rollback 처리를 한다.
		}

		return result;
	}
		

}
