package com.kyq.env.dao;

import java.util.List;

import com.kyq.env.pojo.MapArea;
import com.kyq.env.pojo.MapAreaPoint;
import org.apache.ibatis.annotations.Param;

/**
 * [功能描述]：百度地图覆盖区域确定 Dao
 *
 * @author kyq
 */
public interface IMapAreaDao {
    /**
     * 获取所有的覆盖区域信息
     */
    List<MapArea> getMapArea(@Param("addMapArea") MapArea addMapArea);

    /**
     * 获取所有的覆盖区域信息
     */
    List<MapArea> getAllMapAreaPoint();

    /**
     * 得到某覆盖区域的坐标的数量
     */
    int getMapAreaPointCount(@Param("maId") int maId);

    /**
     * 得到某覆盖区域的所有坐标
     */
    List<MapAreaPoint> getMapAreaPoint(@Param("mapArea") MapArea mapArea);

    /**
     * 添加坐标区域
     */
    int addMapArea(@Param("addMapArea") MapArea addMapArea);

    /**
     * 修改坐标区域
     */
    int editMapArea(@Param("mapArea") MapArea mapArea);

    /**
     * 删除坐标区域
     */
    int deleteArea(@Param("maId") int maId);

    /**
     * 批量进行区域添加
     */
    int addBatchMapAreaPoint(@Param("mapAreaPointList") List<MapAreaPoint> mapAreaPointList);
}
