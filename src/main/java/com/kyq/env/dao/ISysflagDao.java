package com.kyq.env.dao;
import java.util.List;

import com.kyq.env.pojo.Sysflag;
import org.apache.ibatis.annotations.Param;

/**
 * @author kkyq
 */
public interface ISysflagDao {
	/**
	 * 获取系统标识值
	 */
	List<Sysflag> getSysflag(@Param("sysflag")Sysflag sysflag);
}
