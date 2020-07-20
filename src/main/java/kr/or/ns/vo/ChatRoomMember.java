package kr.or.ns.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatRoomMember {
	private int cm_seq;
	private String user_id;
	private int ch_seq;
}
