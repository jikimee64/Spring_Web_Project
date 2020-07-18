package kr.or.ns.crawling.vo;

import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UdemyItem {
	private int id;
	private String title;
	private String url;
	private String is_paid;
	private String image_750x422;
	private float rating;
	private int num_reviews;
	private String instructional_level;
	private String instructional_level_simple;
	private List<UdemyvsibleInstructors> visible_instructors;
	private UdemyContextInfo context_info; 
	
	
	//author는 작성자가 2명이상 배열로 출력될때 나올때 배열에 있는 값을 한문자열로 뽑을려고 만듬
	private String author;
	
	public String getAuthor() {
		if (CollectionUtils.isEmpty(this.visible_instructors)) {
			return "";
		}
		
		String author = "";
		for(UdemyvsibleInstructors visibleInstructor : this.visible_instructors) {
			author += " " + visibleInstructor.getDisplay_name();
		}
		return author.trim();
	}

}
