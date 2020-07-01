package kr.or.ns.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class Users {
	
		private String user_id;
		private String user_pwd;
		private int enabled;
		private String user_name;
		private String user_email;
		private String profile_img;
		private String nickname;
		private String introduce;
		private int blame_count;
		

}
