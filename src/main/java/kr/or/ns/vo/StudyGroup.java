package kr.or.ns.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudyGroup {

	private int stu_seq;
	private String user_id;
	private String accept_status;
	private String role_name;
}
