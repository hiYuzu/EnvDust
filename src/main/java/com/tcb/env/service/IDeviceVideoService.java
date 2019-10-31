package com.tcb.env.service;

import com.tcb.env.message.CameraMessage;
import com.tcb.env.model.VideoImageModel;
import com.tcb.env.pojo.DeviceVideo;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>[功能描述]：设备视频服务接口</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2019年06月21日下午15:16:15
 * @since envdust 1.0.0
 */
public interface IDeviceVideoService {

    /**
     * 查询设备视频信息个数
     *
     * @param deviceVideo
     * @return
     */
    int getDeviceVideoCount(DeviceVideo deviceVideo);

    /**
     * 查询设备视频信息
     *
     * @param deviceVideo
     * @return
     */
    List<DeviceVideo> getDeviceVideo(DeviceVideo deviceVideo);

    /**
     * 查询报警抓拍
     *
     * @param videoId
     * @return
     */
    List<VideoImageModel> getVideoAlarmImage(String videoId);

    /**
     * 更新所有VideoToken
     *
     * @param videoToken
     * @return
     */
    int updateVideoToken(String videoToken);

    /**
     * 查询需要拍照的摄像头
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    List<CameraMessage> getPhotographCamera(Timestamp beginTime, Timestamp endTime);

}
