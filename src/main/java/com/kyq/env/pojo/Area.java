package com.kyq.env.pojo;


import com.kyq.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：区域pojo</p>
 *
 */
public class Area extends BasePojo {

	private int areaId = DefaultArgument.INT_DEFAULT;
	private String areaName;
	private int parentId = DefaultArgument.INT_DEFAULT;
	private AreaLevel areaLevel;
	private String areaPath;
	private String cityId;

	/**
	 * @return the areaId
	 */
	public int getAreaId() {
		return areaId;
	}
	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * @return the areaLevel
	 */
	public AreaLevel getAreaLevel() {
		return areaLevel;
	}
	/**
	 * @param areaLevel the areaLevel to set
	 */
	public void setAreaLevel(AreaLevel areaLevel) {
		this.areaLevel = areaLevel;
	}
	
	/**
	 * @return the areaPath
	 */
	public String getAreaPath() {
		return areaPath;
	}
	/**
	 * @param areaPath the areaPath to set
	 */
	public void setAreaPath(String areaPath) {
		this.areaPath = areaPath;
	}
	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	
}
