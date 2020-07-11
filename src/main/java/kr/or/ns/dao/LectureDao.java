package kr.or.ns.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ns.vo.BookMark;
import kr.or.ns.vo.Criteria;
import kr.or.ns.vo.Criteria_Select;

public interface LectureDao {

	//글목록
	public List<Map<String, Object>> getLectureList(Criteria cri);

	//총 게시글 수
	public int getLectureCount();
	
	//북마크 추가
	public void heartinsert(BookMark bk);
	
	//북마크 유무 확인
	public int heartcheck(BookMark bk);
	
	//북마크 체크유무 컬럼 업데이트
	public void heartupdate(BookMark bk);
	
	//북마크 체크컬럼 0 or 1 가져오기 
	public int heartnum(BookMark bk);

	//가져온 북마크 갯수
	public int getBookmarkCount(String userid);

	//가져온 북마크 목록 페이징
	public List<HashMap<String, Object>> getBookmarkList(HashMap<String, Object> map);

}
