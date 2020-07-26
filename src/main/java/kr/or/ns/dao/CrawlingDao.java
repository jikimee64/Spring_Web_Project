package kr.or.ns.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CrawlingDao {
	//크롤링으로 가져온 데이터를 DB에 삽입하는 함수
	public int insertStudy(HashMap<String, Object> mo);
}
