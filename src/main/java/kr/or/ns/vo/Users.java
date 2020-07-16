package kr.or.ns.vo;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
	private String snstype;
	private String java;
	private String python;
	private String html_css;
	private String javascript;
	private String sql;
	private CommonsMultipartFile file;
	
	
}