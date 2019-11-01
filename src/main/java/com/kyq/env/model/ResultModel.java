package com.kyq.env.model;

/**
 * 
 * <p>[功能描述]：返回结果类</p>
 */
public class ResultModel {

	/**
	 * 是否成功标志
	 */
	private boolean result = false;
	
	/**
	 * 详细信息（错误时不为空）
	 */
	private String detail = null;

	/**
	 * @return the result
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(boolean result) {
		this.result = result;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
}
