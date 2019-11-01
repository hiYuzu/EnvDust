package com.kyq.env.dao;

import java.util.List;

import com.kyq.env.pojo.Manufacturer;
import org.apache.ibatis.annotations.Param;

/**
 * @author	kyq
 */
public interface IManufacturerDao {
	/**
	 * [功能描述]：是否模糊查询获得个数
	 */
	int getCount(@Param("manufacturer")Manufacturer manufacturer, @Param("isLike")boolean isLike);
	/**
	 * [功能描述]：获取厂商数据
	 */
	List<Manufacturer> getManufacturer(@Param("manufacturer")Manufacturer manufacturer);
	/**
	 * [功能描述]：新增数据
	 */
	int insertManufacturer(@Param("listmanufacturer")List<Manufacturer> listmanufacturer);
	/**
	 * [功能描述]：删除厂商数据
	 */
	int deleteManufacturer(@Param("manufacturerid")List<Integer> manufacturerid);
	/**
	 * [功能描述]：更新厂商数据
	 */
	int updateManufacturer(@Param("list")List<Manufacturer> list);
	/**
	 * [功能描述]：判断是否存在数据
	 */
	int getManufacturerExist(@Param("manufacturerid")int manufacturerid,@Param("manufacturercode")String manufacturercode);
}
