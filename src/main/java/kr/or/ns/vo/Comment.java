package kr.or.ns.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Comment {
	private int r_seq;
	private int s_seq;
	private String r_content;
	private String r_name;
	private Date r_date;
	private int r_refer;
	private int r_depth;
	private int r_step;
}
