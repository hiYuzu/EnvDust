package com.kyq.env.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kyq.env.config.Dom4jConfig;
import com.kyq.env.dao.IMonitorStorageDao;
import com.kyq.env.model.MonitorStorageModel;
import com.kyq.env.model.OriginalDataModel;
import com.kyq.env.pojo.NetStatusCount;
import com.kyq.env.service.IMonitorStorageService;
import com.kyq.env.util.DateUtil;
import com.kyq.env.util.DefaultArgument;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * [功能描述]：监测物查询服务类接口实现类
 */
@Service("monitorStorageService")
@Transactional(rollbackFor = Exception.class)
public class MonitorStorageServiceImpl implements IMonitorStorageService {

    /**
     * 日志输出标记
     */
    private static final String LOG = "MonitorStorageServiceImpl";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(MonitorStorageServiceImpl.class);

    @Resource
    private IMonitorStorageDao monitorStorageDao;

    /**
     * 配置文件服务类
     */
    @Resource
    private Dom4jConfig dom4jConfig;

    @Override
    public Map<Integer, List<MonitorStorageModel>> getPerMinuteMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime) {
        try {
            String dboldname = "";
            if (!DateUtil.isRecentlyData(starttime, DefaultArgument.RECENT_DAYS)) {
                dboldname = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            int allcount = 0;
            List<MonitorStorageModel> list = new ArrayList<MonitorStorageModel>();
            for (String tempdev : listdevicecode) {
                for (String tempthg : listthingcode) {
                    Map<String, Object> hashmap = new HashMap<String, Object>();
                    hashmap.put("dboldname", dboldname);
                    hashmap.put("devcode", tempdev);
                    hashmap.put("thingcode", tempthg);
                    hashmap.put("starttime", starttime);
                    hashmap.put("endtime", endtime);
                    // 页数默认值-1，不做分页处理
                    hashmap.put("rowindex", DefaultArgument.INT_DEFAULT);
                    hashmap.put("rowcount", DefaultArgument.INT_DEFAULT);
                    List<MonitorStorageModel> templist = monitorStorageDao.getPerMinuteMonitorData(hashmap);
                    list.addAll(templist);
                    allcount += (int) hashmap.get("allcount");
                }
            }
            Map<Integer, List<MonitorStorageModel>> result = new HashMap<Integer, List<MonitorStorageModel>>();
            result.put(allcount, list);
            return result;
        } catch (Exception e) {
            logger.error(LOG + ":获取小时历史数据失败，错误信息为：" + e.getMessage());
            return null;
        }
    }

    @Override
    public Map<Integer, List<MonitorStorageModel>> getPerHourMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime) {
        try {
            String dboldname = "";
            if (!DateUtil.isRecentlyData(starttime, DefaultArgument.RECENT_DAYS)) {
                dboldname = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            int allcount = 0;
            List<MonitorStorageModel> list = new ArrayList<MonitorStorageModel>();
            for (String tempdev : listdevicecode) {
                for (String tempthg : listthingcode) {
                    Map<String, Object> hashmap = new HashMap<String, Object>();
                    hashmap.put("dboldname", dboldname);
                    hashmap.put("devcode", tempdev);
                    hashmap.put("thingcode", tempthg);
                    hashmap.put("starttime", starttime);
                    hashmap.put("endtime", endtime);
                    // 页数默认值-1，不做分页处理
                    hashmap.put("rowindex", DefaultArgument.INT_DEFAULT);
                    hashmap.put("rowcount", DefaultArgument.INT_DEFAULT);
                    List<MonitorStorageModel> templist = monitorStorageDao.getPerHourMonitorData(hashmap);
                    list.addAll(templist);
                    allcount += (int) hashmap.get("allcount");
                }
            }
            Map<Integer, List<MonitorStorageModel>> result = new HashMap<Integer, List<MonitorStorageModel>>();
            result.put(allcount, list);
            return result;
        } catch (Exception e) {
            logger.error(LOG + ":获取小时历史数据失败，错误信息为：" + e.getMessage());
            return null;
        }
    }

    @Override
    public Map<Integer, List<MonitorStorageModel>> getPerDayMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime) {
        try {
            String dboldname = "";
            if (!DateUtil.isRecentlyData(starttime, DefaultArgument.RECENT_DAYS)) {
                dboldname = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            int allcount = 0;
            List<MonitorStorageModel> list = new ArrayList<MonitorStorageModel>();
            for (String tempdev : listdevicecode) {
                for (String tempthg : listthingcode) {
                    Map<String, Object> hashmap = new HashMap<String, Object>();
                    hashmap.put("dboldname", dboldname);
                    hashmap.put("devcode", tempdev);
                    hashmap.put("thingcode", tempthg);
                    hashmap.put("starttime", starttime);
                    hashmap.put("endtime", endtime);
                    // 页数默认值-1，不做分页处理
                    hashmap.put("rowindex", DefaultArgument.INT_DEFAULT);
                    hashmap.put("rowcount", DefaultArgument.INT_DEFAULT);
                    List<MonitorStorageModel> templist = monitorStorageDao.getPerDayMonitorData(hashmap);
                    list.addAll(templist);
                    allcount += (int) hashmap.get("allcount");
                }
            }
            Map<Integer, List<MonitorStorageModel>> result = new HashMap<Integer, List<MonitorStorageModel>>();
            result.put(allcount, list);
            return result;
        } catch (Exception e) {
            logger.error(LOG + ":获取天历史数据失败，错误信息为：" + e.getMessage());
            return null;
        }
    }

    @Override
    public Map<Integer, List<MonitorStorageModel>> getPerMonthMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime) {
        try {
            String dboldname = "";
            if (!DateUtil.isRecentlyData(starttime, DefaultArgument.RECENT_DAYS)) {
                dboldname = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            int allcount = 0;
            List<MonitorStorageModel> list = new ArrayList<MonitorStorageModel>();
            for (String tempdev : listdevicecode) {
                for (String tempthg : listthingcode) {
                    Map<String, Object> hashmap = new HashMap<String, Object>();
                    hashmap.put("dboldname", dboldname);
                    hashmap.put("devcode", tempdev);
                    hashmap.put("thingcode", tempthg);
                    hashmap.put("starttime", starttime);
                    hashmap.put("endtime", endtime);
                    // 页数默认值-1，不做分页处理
                    hashmap.put("rowindex", DefaultArgument.INT_DEFAULT);
                    hashmap.put("rowcount", DefaultArgument.INT_DEFAULT);
                    List<MonitorStorageModel> templist = monitorStorageDao.getPerMonthMonitorData(hashmap);
                    list.addAll(templist);
                    allcount += (int) hashmap.get("allcount");
                }
            }
            Map<Integer, List<MonitorStorageModel>> result = new HashMap<Integer, List<MonitorStorageModel>>();
            result.put(allcount, list);
            return result;
        } catch (Exception e) {
            logger.error(LOG + ":获取月历史数据失败，错误信息为：" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<Integer, List<MonitorStorageModel>> getPerQuarterMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime) {
        try {
            String dboldname = "";
            if (!DateUtil.isRecentlyData(starttime, DefaultArgument.RECENT_DAYS)) {
                dboldname = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            int allcount = 0;
            List<MonitorStorageModel> list = new ArrayList<MonitorStorageModel>();
            for (String tempdev : listdevicecode) {
                for (String tempthg : listthingcode) {
                    Map<String, Object> hashmap = new HashMap<String, Object>();
                    hashmap.put("dboldname", dboldname);
                    hashmap.put("devcode", tempdev);
                    hashmap.put("thingcode", tempthg);
                    hashmap.put("starttime", starttime);
                    hashmap.put("endtime", endtime);
                    // 页数默认值-1，不做分页处理
                    hashmap.put("rowindex", DefaultArgument.INT_DEFAULT);
                    hashmap.put("rowcount", DefaultArgument.INT_DEFAULT);
                    List<MonitorStorageModel> templist = monitorStorageDao.getPerQuarterMonitorData(hashmap);
                    list.addAll(templist);
                    allcount += (int) hashmap.get("allcount");
                }
            }
            Map<Integer, List<MonitorStorageModel>> result = new HashMap<Integer, List<MonitorStorageModel>>();
            result.put(allcount, list);
            return result;
        } catch (Exception e) {
            logger.error(LOG + ":获取季度历史数据失败，错误信息为：" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<Integer, List<MonitorStorageModel>> getPerMinuteStaMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime) {
        try {
            String dboldname = "";
            if (!DateUtil.isRecentlyData(starttime, DefaultArgument.RECENT_DAYS)) {
                dboldname = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            int allcount = 0;
            List<MonitorStorageModel> list = new ArrayList<MonitorStorageModel>();
            for (String tempdev : listdevicecode) {
                for (String tempthg : listthingcode) {
                    Map<String, Object> hashmap = new HashMap<String, Object>();
                    hashmap.put("dboldname", dboldname);
                    hashmap.put("devcode", tempdev);
                    hashmap.put("thingcode", tempthg);
                    hashmap.put("starttime", starttime);
                    hashmap.put("endtime", endtime);
                    // 页数默认值-1，不做分页处理
                    hashmap.put("rowindex", DefaultArgument.INT_DEFAULT);
                    hashmap.put("rowcount", DefaultArgument.INT_DEFAULT);
                    List<MonitorStorageModel> templist = monitorStorageDao.getPerMinuteStaMonitorData(hashmap);
                    list.addAll(templist);
                    allcount += (int) hashmap.get("allcount");
                }
            }
            Map<Integer, List<MonitorStorageModel>> result = new HashMap<Integer, List<MonitorStorageModel>>();
            result.put(allcount, list);
            return result;
        } catch (Exception e) {
            logger.error(LOG + ":获取小时历史数据失败，错误信息为：" + e.getMessage());
            return null;
        }
    }

    @Override
    public Map<Integer, List<MonitorStorageModel>> getPerHourStaMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime) {
        try {
            String dboldname = "";
            if (!DateUtil.isRecentlyData(starttime, DefaultArgument.RECENT_DAYS)) {
                dboldname = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            int allcount = 0;
            List<MonitorStorageModel> list = new ArrayList<MonitorStorageModel>();
            for (String tempdev : listdevicecode) {
                for (String tempthg : listthingcode) {
                    Map<String, Object> hashmap = new HashMap<String, Object>();
                    hashmap.put("dboldname", dboldname);
                    hashmap.put("devcode", tempdev);
                    hashmap.put("thingcode", tempthg);
                    hashmap.put("starttime", starttime);
                    hashmap.put("endtime", endtime);
                    // 页数默认值-1，不做分页处理
                    hashmap.put("rowindex", DefaultArgument.INT_DEFAULT);
                    hashmap.put("rowcount", DefaultArgument.INT_DEFAULT);
                    List<MonitorStorageModel> templist = monitorStorageDao.getPerHourStaMonitorData(hashmap);
                    list.addAll(templist);
                    allcount += (int) hashmap.get("allcount");
                }
            }
            Map<Integer, List<MonitorStorageModel>> result = new HashMap<Integer, List<MonitorStorageModel>>();
            result.put(allcount, list);
            return result;
        } catch (Exception e) {
            logger.error(LOG + ":获取小时历史数据失败，错误信息为：" + e.getMessage());
            return null;
        }
    }

    @Override
    public Map<Integer, List<MonitorStorageModel>> getPerDayStaMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime) {
        try {
            String dboldname = "";
            if (!DateUtil.isRecentlyData(starttime, DefaultArgument.RECENT_DAYS)) {
                dboldname = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            int allcount = 0;
            List<MonitorStorageModel> list = new ArrayList<MonitorStorageModel>();
            for (String tempdev : listdevicecode) {
                for (String tempthg : listthingcode) {
                    Map<String, Object> hashmap = new HashMap<String, Object>();
                    hashmap.put("dboldname", dboldname);
                    hashmap.put("devcode", tempdev);
                    hashmap.put("thingcode", tempthg);
                    hashmap.put("starttime", starttime);
                    hashmap.put("endtime", endtime);
                    // 页数默认值-1，不做分页处理
                    hashmap.put("rowindex", DefaultArgument.INT_DEFAULT);
                    hashmap.put("rowcount", DefaultArgument.INT_DEFAULT);
                    List<MonitorStorageModel> templist = monitorStorageDao.getPerDayStaMonitorData(hashmap);
                    list.addAll(templist);
                    allcount += (int) hashmap.get("allcount");
                }
            }
            Map<Integer, List<MonitorStorageModel>> result = new HashMap<Integer, List<MonitorStorageModel>>();
            result.put(allcount, list);
            return result;
        } catch (Exception e) {
            logger.error(LOG + ":获取天历史数据失败，错误信息为：" + e.getMessage());
            return null;
        }
    }

    @Override
    public Map<Integer, List<MonitorStorageModel>> getPerMonthStaMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime) {
        try {
            String dboldname = "";
            if (!DateUtil.isRecentlyData(starttime, DefaultArgument.RECENT_DAYS)) {
                dboldname = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            int allcount = 0;
            List<MonitorStorageModel> list = new ArrayList<MonitorStorageModel>();
            for (String tempdev : listdevicecode) {
                for (String tempthg : listthingcode) {
                    Map<String, Object> hashmap = new HashMap<String, Object>();
                    hashmap.put("dboldname", dboldname);
                    hashmap.put("devcode", tempdev);
                    hashmap.put("thingcode", tempthg);
                    hashmap.put("starttime", starttime);
                    hashmap.put("endtime", endtime);
                    // 页数默认值-1，不做分页处理
                    hashmap.put("rowindex", DefaultArgument.INT_DEFAULT);
                    hashmap.put("rowcount", DefaultArgument.INT_DEFAULT);
                    List<MonitorStorageModel> templist = monitorStorageDao.getPerMonthStaMonitorData(hashmap);
                    list.addAll(templist);
                    allcount += (int) hashmap.get("allcount");
                }
            }
            Map<Integer, List<MonitorStorageModel>> result = new HashMap<Integer, List<MonitorStorageModel>>();
            result.put(allcount, list);
            return result;
        } catch (Exception e) {
            logger.error(LOG + ":获取月历史数据失败，错误信息为：" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<Integer, List<MonitorStorageModel>> getPerQuarterStaMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime) {
        try {
            String dboldname = "";
            if (!DateUtil.isRecentlyData(starttime, DefaultArgument.RECENT_DAYS)) {
                dboldname = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            int allcount = 0;
            List<MonitorStorageModel> list = new ArrayList<MonitorStorageModel>();
            for (String tempdev : listdevicecode) {
                for (String tempthg : listthingcode) {
                    Map<String, Object> hashmap = new HashMap<String, Object>();
                    hashmap.put("dboldname", dboldname);
                    hashmap.put("devcode", tempdev);
                    hashmap.put("thingcode", tempthg);
                    hashmap.put("starttime", starttime);
                    hashmap.put("endtime", endtime);
                    // 页数默认值-1，不做分页处理
                    hashmap.put("rowindex", DefaultArgument.INT_DEFAULT);
                    hashmap.put("rowcount", DefaultArgument.INT_DEFAULT);
                    List<MonitorStorageModel> templist = monitorStorageDao.getPerQuarterStaMonitorData(hashmap);
                    list.addAll(templist);
                    allcount += (int) hashmap.get("allcount");
                }
            }
            Map<Integer, List<MonitorStorageModel>> result = new HashMap<Integer, List<MonitorStorageModel>>();
            result.put(allcount, list);
            return result;
        } catch (Exception e) {
            logger.error(LOG + ":获取季度历史数据失败，错误信息为：" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MonitorStorageModel> getTimelyMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp nowtime, Timestamp selecttime) {
        List<MonitorStorageModel> liststorage = new ArrayList<MonitorStorageModel>();
        try {
            for (String devicecode : listdevicecode) {
                try {
                    liststorage.addAll(monitorStorageDao.getTimelyMonitorData(devicecode, listthingcode, nowtime, selecttime));
                } catch (Exception e) {
                    logger.error(LOG + "：查询实时数据失败：" + devicecode);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：查询实时数据失败，信息为：" + e.getMessage());
        }
        return liststorage;
    }

    @Override
    public List<String> getMonNamebyCode(List<String> listMonCode) {
        return monitorStorageDao.getMonNamebyCode(listMonCode);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int getOriginalDataCount(List<String> listdevicecode,
                                    List<String> listthingcode, Timestamp begintime, Timestamp endtime,
                                    String updateType, String select) {
        int count = 0;
        try {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            if (!DateUtil.isRecentlyData(begintime, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            for (String devicecode : listdevicecode) {
                try {
                    count = monitorStorageDao.getOriginalDataCount(dbName, dbOldName, devicecode,
                            listthingcode, begintime, endtime, updateType, select);
                } catch (Exception e) {
                    logger.error(LOG + "：查询原始数据个数失败：" + devicecode);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：查询原始数据个数失败，信息为：" + e.getMessage());
        }
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<OriginalDataModel> getOriginalData(List<String> listdevicecode,
                                                   List<String> listthingcode, Timestamp begintime, Timestamp endtime,
                                                   String updateType, String select, int rowIndex, int rowCount) {
        List<OriginalDataModel> liststorage = new ArrayList<OriginalDataModel>();
        try {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            if (!DateUtil.isRecentlyData(begintime, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            for (String devicecode : listdevicecode) {
                try {
                    liststorage.addAll(monitorStorageDao.getOriginalData(dbName, dbOldName,
                            devicecode, listthingcode, begintime, endtime,
                            updateType, select, rowIndex, rowCount));
                } catch (Exception e) {
                    logger.error(LOG + "：查询原始数据失败：" + devicecode);
                }
            }

        } catch (Exception e) {
            logger.error(LOG + "：查询原始数据失败，信息为：" + e.getMessage());
        }
        return liststorage;
    }

    @Override
    public List<NetStatusCount> getNetStatusCount(String userCode,
                                                  List<String> listdevicecode) {
        List<NetStatusCount> list = new ArrayList<NetStatusCount>();
        try {
            list = monitorStorageDao.getNetStatusCount(userCode, listdevicecode);
        } catch (Exception e) {
            logger.error(LOG + "：查询网络状态个数失败，信息为：" + e.getMessage());
        }
        return list;
    }
}
