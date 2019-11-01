package com.kyq.env.service;

import java.util.List;
import java.util.Map;

import com.kyq.env.pojo.Area;
import com.kyq.env.pojo.HeWeather;

/**
 * [功能描述]：Area操作服务类接口
 *
 * @author kyq
 */
public interface IAreaService {

    /**
     * [功能描述]：查询结果个数
     */
    int getCount(Area area, boolean ignorecountry);

    /**
     * [功能描述]：查询Area数据，包括所有国家
     */
    List<Area> getAreas(Area area, boolean ignorecountry);

    /**
     * [功能描述]：新增Area数据
     */
    int insertAreas(Area area) throws Exception;

    /**
     * [功能描述]：更新Area数据
     */
    int updateAreas(List<Area> listarea) throws Exception;

    /**
     * [功能描述]：物理删除User数据
     */
    int deleteAreas(List<Integer> listid) throws Exception;

    /**
     * [功能描述]：获取区域路径名称
     */
    String getAreaPath(int areaid, String areaName);

    /**
     * [功能描述]：查询是否存在同名区域 
     */
    int getAreaExist(int areaid, String areaname);

    /**
     * [功能描述]：插入区域天气信息
     */
    int insertAreaWeather(HeWeather weather);

    /**
     * [功能描述]：查询区域天气信息个数
     */
    int getAreaWeatherCount(HeWeather weather);

    /**
     * [功能描述]：查询地方/城市ID
     */
    List<String> getAreaCityId();

    /**
     * 获取第四级区域信息
     */
    List<Map<String, String>> queryFourthAreaInfo();
}
