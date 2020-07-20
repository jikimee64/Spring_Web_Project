package kr.or.ns.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatRoom {
	private int ch_seq;
	private String user_id;
	private String ch_title;
	private String ch_description;
	private int ch_pw_check;
	private String ch_pw;
}
