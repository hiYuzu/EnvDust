package com.tcb.env.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.tcb.env.model.MonitorStorageModel;
import com.tcb.env.model.OriginalDataModel;
import com.tcb.env.pojo.NetStatusCount;

/**
 * 
 * <p>
 * [功能描述]：监测物查询服务类接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年3月29日下午3:56:12
 * @since EnvDust 1.0.0
 *
 */
public interface IMonitorStorageService {

	/**
	 * 
	 * <p>[功能描述]：获取分钟原始统计数据</p>
	 * 
	 * @author	王垒, 2018年1月15日下午2:31:17
	 * @since	EnvDust 1.0.0
	 *
	 * @param listdevicecode
	 * @param listthingcode
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	Map<Integer, List<MonitorStorageModel>> getPerMinuteMonitorData(
			List<String> listdevicecode, List<String> listthingcode,
			Timestamp starttime, Timestamp endtime);
	
	/**
	 * 
	 * <p>
	 * [功能描述]：获取小时原始小时数据
	 * </p>
	 * 
	 * @author 王垒, 2016年3月29日下午2:50:06
	 * @since EnvDust 1.0.0
	 *
	 * @param devicecode
	 * @param thingcode
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	Map<Integer, List<MonitorStorageModel>> getPerHourMonitorData(
			List<String> listdevicecode, List<String> listthingcode,
			Timestamp starttime, Timestamp endtime);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取天原始每日数据
	 * </p>
	 * 
	 * @author 王垒, 2016年3月29日下午2:50:06
	 * @since EnvDust 1.0.0
	 *
	 * @param devicecode
	 * @param thingcode
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	Map<Integer, List<MonitorStorageModel>> getPerDayMonitorData(
			List<String> listdevicecode, List<String> listthingcode,
			Timestamp starttime, Timestamp endtime);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取月统计数据(每日)
	 * </p>
	 * 
	 * @author 王垒, 2016年3月29日下午2:50:06
	 * @since EnvDust 1.0.0
	 *
	 * @param devicecode
	 * @param thingcode
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	Map<Integer, List<MonitorStorageModel>> getPerMonthMonitorData(
			List<String> listdevicecode, List<String> listthingcode,
			Timestamp starttime, Timestamp endtime);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取季度统计数据(每日)
	 * </p>
	 * 
	 * @author 王垒, 2016年3月29日下午2:50:06
	 * @since EnvDust 1.0.0
	 *
	 * @param devicecode
	 * @param thingcode
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	Map<Integer, List<MonitorStorageModel>> getPerQuarterMonitorData(
			List<String> listdevicecode, List<String> listthingcode,
			Timestamp starttime, Timestamp endtime);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取分钟统计数据(实时统计)
	 * </p>
	 * 
	 * @author 王垒, 2016年3月29日下午2:50:06
	 * @since EnvDust 1.0.0
	 *
	 * @param devicecode
	 * @param thingcode
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	Map<Integer, List<MonitorStorageModel>> getPerMinuteStaMonitorData(
			List<String> listdevicecode, List<String> listthingcode,
			Timestamp starttime, Timestamp endtime);
	
	/**
	 * 
	 * <p>
	 * [功能描述]：获取小时统计数据(实时统计)
	 * </p>
	 * 
	 * @author 王垒, 2016年3月29日下午2:50:06
	 * @since EnvDust 1.0.0
	 *
	 * @param devicecode
	 * @param thingcode
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	Map<Integer, List<MonitorStorageModel>> getPerHourStaMonitorData(
			List<String> listdevicecode, List<String> listthingcode,
			Timestamp starttime, Timestamp endtime);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取每日统计数据(实时统计)
	 * </p>
	 * 
	 * @author 王垒, 2016年3月29日下午2:50:06
	 * @since EnvDust 1.0.0
	 *
	 * @param devicecode
	 * @param thingcode
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	Map<Integer, List<MonitorStorageModel>> getPerDayStaMonitorData(
			List<String> listdevicecode, List<String> listthingcode,
			Timestamp starttime, Timestamp endtime);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取月统计数据(实时统计)
	 * </p>
	 * 
	 * @author 王垒, 2016年3月29日下午2:50:06
	 * @since EnvDust 1.0.0
	 *
	 * @param devicecode
	 * @param thingcode
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	Map<Integer, List<MonitorStorageModel>> getPerMonthStaMonitorData(
			List<String> listdevicecode, List<String> listthingcode,
			Timestamp starttime, Timestamp endtime);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取季度统计数据(实时统计)
	 * </p>
	 * 
	 * @author 王垒, 2016年3月29日下午2:50:06
	 * @since EnvDust 1.0.0
	 *
	 * @param devicecode
	 * @param thingcode
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	Map<Integer, List<MonitorStorageModel>> getPerQuarterStaMonitorData(
			List<String> listdevicecode, List<String> listthingcode,
			Timestamp starttime, Timestamp endtime);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取实时数据个数
	 * </p>
	 * 
	 * @author 王垒, 2016年3月29日下午2:50:06
	 * @since EnvDust 1.0.0
	 *
	 * @param listdevicecode
	 *            :设备列表
	 * @param listthingcode
	 *            :监测物列表
	 * @param secondcount
	 *            :查询时间段（秒）
	 * @param nowtime
	 *            :当前系统时间
	 * @param selecttime
	 *            :上次查询时间
	 * @return
	 */
	int getTimelyMonitorDataCount(List<String> listdevicecode,
			List<String> listthingcode, Timestamp nowtime, Timestamp selecttime);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取实时数据
	 * </p>
	 * 
	 * @author 王垒, 2016年3月29日下午2:50:06
	 * @since EnvDust 1.0.0
	 *
	 * @param listdevicecode
	 *            :设备列表
	 * @param listthingcode
	 *            :监测物列表
	 * @param secondcount
	 *            :查询时间段（秒）
	 * @param nowtime
	 *            :当前系统时间
	 * @param selecttime
	 *            :上次查询时间
	 * @return
	 */
	List<MonitorStorageModel> getTimelyMonitorData(
			List<String> listdevicecode, List<String> listthingcode,
			Timestamp nowtime, Timestamp selecttime);

	/**
	 * 
	 * <p>
	 * [功能描述]：是否存在新上传的小时数据
	 * </p>
	 * 
	 * @author 王垒, 2016年3月29日下午2:50:06
	 * @since EnvDust 1.0.0
	 *
	 * @param devicecode
	 *            :设备编号
	 * @param listthingcode
	 *            :监测物列表
	 * @param nowtime
	 *            :系统当前时间
	 * @param selecttime
	 *            :上次查询时间
	 * @return
	 */
	int getHourMonitorDataCount(List<String> listdevicecode,
			List<String> listthingcode, Timestamp nowtime, Timestamp selecttime);

	/**
	 * 
	 * <p>
	 * [功能描述]：通过监测物code获取name
	 * </p>
	 * 
	 * @author 王垒, 2016年4月6日下午4:11:52
	 * @since EnvDust 1.0.0
	 *
	 * @param listMonCode
	 * @return
	 */
	List<String> getMonNamebyCode(List<String> listMonCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取原始数据个数
	 * </p>
	 * 
	 * @author 王垒, 2016年3月29日下午2:50:06
	 * @since EnvDust 1.0.0
	 *
	 * @param listdevicecode
	 *            :设备编号列表
	 * @param listthingcode
	 *            :监测物列表
	 * @param begintime
	 *            :开始时间
	 * @param endtime
	 *            :结束时间
	 * @param updateType
	 *            :数据类型
	 * @param select
	 *            :选择标识
	 * @return
	 */
	int getOriginalDataCount(List<String> listdevicecode,
			List<String> listthingcode, Timestamp begintime, Timestamp endtime,
			String updateType, String select);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取原始数据
	 * </p>
	 * 
	 * @author 王垒, 2016年3月29日下午2:50:06
	 * @since EnvDust 1.0.0
	 *
	 * @param listdevicecode
	 *            :设备编号列表
	 * @param listthingcode
	 *            :监测物列表
	 * @param begintime
	 *            :开始时间
	 * @param endtime
	 *            :结束时间
	 * @param updateType
	 *            :数据类型
	 * @param select
	 *            :选择标识
	 * @param rowIndex
	 *            :起始行数
	 * @param rowCount
	 *            :查询行数
	 * @return
	 */
	List<OriginalDataModel> getOriginalData(List<String> listdevicecode,
			List<String> listthingcode, Timestamp begintime, Timestamp endtime,
			String updateType, String select, int rowIndex, int rowCount);
	
	/**
	 * 
	 * <p>[功能描述]：获取网络状态个数</p>
	 * 
	 * @author	王垒, 2016年7月1日下午3:43:20
	 * @since	EnvDust 1.0.0
	 *
	 * @param userCode
	 * @param listdevicecode
	 * @return
	 */
	List<NetStatusCount> getNetStatusCount(String userCode,List<String> listdevicecode);
	
	/**
	 * 
	 * <p>[功能描述]：获取网络监控最新数据更新时间</p>
	 * 
	 * @author	王垒, 2017年8月9日上午10:41:41
	 * @since	EnvDust 1.0.0
	 *
	 * @param listdevicecode
	 * @param listthingcode
	 * @param statusCode
	 * @return
	 */
	List<MonitorStorageModel> getNetMonitorRecentTime(
			List<String> listdevicecode,List<String> listthingcode,String statusCode);
	
	
	/**
	 * 
	 * <p>[功能描述]：更新原始上传数据(数据修约)</p>
	 * 
	 * @author	王垒, 2018年5月28日下午4:08:58
	 * @since	EnvDust 1.0.0
	 *
	 * @param originalDataModel
	 * @param optUserId
	 * @return
	 * @throws Exception
	 */
	int updateOriginalDeviceData(OriginalDataModel originalDataModel,int optUserId) throws Exception;
	
	/**
	 * 
	 * <p>[功能描述]：删除原始上传数据(数据修约)</p>
	 * 
	 * @author	王垒, 2018年6月5日上午11:10:20
	 * @since	EnvDust 1.0.0
	 *
	 * @param originalDataModel
	 * @param optUserId
	 * @return
	 * @throws Exception
	 */
	int deleteOriginalDeviceData(OriginalDataModel originalDataModel,int optUserId) throws Exception;
	
	/**
	 * 
	 * <p>[功能描述]：通过状态筛选设备编码</p>
	 * 
	 * @author	王垒, 2018年12月10日下午2:21:01
	 * @since	EnvDust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param deviceStatus
	 * @return
	 */
	List<String> getDeviceCodeByStatus(List<String> deviceCodeList,String deviceStatus);

}
