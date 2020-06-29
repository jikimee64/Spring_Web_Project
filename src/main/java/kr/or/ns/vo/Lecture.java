package kr.or.ns.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Lecture {
	private int l_seq;
	private String cate_level;
	private int p_seq;
	private int site_seq;
	private int lan_seq;
	private String l_title;
	private String l_content;
	private String l_image;

}
