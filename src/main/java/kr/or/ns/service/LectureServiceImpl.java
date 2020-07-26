package kr.or.ns.service;

import java.io.FileOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.ns.dao.BoardDao;
import kr.or.ns.dao.LectureDao;
import kr.or.ns.vo.BookMark;
import kr.or.ns.vo.Criteria;
import kr.or.ns.vo.Criteria_Select;
import kr.or.ns.vo.Study;

@Service
public class LectureServiceImpl implements LectureService {

	@Autowired
	private SqlSession sqlsession;

	// 총 게시글 수
	public int getLectureCount() {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		int count = dao.getLectureCount();
		return count;
	}

	// 게시글 목록
	public List<Map<String, Object>> getLectureList(Criteria cri, HashMap<String, Object> map4) {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		map4.put("cri", cri);
		List<Map<String, Object>> list = dao.getLectureList(map4);
		return list;
	}
	
	// 게시글 목록
		public List<Map<String, Object>> getLectureListSize(Criteria cri, HashMap<String, Object> map4) {
			LectureDao dao = sqlsession.getMapper(LectureDao.class);
			map4.put("cri", cri);
			List<Map<String, Object>> list = dao.getLectureListSize(map4);
			return list;
		}


	// 게시글 목록(필터)
	@Override
	public List<Map<String, Object>> getLectureListFilter(Criteria cri, HashMap<String, Object> params) {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		params.put("cri", cri);
		List<Map<String, Object>> list = dao.getLectureListFilter(params);
		return list;
	}

	// 게시글 목록(필터)
	@Override
	public List<Map<String, Object>> getLectureListFilterSize(Criteria cri, HashMap<String, Object> params) {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		params.put("cri", cri);
		List<Map<String, Object>> list = dao.getLectureListFilterSize(params);
		return list;
	}

	public HashMap<String, Object> getLecture(String l_seq) {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		HashMap<String, Object> map = dao.getLecture(l_seq);
		return map;
	}

	// 온라인컨텐츠(스터디 게시판 수정)
	@Transactional
	public int studyOnlineEdit(Study study, HttpServletRequest request, Principal principal) {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		BoardDao dao2 = sqlsession.getMapper(BoardDao.class);
		int s_seq = study.getS_seq();

		List<CommonsMultipartFile> files = study.getFiles();
		List<String> filenames = new ArrayList<String>(); // 파일명관리
		int count = 0;

		if (files != null && files.size() > 0) { // 최소 1개의 업로드가 있다면
			for (CommonsMultipartFile multifile : files) {
				String filename = multifile.getOriginalFilename();
				String path = request.getServletContext().getRealPath("/studyboard/upload");

				String fpath = path + "\\" + filename;

				if (!filename.equals("")) { // 실 파일 업로드
					try {
						FileOutputStream fs = new FileOutputStream(fpath);
						fs.write(multifile.getBytes());
						fs.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else { // filename이 공백일시넘 어옴
					for (int i = count; i < count + 1; i++) {
						if (i == 0) {
							filename = "defaultfile";
						} else if (i == 1) {
							filename = "defaultfile2";
						}
					}
				}
				count++;
				filenames.add(filename); // 파일명을 별도 관리 (DB insert)
			}
		}
		study.setUser_id(principal.getName());
		study.setC_seq(1); // 온라인강의 등록폼이니까 온라인 컨텐츠 정적 부여
		study.setFilesrc(filenames.get(0));
		study.setFilesrc2(filenames.get(1));
		Map<String, Object> map = null;
		try {
			String l_seq = dao2.getL_SEQ(Integer.toString(s_seq));
			map = dao.getLecture(l_seq);	
			study.setImage((String) map.get("L_IMAGE"));		
			int result = dao.studyOnlineEdit(study);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return 0;

	}

	// 사용자가 선택한 북마크 테이블에 넣기
	public void heartinsert(String user_id, String l_seq) {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		// BookMark 생성
		BookMark bk = new BookMark();
		bk.setL_seq(Integer.parseInt(l_seq));
		bk.setUser_id(user_id);

		try {
			// id&l_seq 값으로 북마크 유무 체크
			int result = dao.heartcheck(bk);
			if (result == 0) {
				// 없으면 insert
				bk.setBookmark_check(1);
				dao.heartinsert(bk);
			} else {
				// 있으면 update
				if (dao.heartnum(bk) == 0) {
					bk.setBookmark_check(1);
					dao.heartupdate(bk);
				} else {		
					bk.setBookmark_check(0);
					dao.heartupdate(bk);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	// 가져온 북마크 수
	@Override
	public int getBookmarkCount(String userid) {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);
		int count = dao.getBookmarkCount(userid);
		return count;
	}

	// 가져온 북마크 목록 페이징
	@Override
	public List<HashMap<String, Object>> getBookmarkList(HashMap<String, Object> map) {
		LectureDao dao = sqlsession.getMapper(LectureDao.class);

		Criteria_Select cri_s = (Criteria_Select) map.get("cri_s");
		map.put("pageStart", cri_s.getPageStart());
		map.put("perPageNum", cri_s.getPerPageNum());

		List<HashMap<String, Object>> list = dao.getBookmarkList(map);
		return list;
	}
	
	// 가져온 북마크 목록 페이징
		@Override
		public List<HashMap<String, Object>> getBookmarkListSize(HashMap<String, Object> map) {
			LectureDao dao = sqlsession.getMapper(LectureDao.class);

			Criteria_Select cri_s = (Criteria_Select) map.get("cri_s");

			map.put("pageStart", cri_s.getPageStart());
			map.put("perPageNum", cri_s.getPerPageNum());

			List<HashMap<String, Object>> list = dao.getBookmarkListSize(map);
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
