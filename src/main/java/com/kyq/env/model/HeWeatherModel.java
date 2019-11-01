package com.kyq.env.model;

/**
 * [功能描述]：天气信息明细类
 */
public class HeWeatherModel {

	private HeWeatherBasicModel basic;
	private HeWeatherUpdateModel update;
	private String status;
	private HeWeatherNowModel now;
	
	/**
	 * @return the basic
	 */
	public HeWeatherBasicModel getBasic() {
		return basic;
	}
	/**
	 * @param basic the basic to set
	 */
	public void setBasic(HeWeatherBasicModel basic) {
		this.basic = basic;
	}
	/**
	 * @return the update
	 */
	public HeWeatherUpdateModel getUpdate() {
		return update;
	}
	/**
	 * @param update the update to set
	 */
	public void setUpdate(HeWeatherUpdateModel update) {
		this.update = update;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the now
	 */
	public HeWeatherNowModel getNow() {
		return now;
	}
	/**
	 * @param now the now to set
	 */
	public void setNow(HeWeatherNowModel now) {
		this.now = now;
	}
	
}
