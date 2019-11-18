package com.kyq.env.dao;

import java.util.List;

import com.kyq.env.pojo.Monitor;
import org.apache.ibatis.annotations.Param;

/**
 * [功能描述]：监测物数据库接口
 *
 * @author kyq
 */
public interface IMonitorDao {

    /**
     * [功能描述]：查询检测物
     */
    List<Monitor> getMonitor(@Param("monitor") Monitor monitor);

    /**
     * [功能描述]：得到数量
     */
    int getCount(@Param("monitor") Monitor monitor);

    /**
     * [功能描述]：增加数据
     */
    int insertMonitor(@Param("monitor") Monitor monitor);

    /**
     * [功能描述]：删除数据
     */
    int deleteMonitor(@Param("monitorid") List<Integer> monitorid);

    /**
     * [功能描述]：判断是否存在
     */
    int getMonitorExist(@Param("monitorid") int monitorid,
                        @Param("monitorcode") String monitorcode,
                        @Param("monitorname") String monitorname);

    /**
     * [功能描述]：更新数据
     */
    int updateMonitor(@Param("list") List<Monitor> list);

    /**
     * [功能描述]：获取监测物名称（通过编号）
     */
    String getMonitorName(@Param("thingCode") String thingCode);
}
