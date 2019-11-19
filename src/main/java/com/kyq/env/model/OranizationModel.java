package com.kyq.env.model;


 import com.kyq.env.util.DefaultArgument;

public class OranizationModel extends BaseModel {
	private int orgId = DefaultArgument.INT_DEFAULT;
	private int orgIdParent = DefaultArgument.INT_DEFAULT;
	private String orgName;
	private String levelType;
	private String orgAddress;
	private String orgPath;
	private String orgTelephone;
	private String orgFax;
	private String orgLiaison;
	private int optUser = DefaultArgument.INT_DEFAULT;
	/**
	 * @return the orgId
	 */
	public int getOrgId() {
		return orgId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	/**
	 * @return the orgIdParent
	 */
	public int getOrgIdParent() {
		return orgIdParent;
	}
	/**
	 * @param orgIdParent the orgIdParent to set
	 */
	public void setOrgIdParent(int orgIdParent) {
		this.orgIdParent = orgIdParent;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the levelType
	 */
	public String getLevelType() {
		return levelType;
	}
	/**
	 * @param levelType the levelType to set
	 */
	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}
	/**
	 * @return the orgAddress
	 */
	public String getOrgAddress() {
		return orgAddress;
	}
	/**
	 * @param orgAddress the orgAddress to set
	 */
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	/**
	 * @return the orgPath
	 */
	public String getOrgPath() {
		return orgPath;
	}
	/**
	 * @param orgPath the orgPath to set
	 */
	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}
	
	/**
	 * @return the orgLiaison
	 */
	public String getOrgLiaison() {
		return orgLiaison;
	}
	/**
	 * @param orgLiaison the orgLiaison to set
	 */
	public void setOrgLiaison(String orgLiaison) {
		this.orgLiaison = orgLiaison;
	}
	/**
	 * @return the optUser
	 */
	public int getOptUser() {
		return optUser;
	}
	/**
	 * @param optUser the optUser to set
	 */
	public void setOptUser(int optUser) {
		this.optUser = optUser;
	}
	/**
	 * @return the orgTelephone
	 */
	public String getOrgTelephone() {
		return orgTelephone;
	}
	/**
	 * @param orgTelephone the orgTelephone to set
	 */
	public void setOrgTelephone(String orgTelephone) {
		this.orgTelephone = orgTelephone;
	}
	/**
	 * @return the orgFax
	 */
	public String getOrgFax() {
		return orgFax;
	}
	/**
	 * @param orgFax the orgFax to set
	 */
	public void setOrgFax(String orgFax) {
		this.orgFax = orgFax;
	}

	

}
