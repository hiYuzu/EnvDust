package com.kyq.env.pojo;

import com.kyq.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：权限组pojo</p>
 *
 */
public class Authority extends BasePojo {

	private int authorityId = DefaultArgument.INT_DEFAULT;
	private String authorityCode;
	private String authorityName;
	private String remark;
	/**
	 * @return the authorityId
	 */
	public int getAuthorityId() {
		return authorityId;
	}
	/**
	 * @param authorityId the authorityId to set
	 */
	public void setAuthorityId(int authorityId) {
		this.authorityId = authorityId;
	}
	/**
	 * @return the authorityCode
	 */
	public String getAuthorityCode() {
		return authorityCode;
	}
	/**
	 * @param authorityCode the authorityCode to set
	 */
	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}
	/**
	 * @return the authorityName
	 */
	public String getAuthorityName() {
		return authorityName;
	}
	/**
	 * @param authorityName the authorityName to set
	 */
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
