package kr.or.ns.vo;

import org.springframework.web.util.UriComponentsBuilder;

/*
클래스명 : Criteria - 페이지 VO
버전 정보 v.1.5
마지막 업데이트 날짜 : 2020 - 07 - 19
작업자 : 박민혜

검색 추가
*/

public class Criteria_Board {
	private int page;
	private int perPageNum;
//	private int pageStart; 이렇게 원래 써야 한다 이거 안써도 되지만 그건 직관적이 아니다 일종의 꼼수일뿐..
	
	//속성 searchType, keyword 추가
	private String searchType;
	private String keyword;
	
	
	
	
	//생성자(최초로 게시판 목록에 들어왔을 때를 위한 기본세팅)
	public Criteria_Board() {
		this.page = 1;   //시작페이지
		this.perPageNum = 10;  //페이지당 게시글 갯수
		this.searchType = null;
		this.keyword = null;
		
	}
	
	

	//시작페이지 메서드 
	public int getPageStart() {
	System.out.println("getPageStart");
	
		return (this.page-1)*perPageNum;
	}
	
	
	
	//페이지
	//겟터
	public int getPage() {
		return page;
	}
	
	//셋터(페이지 음수값 방지)
	public void setPage(int page) {
		if(page <= 0) {
			this.page = 1; //음수가 되면 1페이지를 나타낸다
		}else {
		this.page = page;
		}
	}
	
	
	//페이지 갯수
	//겟터
	public int getPerPageNum() {
		return perPageNum;
	}
	
	
	//셋터
	public void setPerPageNum(int pageCount) { //페이지당 보여줄 게시글 수가 변하지않게 설정
		int cnt = this.perPageNum;
		
		if(pageCount != cnt) {
			this.perPageNum = cnt;
		}else {
			this.perPageNum = pageCount;
		}
	}
	
	
	
	
	
	
	
	public String getSearchType() {
		return searchType;
	}



	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}



	public String getKeyword() {
		return keyword;
	}



	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	
	
	
	
	
	
	
	
	
	
	
	/////////////////////////////////////////////////////////



	//searchType, keyword 추가
//	public String makeQuery() {
//		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
//				.queryParam("page", page)
//				.queryParam("perPageNum", this.perPageNum);
//		
//		
//		
//		
//		//searchType 이 null인지 아닌지 확인하는 작업을 통해 검색한 경우와 하지않은 경우의 uri를 다르게 구현
//		//검색을 하지 않았는데 굳이 searchType과 keyword를 달고 다닐 필요가 없으므로
//		if(searchType != null) {
//			uriComponentsBuilder.queryParam("searchType", this.searchType)
//			.queryParam("keyword", this.keyword);
//			
//		}
//		
//		return uriComponentsBuilder.build().encode().toString();
//		
//	}


	/////////////////////////////////////////////////////////////////////////
	
	
	public String[] getTypeArr() {
		return searchType == null? new String[] {}: searchType.split("");
	}

	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////
	
	
	@Override
	public String toString() {
		return "Criteria_Board [page=" + page + ", perPageNum=" + perPageNum + ", searchType=" + searchType
				+ ", keyword=" + keyword + "]";
	}
	
	
	
	
} //끝
