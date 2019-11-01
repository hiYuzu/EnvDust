package com.kyq.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * <p>
 * [功能描述]：权限监测物数据库操作接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月6日上午8:57:58
 * @since EnvDust 1.0.0
 *
 */
public interface IAccessMonitorDao {

	/**
	 * [功能描述]：添加权限组监测物
	 */
	int insertAccessMonitor(@Param("ahrCode") String ahrCode,
			@Param("listMonCode") List<String> listMonCode,
			@Param("optUser") int optUser);

	/**
	 * [功能描述]：删除权限组监测物
	 */
	int deleteAccessMonitor(
			@Param("listAhrCode") List<String> listAhrCode);

}
