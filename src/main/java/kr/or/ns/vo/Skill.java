package kr.or.ns.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Skill {
	private int skill_seq;
	private String user_id;
	private String skill_name;
	private String skill_level;
}
