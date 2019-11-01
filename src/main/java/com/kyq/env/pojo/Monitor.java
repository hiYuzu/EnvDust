package com.kyq.env.pojo;

import com.kyq.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：映射数据库监测物表</p>
 *
 */
public class Monitor extends BasePojo {

	private int thingId = DefaultArgument.INT_DEFAULT;
	private String thingCode;
	private String thingName;
	private String thingUnit;
	private Integer thingOrder;
	/**
	 * @return the thingUnit
	 */
	public String getThingUnit() {
		return thingUnit;
	}
	/**
	 * @param thingUnit the thingUnit to set
	 */
	public void setThingUnit(String thingUnit) {
		this.thingUnit = thingUnit;
	}
	/**
	 * @return the thingId
	 */
	public int getThingId() {
		return thingId;
	}
	/**
	 * @param thingId the thingId to set
	 */
	public void setThingId(int thingId) {
		this.thingId = thingId;
	}
	/**
	 * @return the thingCode
	 */
	public String getThingCode() {
		return thingCode;
	}
	/**
	 * @param thingCode the thingCode to set
	 */
	public void setThingCode(String thingCode) {
		this.thingCode = thingCode;
	}
	/**
	 * @return the thingName
	 */
	public String getThingName() {
		return thingName;
	}
	/**
	 * @param thingName the thingName to set
	 */
	public void setThingName(String thingName) {
		this.thingName = thingName;
	}
	/**
	 * @return the thingOrder
	 */
	public Integer getThingOrder() {
		return thingOrder;
	}
	/**
	 * @param thingOrder the thingOrder to set
	 */
	public void setThingOrder(Integer thingOrder) {
		this.thingOrder = thingOrder;
	}
	
	
}
