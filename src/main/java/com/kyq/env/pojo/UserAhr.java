package com.kyq.env.pojo;

/**
 * <p>[功能描述]：用户与权限组的对应关系pojo</p>
 */
public class UserAhr extends BasePojo {

	private int authorityUserId;
	private Authority authority;
	private User user;
	
	/**
	 * @return the authorityUserId
	 */
	public int getAuthorityUserId() {
		return authorityUserId;
	}
	/**
	 * @param authorityUserId the authorityUserId to set
	 */
	public void setAuthorityUserId(int authorityUserId) {
		this.authorityUserId = authorityUserId;
	}
	/**
	 * @return the authority
	 */
	public Authority getAuthority() {
		return authority;
	}
	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
