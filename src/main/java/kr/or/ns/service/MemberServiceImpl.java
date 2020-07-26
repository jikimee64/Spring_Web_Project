package kr.or.ns.service;

import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import kr.or.ns.dao.MemberDao;
import kr.or.ns.vo.Users;

@Service
@Repository
public class MemberServiceImpl implements MemberService {

	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	//일반 회원가입
	@Override
	@Transactional
	public int joininsert(Users users, HttpServletRequest request) throws Exception, SQLException {

		String filename = null;
		String path = null;
		String fpath = null;
		FileOutputStream fs = null;

		CommonsMultipartFile imagefile = users.getFile();
		if (!imagefile.isEmpty()) {
			filename = users.getFile().getOriginalFilename();
			path = request.getServletContext().getRealPath("/userboard/upload");
			fpath = path + "\\" + filename;
			users.setProfile_img(filename);
			fs = new FileOutputStream(fpath);
			fs.write(users.getFile().getBytes());
			fs.close();
		} else {
			users.setProfile_img("member.png");
		}
		
		if(users.getSnstype() == null) {
			users.setSnstype("normal");
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
		
		try {
			result = dao.joininsert(users);
			result2 = dao.insertskill(mo);
		} catch (Exception e) {
			System.out.println("둘 중에 하나라도 문제가 생기면 예외가 떨어지는 부분" + e.getMessage());
			throw e; // 예외를 다시 돌려줌. 그리고 이 예외가 발생하는 시점에 transactionManager가 감시를 하다가 rollback 처리를 한다.
		}

		return result;
	}
	
	
	//소셜 로그인 회원가입
	@Override
	@Transactional
	public int socialjoininsert(Users users, HttpServletRequest request) throws Exception, SQLException {
		String filename = null;
		String path = null;
		String fpath = null;
		FileOutputStream fs = null;

		CommonsMultipartFile imagefile = users.getFile();
		if (!imagefile.isEmpty()) {
			filename = users.getFile().getOriginalFilename();
			path = request.getServletContext().getRealPath("/userboard/upload");
			fpath = path + "\\" + filename;
			users.setProfile_img(filename);
			fs = new FileOutputStream(fpath);
			fs.write(users.getFile().getBytes());
			fs.close();
		} else {
			users.setProfile_img("member.png");
		}
		
		
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
		
		
		
		int result = 0;
		int result2 = 0;

		try {
			result = dao.socialjoininsert(users);
			result2 = dao.insertskill(mo);
		} catch (Exception e) {
			System.out.println("둘 중에 하나라도 문제가 생기면 예외가 떨어지는 부분" + e.getMessage());
			throw e; // 예외를 다시 돌려줌. 그리고 이 예외가 발생하는 시점에 transactionManager가 감시를 하다가 rollback 처리를 한다.
		}

		return result;
	}

	
	@Override
	public Users confirmsocial(String user_id) {
		MemberDao dao = sqlsession.getMapper(MemberDao.class);
		 Users user = dao.confirmsocial(user_id);
		
		return user;
	}
		

}
