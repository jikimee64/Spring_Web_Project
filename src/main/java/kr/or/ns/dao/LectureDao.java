package kr.or.ns.dao;

import java.util.List;
import java.util.Map;

import kr.or.ns.vo.Criteria;

public interface LectureDao {

	//글목록
	public List<Map<String, Object>> getLectureList(Criteria cri);

	//총 게시글 수
	public int getLectureCount();

}
