package com.kyq.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kyq.env.dao.ISysflagDao;
import com.kyq.env.pojo.Sysflag;
import com.kyq.env.service.ISysflagService;
import org.springframework.stereotype.Service;

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