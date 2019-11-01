package com.kyq.env.service;

import java.util.List;

import com.kyq.env.pojo.Manufacturer;
import com.tcb.env.pojo.Manufacturer;


/**
 * @author	kyq
 */
public interface IManufacturerService {
	/**
	 * [功能描述]：是否模糊查询符合条件个数
	 */
	int getCount(Manufacturer manufacturer, boolean isLike);
	/**
	 * [功能描述]：得到厂商数据
	 */
	List<Manufacturer> getManufacturer(Manufacturer manufacturer);
	/**
	 * [功能描述]：新增厂商数据
	 */
	int insertManufacturer(List<Manufacturer> listmanufacturer) throws Exception;
	/**
	 * [功能描述]：删除数据
	 */
	int deleteManufacturer(List<Integer>  manufacturerid) throws Exception;
	/**
	 * [功能描述]：更新厂商信息
	 */
	int updateManufacturer(List<Manufacturer> listmanufacturer) throws Exception;
	/**
	 * [功能描述]：更新用户数据时候判断用户名是否存在
	 */
	int getManufacturerExist(int manufacturerid,String manufacturercode);
}
