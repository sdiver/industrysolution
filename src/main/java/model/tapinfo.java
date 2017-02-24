/**
 *
 * @Title:tapinfo.java
 *
 * @Package:model
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年2月6日 下午4:52:46
 *
 * @version V1.0
 *
 */
package model;

public class tapinfo {
	private int thirdtypeid;
	private String thirdtype;
	private String TestColor;
	private String BackgroundColor;
	
	public tapinfo(){
	}
	public tapinfo(String thirdtype){
		this.thirdtype = thirdtype;
	}
	/**
	 * @return the thirdtypeid
	 */
	public int getThirdtypeid() {
		return thirdtypeid;
	}

	/**
	 * @param thirdtypeid the thirdtypeid to set
	 */
	public void setThirdtypeid(int thirdtypeid) {
		this.thirdtypeid = thirdtypeid;
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

	/**
	 * @return the testColor
	 */
	public String getTestColor() {
		return TestColor;
	}

	/**
	 * @param testColor the testColor to set
	 */
	public void setTestColor(String testColor) {
		TestColor = testColor;
	}

	/**
	 * @return the backgroundColor
	 */
	public String getBackgroundColor() {
		return BackgroundColor;
	}

	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(String backgroundColor) {
		BackgroundColor = backgroundColor;
	}
	
	
	
}
