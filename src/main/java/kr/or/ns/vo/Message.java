package kr.or.ns.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Getter
//@Setter
@ToString
public class Message {
	private int m_seq;
	private String receptionid;
	private String senderid;
	private String content;
	private Date opendate;
//	private String opendate;
	private Date senddate;
//	private String senddate;
	
	
	
	
	
	
	public int getM_seq() {
		return m_seq;
	}
	public void setM_seq(int m_seq) {
		this.m_seq = m_seq;
	}
	public String getReceptionid() {
		return receptionid;
	}
	public void setReceptionid(String receptionid) {
		this.receptionid = receptionid;
	}
	public String getSenderid() {
		return senderid;
	}
	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getOpendate() {
		return opendate;
	}
	public void setOpendate(Date opendate) {
		this.opendate = opendate;
	}
	
	
	
	
	
	
	public Date getSenddate() {
		System.out.println("get-vo입니다: "+senddate);
		return senddate;
	}
	public void setSenddate(Date senddate) {
		System.out.println("set-vo입니다: "+senddate);
		this.senddate = senddate;
		
	}
	
	
	
	
	
	
	
	
	
}
