/**
 *
 * @Title:projectinfo.java
 *
 * @Package:model
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年1月12日 上午10:51:10
 *
 * @version V1.0
 *
 */
package model;

import java.util.Date;
import java.util.List;

public class projectinfo {
	private String rownum;
	private String projectid;
	private String projectname;
	private String projectinfo;
	private String projectuserid;
	private String projectuname;
	private int firsttype;
	private int secondtype;
	private String thirdtype;
	private Date projecttime;
	private String countperson;
	private String countxq;
	private String countsc;
	private String countal;
	private List<tapinfo> thirdrealtype;
	
	/**
	 * @return the projecttime
	 */
	public Date getProjecttime() {
		return projecttime;
	}

	/**
	 * @param projecttime the projecttime to set
	 */
	public void setProjecttime(Date projecttime) {
		this.projecttime = projecttime;
	}

	/**
	 * @return the thirdrealtype
	 */
	public List<tapinfo> getThirdrealtype() {
		return thirdrealtype;
	}

	/**
	 * @param thirdrealtype the thirdrealtype to set
	 */
	public void setThirdrealtype(List<tapinfo> thirdrealtype) {
		this.thirdrealtype = thirdrealtype;
	}

	/**
	 * @return the rownum
	 */
	public String getRownum() {
		return rownum;
	}

	/**
	 * @param rownum the rownum to set
	 */
	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	/**
	 * @return the projectid
	 */
	public String getProjectid() {
		return projectid;
	}

	/**
	 * @param projectid the projectid to set
	 */
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	/**
	 * @return the projectname
	 */
	public String getProjectname() {
		return projectname;
	}

	/**
	 * @param projectname the projectname to set
	 */
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	/**
	 * @return the projectinfo
	 */
	public String getProjectinfo() {
		return projectinfo;
	}

	/**
	 * @param projectinfo the projectinfo to set
	 */
	public void setProjectinfo(String projectinfo) {
		this.projectinfo = projectinfo;
	}

	/**
	 * @return the projectuserid
	 */
	public String getProjectuserid() {
		return projectuserid;
	}

	/**
	 * @param projectuserid the projectuserid to set
	 */
	public void setProjectuserid(String projectuserid) {
		this.projectuserid = projectuserid;
	}

	/**
	 * @return the projectuname
	 */
	public String getProjectuname() {
		return projectuname;
	}

	/**
	 * @param projectuname the projectuname to set
	 */
	public void setProjectuname(String projectuname) {
		this.projectuname = projectuname;
	}

	/**
	 * @return the countperson
	 */
	public String getCountperson() {
		return countperson;
	}

	/**
	 * @param countperson the countperson to set
	 */
	public void setCountperson(String countperson) {
		this.countperson = countperson;
	}

	/**
	 * @return the countxq
	 */
	public String getCountxq() {
		return countxq;
	}

	/**
	 * @param countxq the countxq to set
	 */
	public void setCountxq(String countxq) {
		this.countxq = countxq;
	}

	/**
	 * @return the countsc
	 */
	public String getCountsc() {
		return countsc;
	}

	/**
	 * @param countsc the countsc to set
	 */
	public void setCountsc(String countsc) {
		this.countsc = countsc;
	}

	/**
	 * @return the countal
	 */
	public String getCountal() {
		return countal;
	}

	/**
	 * @param countal the countal to set
	 */
	public void setCountal(String countal) {
		this.countal = countal;
	}

	/**
	 * @return the firsttype
	 */
	public int getFirsttype() {
		return firsttype;
	}

	/**
	 * @param firsttype the firsttype to set
	 */
	public void setFirsttype(int firsttype) {
		this.firsttype = firsttype;
	}

	/**
	 * @return the secondtype
	 */
	public int getSecondtype() {
		return secondtype;
	}

	/**
	 * @param secondtype the secondtype to set
	 */
	public void setSecondtype(int secondtype) {
		this.secondtype = secondtype;
	}

	/**
	 * @return the thirdtype
	 */
	public String getThirdtype() {
		return thirdtype;
	}

	/**
	 * @param thirdtype the thirdtype to set
	 */
	public void setThirdtype(String thirdtype) {
		this.thirdtype = thirdtype;
	}
	
	
	
}
