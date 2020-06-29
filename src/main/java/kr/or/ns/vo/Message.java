package kr.or.ns.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Message {
	private int m_seq;
	private String receptionid;
	private String senderid;
	private String rs_code;
	private String content;
	private Date opendate;
	private Date senddate;
}
