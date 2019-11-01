package com.kyq.env.dao;

import java.util.List;

import com.kyq.env.pojo.Oranization;
import org.apache.ibatis.annotations.Param;

/**
 * [功能描述]：组织dao
 */
public interface IOranizationDao {
		/**
		 * [功能描述]：查询数据个数是否模糊查询用到获方法
		 */
		int getCount(@Param("oranization")Oranization oranization, @Param("isLike")boolean isLike);
		/**
		 * [功能描述]：查询组织数据
		 */
		List<Oranization> getOranization(@Param("oranization")Oranization oranization);
		/**
		 * [功能描述]：增加组织数据
		 */
		int insertOranization(@Param("oranization")Oranization oranization);
		/**
		 * [功能描述]：删除组织数据
		 */
		int deleteOranization(@Param("oranizationid")List<Integer> oranizationid);
		/**
		 * [功能描述]：更新组织数据
		 */
		int updateOranization(@Param("list")List<Oranization> list);
		/**
		 * [功能描述]：判断组织数据是否存在
		 */
		int getOranizationExist(@Param("oranizationid")int oranizationid,@Param("oranizationname")String oranizationname);
		/**
		 * [功能描述]：通过ID得到组织路径
		 */
		String getOranizationPathById(@Param("orgid")int orgid);
}
