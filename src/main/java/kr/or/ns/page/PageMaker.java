package kr.or.ns.page;

import kr.or.ns.vo.Criteria;


/*
 클래스명 : PageMaker - 페이징 계산 및 처리하는 클래스
 버전 정보 v.1.0
 마지막 업데이트 날짜 : 2020 - 07 - 01
 작업자 : 박민혜
*/


//페이징 버튼 만들거임
public class PageMaker {
	private Criteria cri; //pageVO
	private int totalCount; //총 게시글수
	private int startPage; //화면에 보여질 첫번째 페이지 번호,시작페이지 번호
	private int endPage; //화면에 보여질 마지막 페이지 번호,끝페이지 번호
	private boolean prev; //이전
	private boolean next; //다음
	private int displayPageNum = 5;  //5개씩 보여준다
	
	
	//VO
	public Criteria getCri() {
		return cri;
	}
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	
	
	//총 게시글 수
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData(); //얘는 뭐지? 계산?
	}
	
	
	//페이징 관련 버튼 계산
	private void calcData() {
		//끝페이지    				------------현재 페이지 번호		
		endPage = (int)(Math.ceil(cri.getPage()/(double)displayPageNum)*displayPageNum);
	//  끝페이지 번호 = (현재 페이지 번호/화면에 보여질 페이지 번호의 갯수)*화면에 보여질 페이지 번호의 갯수
		
		//시작페이지
		startPage=(endPage-displayPageNum) + 1;
	//  시작페이지 번호 = (끝페이지 번호 - 화면에 보여질 페이지 번호의 갯수)+1	
		if(startPage <= 0) {
			startPage = 1;
		}
															 //---------------한 페이지 당 보여줄 게시글의 갯수
		int tempEndPage = (int)(Math.ceil(totalCount/(double)cri.getPerPageNum()));
	//  마지막 페이지 번호 = 총 게시글 수 /한 페이지당 보여줄 게시글의 갯수	
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		//이전
		//이전버튼 생성여부 = 시작페이지 번호 ==1? false:true
		prev = startPage == 1? false : true;
		//다음
		//다음버튼 생성여부 = 끝페이지 번호 * 한페이지당 보여줄 게시글의 갯수 < 총 게시글의 수? true:false;
		next = endPage*cri.getPerPageNum() < totalCount? true : false;
	}
	
	
	//시작페이지
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	
	//끝페이지
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	//이전
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	
	//다음
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	
	//화면 하단에 보여지는 페이지 버튼의 갯수
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	
	
}
