package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcb.env.dao.ISysflagDao;
import com.tcb.env.pojo.Sysflag;
import com.tcb.env.service.ISysflagService;

/**
 * @author kyq
 */
@Service("sysflagService")
public class SysflagServiceImpl implements ISysflagService {
	@Resource
	private ISysflagDao sysflagDao;

	@Override
	public String getSysFlagValue(String sysflagcode) {
		String sysFlagValue = null;
		if (sysflagcode != null && !sysflagcode.isEmpty()) {
			Sysflag sysflag = new Sysflag();
			sysflag.setSysFlagCode(sysflagcode);
			List<Sysflag> list = sysflagDao.getSysflag(sysflag);
			if (list != null && list.size() > 0) {
				sysFlagValue = list.get(0).getSysValue();
			}
		}
		return sysFlagValue;
	}

}
