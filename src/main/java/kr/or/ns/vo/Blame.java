package kr.or.ns.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Blame {
	private int bl_seq;
	private int blame_seq;
	private String type;
	private String bl_id;
	private String bl_target_id;
	private String bl_title;
	private String bl_content;
	private String bl_status;
	private Date bl_write_date;

}
