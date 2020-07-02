package kr.or.ns.vo;

/*
클래스명 : Criteria - 페이지 VO
버전 정보 v.1.0
마지막 업데이트 날짜 : 2020 - 07 - 01
작업자 : 박민혜
*/

public class Criteria {
	private int page;
	private int perPageNum;
	
	
	//생성자(최초로 게시판 목록에 들어왔을 때를 위한 기본세팅)
	public Criteria() {
		this.page = 1;   //시작페이지
		this.perPageNum = 10;  //페이지 갯수
	}
	
	

	//시작페이지 메서드 
	public int getPageStart() {
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
}
