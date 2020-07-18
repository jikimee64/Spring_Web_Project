package kr.or.ns.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ns.dao.CrawlingDao;

@Service
public class CrawlingServiceImpl implements CrawlingService{ 

	@Autowired
	private SqlSession sqlsession;
	
	@Override
	public int insertStudy(List<Map<String, Object>> map) {
		
		CrawlingDao dao = sqlsession.getMapper(CrawlingDao.class);
		
		HashMap<String, Object> mo = new HashMap();
		mo.put("onlineStudyList", map);
		System.out.println("사이즈dsasad : " + map.size());
		
		int result = dao.insertStudy(mo);
		
		return result;
	} 

}
