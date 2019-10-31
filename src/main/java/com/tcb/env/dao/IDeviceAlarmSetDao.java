package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.CommMain;
import com.tcb.env.pojo.DeviceAlarmSet;

/**
 * 
 * <p>
 * [功能描述]：设备报警门限信息Dao
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年5月31日下午2:17:36
 * @since EnvDust 1.0.0
 *
 */
public interface IDeviceAlarmSetDao {
	/**
	 * 获取单个报警门限信息
	 * @param deviceCode
	 * @param thingCodeList
	 * @return
	 */
	List<DeviceAlarmSet> getDeviceAlarmSetSingle(
			@Param("deviceCode") String deviceCode,
			@Param("thingCodeList") List<String> thingCodeList);
}
