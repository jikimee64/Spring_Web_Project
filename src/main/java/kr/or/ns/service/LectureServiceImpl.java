package kr.or.ns.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ns.dao.LectureDao;
import kr.or.ns.vo.BookMark;
import kr.or.ns.vo.Criteria;
import kr.or.ns.vo.Criteria_Select;

@Service
public class LectureServiceImpl implements LectureService{

	@Autowired
	private SqlSession sqlsession;
	
	//총 게시글 수
	public int getLectureCount() {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		int count = dao.getLectureCount();
		
		return count;
	}

	//게시글 목록
	public List<Map<String, Object>> getLectureList(Criteria cri) {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		List<Map<String, Object>> list = dao.getLectureList(cri);
		return list;
	}
	
	public HashMap<String, Object> getLecture(String l_seq){
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		System.out.println("아나!!");
		HashMap<String, Object> map = dao.getLecture(l_seq);
		System.out.println("하나만떠라제발 : " + map);
		return map;
	}
	
	//사용자가 선택한 북마크 테이블에 넣기
	public void heartinsert(String user_id, String l_seq) {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		System.out.println("여기 오지?");
		//BookMark 생성
		BookMark bk = new BookMark();
		bk.setL_seq(Integer.parseInt(l_seq));
		bk.setUser_id(user_id);
		
		try {
			//id&l_seq 값으로 북마크 유무 체크
			int result =  dao.heartcheck(bk);
			System.out.println( "---------------------   result 찍어보기 -----------------------");
			System.out.println(result);
			System.out.println( "---------------------------------------------------------------");
			 if(result == 0 ) {
				 //없으면 insert
				 System.out.println("넣으러 왔어요"); 
				 bk.setBookmark_check(1);
				 dao.heartinsert(bk); 
			 }else { 
					 //있으면 update
					 System.out.println("update 갑니다."); 
					 
					 if(dao.heartnum(bk) == 0 ) {
						 //1 넣으러 왔어요
						 System.out.println("1 넣으러 왔어요");
						 bk.setBookmark_check(1);
						 dao.heartupdate(bk);
					 }else {
						 //0 넣으러 왔어요
						 System.out.println("0 넣으러 왔어요");
						 bk.setBookmark_check(0);
						 dao.heartupdate(bk);
					 }
			 }
			 
		} catch (Exception e) {
			System.out.println("오류 났어요.");
			System.out.println(e.getMessage());
		}
		
	}

	
	//가져온 북마크 수
	@Override
	public int getBookmarkCount(String userid) {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		int count = dao.getBookmarkCount(userid);
		
		return count;
	}

	
	
	//가져온 북마크 목록 페이징 
	@Override
	public List<HashMap<String, Object>> getBookmarkList(HashMap<String, Object> map) {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		
		Criteria_Select cri_s = (Criteria_Select) map.get("cri_s");
		
		System.out.println(cri_s.getPage());
		System.out.println(cri_s.getPageStart());
		System.out.println(cri_s.getPerPageNum());
		
		
		map.put("pageStart",cri_s.getPageStart());
		map.put("perPageNum",cri_s.getPerPageNum());
		
		List<HashMap<String, Object>> list = dao.getBookmarkList(map);
		return list;
	}

	@Override
	public List<Integer> getCheckedL_seq(String user_id) {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		List<Integer> seqlist = new ArrayList<Integer>();
		seqlist = dao.getCheckedL_seq(user_id);
		return seqlist;
	}



	
}
