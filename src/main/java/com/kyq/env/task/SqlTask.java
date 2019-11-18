package com.kyq.env.task;

import com.kyq.env.service.IDeviceService;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设备数据插入计划
 *
 * @author kyq
 * @version V1.0
 * @date 2019/11/15 15:40
 */
@Component
public class SqlTask {
    /**
     * 日志输出标记
     */
    private static final String LOG = "SqlTask";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(SqlTask.class);

    @Resource
    private IDeviceService deviceService;

    /**
     * 添加实时数据 2011
     * 每分钟的第45秒执行一次
     */
    @Scheduled(cron = "45 * * * * ?")
    private void rtdCycle() {
        try {
            logger.info(LOG + " : 开始添加实时数据");
            List<String> deviceCodes = deviceService.getDeviceCodes();
            deviceService.insertRtdDeviceData(deviceCodes);
        } catch (Exception e) {
            logger.error(LOG + " : 添加实时数据失败，失败信息：" + e.getMessage());
        }

    }

    /**
     * 添加分钟数据 2051
     * 每隔十分钟的第30秒执行一次
     */
    @Scheduled(cron = "30 0/10 * * * ?")
    private void minuteCycle() {
        try {
            logger.info(LOG + " : 开始添加分钟数据");
            List<String> deviceCodes = deviceService.getDeviceCodes();
            deviceService.insertMinuteDeviceData(deviceCodes);
        } catch (Exception e) {
            logger.error(LOG + " : 添加分钟数据失败，失败信息：" + e.getMessage());
        }
    }

    /**
     * 添加小时数据 2061
     * 每小时的35分15秒执行一次
     */
    @Scheduled(cron = "15 35 * * * ?")
    private void hourCycle() {
        try {
            logger.info(LOG + " : 开始添加小时数据");
            List<String> deviceCodes = deviceService.getDeviceCodes();
            deviceService.insertHourDeviceData(deviceCodes);
        } catch (Exception e) {
            logger.error(LOG + " : 添加小时数据失败，失败信息：" + e.getMessage());
        }
    }

    /**
     * 添加每日数据 2031
     * 每天12点执行一次
     */
    @Scheduled(cron = "0 0 12 * * ?")
    private void dayCycle() {
        try {
            logger.info(LOG + " : 开始添加每日数据");
            List<String> deviceCodes = deviceService.getDeviceCodes();
            deviceService.insertDayDeviceData(deviceCodes);
        } catch (Exception e) {
            logger.error(LOG + " : 添加每日数据失败，失败信息：" + e.getMessage());
        }
    }

}
