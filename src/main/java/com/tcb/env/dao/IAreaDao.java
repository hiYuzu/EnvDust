package com.tcb.env.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.Area;
import com.tcb.env.pojo.HeWeather;

/**
 * [功能描述]：区域数据库操作接口
 */
public interface IAreaDao {

	/**
	 * [功能描述]：查询结果个数（国家）
	 */
	int getCountryCount(@Param("area")Area area);
	
	/**
	 * [功能描述]：查询结果个数（不包括国家）
	 */
	int getCount(@Param("area")Area area);
	
	/**
	 * [功能描述]：查询Area数据，包括所有国家
	 */
	List<Area> getAreas(@Param("area")Area area,@Param("ignorecountry")boolean ignorecountry);
	
	/**
	 * [功能描述]：新增Area数据
	 */
	int insertAreas(@Param("area")Area area);
	
	/**
	 * [功能描述]：更新Area数据
	 */
	int updateAreas(@Param("listarea")List<Area> listarea);
	
	/**
	 * [功能描述]：物理删除User数据
	 */
	int deleteAreas(@Param("listid")List<Integer> listid);
	
	
	/**
	 * [功能描述]：获取区域路径名称
	 */
	String getAreaPath(@Param("areaid")int areaid);
	
	/**
	 * [功能描述]：查询是否存在同名区域
	 */
	int getAreaExist(@Param("areaid")int areaid,@Param("areaname")String areaname);
	
	/**
	 * [功能描述]：插入区域天气信息
	 */
	int insertAreaWeather(@Param("weather")HeWeather weather);
	
	/**
	 * [功能描述]：查询区域天气信息个数
	 */
	int getAreaWeatherCount(@Param("weather")HeWeather weather);
	
	/**
	 * [功能描述]：查询地方/城市ID
	 */
	List<String> getAreaCityId();
	
	/**
	 * [功能描述]：存在关联区域或设备个数
	 */
	int getRelationCount(@Param("areaId") int areaId);

	/**
	 * 获取第四级区域信息
	 */
	List<Map<String, String>> queryFourthAreaInfo();
}
