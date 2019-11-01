package com.kyq.env.service;

import java.util.List;

import com.kyq.env.pojo.Device;
import com.tcb.env.pojo.Device;

/**
 * [功能描述]：权限组访问数据操作接口
 *
 * @author kyq
 */
public interface IAccessOperatorService {
	/**
	 * [功能描述]：查询符合条件设备个数
	 */
	int getJudgeAhrDeviceCount(Device device, String ahrCode);

	/**
	 * [功能描述]：查询符合条件设备
	 */
	List<Device> getJudgeAhrDevice(Device device, String ahrCode);

	/**
	 * [功能描述]：添加权限组设备
	 */
	int insertAccessDevice(String ahrCode, List<String> listDevCode, int optUser) throws Exception;

	/**
	 * [功能描述]：删除权限组设备
	 */
	int deleteAccessDevice(List<String> listAhrCode) throws Exception;

	/**
	 * [功能描述]：删除权限组设备
	 */
	int deleteAccessDeviceSingle(String ahrCode, List<String> listDevCode);

	/**
	 * [功能描述]：更新权限组监测物
	 */
	void updateAccessMonitor(String ahrCode, List<String> listMonCode, int optUser) throws Exception;

}
