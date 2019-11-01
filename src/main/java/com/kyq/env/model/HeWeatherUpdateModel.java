package com.kyq.env.model;

/**
 * [功能描述]：和天气更新时间信息
 */
public class HeWeatherUpdateModel {

	private String loc;//当地时间，24小时制，格式hh:mm
	private String utc;//UTC时间，24小时制，格式hh:mm
	
	/**
	 * @return the loc
	 */
	public String getLoc() {
		return loc;
	}
	/**
	 * @param loc the loc to set
	 */
	public void setLoc(String loc) {
		this.loc = loc;
	}
	/**
	 * @return the utc
	 */
	public String getUtc() {
		return utc;
	}
	/**
	 * @param utc the utc to set
	 */
	public void setUtc(String utc) {
		this.utc = utc;
	}
	
}
