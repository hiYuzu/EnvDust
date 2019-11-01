package com.kyq.env.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>[功能描述]：查询数据返回结果</p>
 */
public class ResultListModel<T> extends ResultModel{

	/**
	 * 返回结果条数
	 */
	private int total = 0;
	
	private String select = null;
	
	/**
	 * 返回结果详细信息
	 */
	private List<T> rows = new ArrayList<T>();

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the select
	 */
	public String getSelect() {
		return select;
	}

	/**
	 * @param select the select to set
	 */
	public void setSelect(String select) {
		this.select = select;
	}

	/**
	 * @return the rows
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	
	
}
