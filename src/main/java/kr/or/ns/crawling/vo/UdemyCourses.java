package kr.or.ns.crawling.vo;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UdemyCourses {
	private HashMap<String, UdemyKey> map;

	/*
	 * public void setMap(String string, UdemyKey up) { System.out.println("우철 : " +
	 * string); System.out.println("정아 : " + up); map2.put(string, up); map = }
	 */

}
