package com.tcb.env.dao;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.SysLog;

/**
 * 
 * <p>[功能描述]：系统日志数据库接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年5月28日下午3:42:48
 * @since	EnvDust 1.0.0
 *
 */
public interface ISysLogDao {
	
	/**
	 * 
	 * <p>[功能描述]：新增系统日志</p>
	 * 
	 * @author	王垒, 2018年5月28日下午3:45:18
	 * @since	EnvDust 1.0.0
	 *
	 * @param sysLog
	 * @return
	 */
	int insertSysLog(@Param("sysLog")SysLog sysLog);
	
}
