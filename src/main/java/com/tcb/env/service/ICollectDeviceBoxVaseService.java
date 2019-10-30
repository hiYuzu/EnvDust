package com.tcb.env.service;

import java.util.List;

import com.tcb.env.model.VaseDataModel;
import com.tcb.env.pojo.CollectDeviceBoxVase;
import com.tcb.env.pojo.CommMain;

/**
 * 
 * <p>[功能描述]：采样记录服务接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月26日下午3:50:04
 * @since	EnvDust 1.0.0
 *
 */
public interface ICollectDeviceBoxVaseService {
	
	/**
	 * 
	 * <p>[功能描述]：获取采样详细数据</p>
	 * 
	 * @author	王垒, 2018年7月30日上午10:55:36
	 * @since	EnvDust 1.0.0
	 *
	 * @param deviceCode
	 * @param vaseNo
	 * @return
	 */
	List<VaseDataModel> getVaseDataModel(String deviceCode,String vaseNo);

}
