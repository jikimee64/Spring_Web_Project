package kr.or.ns.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ns.dao.BoardDao;
import kr.or.ns.dao.MainDao;

@Service
public class MainServiceImpl implements MainDao {

	@Autowired
	private SqlSession sqlsession;
	
	@Override
	public List<Map<String, Object>> getNewListStudy() {
		MainDao dao = sqlsession.getMapper(MainDao.class);
		List<Map<String, Object>> list = dao.getNewListStudy();
		return list;
	}

}
