package com.kyq.env.dao;

import java.util.List;

import com.kyq.env.pojo.AuthorityDetail;
import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.AuthorityDetail;

/**
 * [功能描述]：权限组控件明细数据库操作接口
 */
public interface IAuthorityDetailDao {

	/**
	 * [功能描述]：查询权限明细控件数据
	 */
	List<AuthorityDetail> getAuthorityDetail(@Param("ahrCode") String ahrCode);

	/**
	 * [功能描述]：新添权限明细控件数据
	 */
	int insertAuthorityDetail(
			@Param("listAhrDetail") List<AuthorityDetail> listAhrDetail);

	/**
	 * [功能描述]：删除权限明细控件数据
	 */
	int deleteAuthorityDetail(@Param("listAhrCode") List<String> listAhrCode);

}
