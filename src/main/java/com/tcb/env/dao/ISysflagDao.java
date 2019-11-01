package com.tcb.env.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.Sysflag;

/**
 * @author kkyq
 */
public interface ISysflagDao {
	/**
	 * 获取系统标识值
	 */
	List<Sysflag> getSysflag(@Param("sysflag")Sysflag sysflag);
}
