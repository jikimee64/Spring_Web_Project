package kr.or.ns.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ns.dao.LectureDao;
import kr.or.ns.vo.Criteria;

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

}
