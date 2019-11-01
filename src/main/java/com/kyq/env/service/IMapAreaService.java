package com.kyq.env.service;

import java.util.List;

import com.kyq.env.pojo.MapArea;
import com.kyq.env.pojo.MapAreaPoint;
import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.MapArea;
import com.tcb.env.pojo.MapAreaPoint;

/**
 * [功能描述]：百度地图覆盖区域确定 Service
 *
 * @author kyq
 */
public interface IMapAreaService {

    /**
     * 返回所有覆盖区域的信息
     */
    List<MapArea> getMapArea(MapArea target);

    /**
     * 获某覆盖区域所有的坐标点的个数
     */
    int getMapAreaPointCount(int maId);

    /**
     * 获某覆盖区域所有的坐标点的信息
     */
    List<MapAreaPoint> getMapAreaPoint(MapArea mapArea);

    /**
     * 获所有覆盖区域所有的坐标点的信息
     */
    List<MapArea> getAllMapAreaPoint();

    /**
     * 添加坐标区域
     */
    int addMapArea(@Param("mapArea") MapArea mapArea);

    /**
     * 修改坐标区域
     */
    boolean editMapArea(@Param("mapArea") MapArea mapArea);

    /**
     * 删除坐标区域
     */
    boolean deleteMapArea(@Param("maId") int maId);

    /**
     * 批量进行区域添加
     */
    boolean addBatchMapAreaPoint(@Param("mapAreaPointList") List<MapAreaPoint> mapAreaPointList);
}
