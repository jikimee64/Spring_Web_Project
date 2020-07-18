package kr.or.ns.service;

import java.util.List;
import java.util.Map;

public interface CrawlingService {
	//크롤링한 데이터 인서트
	public int insertStudy(List<Map<String, Object>> map); 

}
