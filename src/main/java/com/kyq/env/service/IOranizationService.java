package com.kyq.env.service;

import java.util.List;

import com.kyq.env.pojo.Oranization;
import com.tcb.env.pojo.Oranization;



/**
 * [功能描述]：Orgnization接口
 */
public interface IOranizationService {
	/**
	 * [功能描述]：获得符合条件数据个数
	 */
	int getCount(Oranization oranization, boolean isLike);
	/**
	 * [功能描述]：获取组织数据,获取到的所有符合条件的值都放在list中
	 */
	List<Oranization> getOranization(Oranization oranization);
	/**
	 * [功能描述]：增加组织数据
	 */
	int insertOranization(Oranization oranization) throws Exception;
	/**
	 * [功能描述]：删除组织数据
	 */
	int deleteOranization(List<Integer>  oranizationid) throws Exception;
	/**
	 * [功能描述]：更新组织数据
	 */
	int updateOranization(List<Oranization> listoranization) throws Exception;
	/**
	 * [功能描述]：获取是否存在相同的组织名称
	 */
	int getOranizationExist(int oranizationid,String oranizationname);
	
	/**
	 * [功能描述]：通过组织Id得到相应组织名称
	 */
	String getOranizationPathById(int orgid);
}
