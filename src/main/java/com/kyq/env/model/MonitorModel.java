package com.kyq.env.model;


import com.kyq.env.util.DefaultArgument;

public class MonitorModel extends BaseModel{
	
	private int thingId = DefaultArgument.INT_DEFAULT;//递增ID
	private String thingCode;//监测物编号
	private String thingName;//监测物名称
	private String thingUnit;
	private String thingOrder;
	
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
	public String getThingOrder() {
		return thingOrder;
	}
	/**
	 * @param thingOrder the thingOrder to set
	 */
	public void setThingOrder(String thingOrder) {
		this.thingOrder = thingOrder;
	}
	
}
