package kr.or.ns.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Likes {
	private String user_id;
	private int s_seq;
	private int like_check;
}
