package kr.or.ns.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class Users {

	private String USER_ID;
	private String USER_PWD;
	private int ENABLED;
	private String USER_NAME;
	private String USER_EMAIL;
	private String PROFILE_IMG;
	private String NICKNAME;
	private String INTRODUCE;
	private int BLAME_COUNT;
	
}