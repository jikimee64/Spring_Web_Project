package kr.or.ns.vo;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Study {
	private int s_seq;
	private String user_id;
	private int c_seq;
	private int loc_seq;
	private int lan_seq;
	private int people;
	private Date deadline; 
	private String title;
	private String content;
	private Date write_date;
	private String level;
	private String image;
	private String filesrc;
	private String filesrc2;
	private String status;
	private int readnum;
	private String selectlan;
	private String selectpeo;
	private String selectloc;
	private String selectlev;
	private String selectend;
	private List<CommonsMultipartFile> files;
	
}
