package com.kyq.env.pojo;


import com.kyq.env.util.DefaultArgument;

/**
 * <p>[功能描述]：</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年4月8日上午9:37:16
 * @since	EnvDust 1.0.0
 * 
 */
public class Sysflag extends BasePojo {
	private int sysId = DefaultArgument.INT_DEFAULT;//递增id
	private String sysFlagCode;//参数标识
	private String sysValue;//参数值
	/**
	 * @return the sysId
	 */
	public int getSysId() {
		return sysId;
	}
	/**
	 * @param sysId the sysId to set
	 */
	public void setSysId(int sysId) {
		this.sysId = sysId;
	}

	/**
	 * @return the sysFlagCode
	 */
	public String getSysFlagCode() {
		return sysFlagCode;
	}
	/**
	 * @param sysFlagCode the sysFlagCode to set
	 */
	public void setSysFlagCode(String sysFlagCode) {
		this.sysFlagCode = sysFlagCode;
	}
	/**
	 * @return the sysValue
	 */
	public String getSysValue() {
		return sysValue;
	}
	/**
	 * @param sysValue the sysValue to set
	 */
	public void setSysValue(String sysValue) {
		this.sysValue = sysValue;
	}

}
