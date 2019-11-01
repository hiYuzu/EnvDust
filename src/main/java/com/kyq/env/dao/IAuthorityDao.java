package com.kyq.env.dao;

import java.util.List;

import com.kyq.env.pojo.Authority;
import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.Authority;

/**
 * [功能描述]：权限组数据库操作接口
 *
 * @author kyq
 */
public interface IAuthorityDao {

	/**
	 * [功能描述]：查询权限组个数
	 */
	int getAuthorityCount(@Param("authority") Authority authority);

	/**
	 * [功能描述]：查询权限组数据
	 */
	List<Authority> getAuthority(@Param("authority") Authority authority);

	/**
	 * [功能描述]：插入权限组数据
	 */
	int insertAuthority(
			@Param("listAuthority") List<Authority> listAuthority);

	/**
	 * [功能描述]：更新权限组数据
	 */
	int updateAuthority(
			@Param("listAuthority") List<Authority> listAuthority);

	/**
	 * [功能描述]：删除权限组数据
	 */
	int deleteAuthority(@Param("listid") List<String> listid);

	/**
	 * [功能描述]：通过条件（非ID的）获取符合结果个数
	 */
	int getAuthorityExist(@Param("authorityCode") String authorityCode,
			@Param("authorityId") int authorityId);
	
	/**
	 * [功能描述]：通过Id获取编码
	 */
	String getAuthorityCodeById(@Param("authorityId") String authorityId);

}
