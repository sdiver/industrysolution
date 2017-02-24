/**
 *
 * @Title:usertable.java
 *
 * @Package:model
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2016年3月14日 下午5:12:48
 *
 * @version V1.0
 *
 */
package model;

import java.sql.Date;

public class usertable {
	private String userid;
	private String username;
	private String userpassword;
	private String uname;
	private String token;
	private Date logintime;
	private String profile_photo_url;
	
	/**
	 * @return the profile_photo_url
	 */
	public String getProfile_photo_url() {
		return profile_photo_url;
	}

	/**
	 * @param profile_photo_url the profile_photo_url to set
	 */
	public void setProfile_photo_url(String profile_photo_url) {
		this.profile_photo_url = profile_photo_url;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the uname
	 */
	public String getUname() {
		return uname;
	}

	/**
	 * @param uname the uname to set
	 */
	public void setUname(String uname) {
		this.uname = uname;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the userpassword
	 */
	public String getUserpassword() {
		return userpassword;
	}

	/**
	 * @param userpassword the userpassword to set
	 */
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	/**
	 * @return the logintime
	 */
	public Date getLogintime() {
		return logintime;
	}

	/**
	 * @param logintime the logintime to set
	 */
	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
}
