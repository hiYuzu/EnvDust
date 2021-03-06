package com.kyq.env.pojo;

import java.sql.Timestamp;
import java.util.List;

/**
 * 
 * <p>[功能描述]：覆盖区域信息pojo</p>
 *
 */
public class MapArea extends BasePojo {
	private int maId;
	private boolean maVisible;
	private String maName;
	private int optUser;
	private Timestamp optTime;
	private List<MapAreaPoint> pointList;
	public int getMaId() {
		return maId;
	}
	public void setMaId(int maId) {
		this.maId = maId;
	}
	public boolean isMaVisible() {
		return maVisible;
	}
	public void setMaVisible(boolean maVisible) {
		this.maVisible = maVisible;
	}
	public String getMaName() {
		return maName;
	}
	public void setMaName(String maName) {
		this.maName = maName;
	}
	public int getOptUser() {
		return optUser;
	}
	public void setOptUser(int optUser) {
		this.optUser = optUser;
	}
	public Timestamp getOptTime() {
		return optTime;
	}
	public void setOptTime(Timestamp optTime) {
		this.optTime = optTime;
	}
	public List<MapAreaPoint> getPointList() {
		return pointList;
	}
	public void setPointList(List<MapAreaPoint> pointList) {
		this.pointList = pointList;
	}
}
