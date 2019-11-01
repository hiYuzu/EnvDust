package com.kyq.env.service;

import java.util.List;

import com.kyq.env.pojo.Authority;
import com.tcb.env.pojo.Authority;

/**
 * [功能描述]：权限组操作服务类接口
 *
 * @author kyq
 */
public interface IAuthorityService {

	/**
	 * [功能描述]：查询权限组个数
	 */
	int getAuthorityCount(Authority authority);

	/**
	 * [功能描述]：查询权限组数据
	 */
	List<Authority> getAuthority(Authority authority);

	/**
	 * [功能描述]：插入权限组数据
	 */
	int insertAuthority(List<Authority> listAuthority) throws Exception;

	/**
	 * [功能描述]：更新权限组数据
	 */
	int updateAuthority(List<Authority> listAuthority) throws Exception;

	/**
	 * [功能描述]：删除权限组数据
	 */
	int deleteAuthority(List<String> listid) throws Exception;

	/**
	 * [功能描述]：通过条件（非ID的）获取符合结果个数
	 */
	int getAuthorityExist(String authotiryCode, int authotiryId);
	
	/**
	 * [功能描述]：通过Id获取编码
	 */
	String getAuthorityCodeById(String authorityId);
	
}
