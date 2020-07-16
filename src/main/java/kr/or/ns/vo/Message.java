package kr.or.ns.vo;

import java.text.SimpleDateFormat;
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
	private String content;
	private Date opendate;
	private Date senddate;

//	public void setSenddate(String senddate) {
//		System.out.println("set-vo입니다: "+senddate);
//
//		
//		
//		//날짜파싱
//		String dateparsing = senddate;
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		String result = "";
//		
//		try {
//			Date date = format.parse(senddate);
//			SimpleDateFormat resultFormat = new SimpleDateFormat("yyyy/MM/dd");
//			result = resultFormat.format(date);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		this.senddate = result;
//		
//	}
	
	
}
