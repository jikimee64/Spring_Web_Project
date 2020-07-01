package kr.or.ns.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
	
	
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getUSER_PWD() {
		return USER_PWD;
	}
	public void setUSER_PWD(String uSER_PWD) {
		USER_PWD = uSER_PWD;
	}
	public int getENABLED() {
		return ENABLED;
	}
	public void setENABLED(int eNABLED) {
		ENABLED = eNABLED;
	}
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	public String getUSER_EMAIL() {
		return USER_EMAIL;
	}
	public void setUSER_EMAIL(String uSER_EMAIL) {
		USER_EMAIL = uSER_EMAIL;
	}
	public String getPROFILE_IMG() {
		return PROFILE_IMG;
	}
	public void setPROFILE_IMG(String pROFILE_IMG) {
		PROFILE_IMG = pROFILE_IMG;
	}
	public String getNICKNAME() {
		return NICKNAME;
	}
	public void setNICKNAME(String nICKNAME) {
		NICKNAME = nICKNAME;
	}
	public String getINTRODUCE() {
		return INTRODUCE;
	}
	public void setINTRODUCE(String iNTRODUCE) {
		INTRODUCE = iNTRODUCE;
	}
	public int getBLAME_COUNT() {
		return BLAME_COUNT;
	}
	public void setBLAME_COUNT(int bLAME_COUNT) {
		BLAME_COUNT = bLAME_COUNT;
	}
	@Override
	public String toString() {
		return "Users [USER_ID=" + USER_ID + ", USER_PWD=" + USER_PWD + ", ENABLED=" + ENABLED + ", USER_NAME="
				+ USER_NAME + ", USER_EMAIL=" + USER_EMAIL + ", PROFILE_IMG=" + PROFILE_IMG + ", NICKNAME=" + NICKNAME
				+ ", INTRODUCE=" + INTRODUCE + ", BLAME_COUNT=" + BLAME_COUNT + "]";
	}
	
	
}
