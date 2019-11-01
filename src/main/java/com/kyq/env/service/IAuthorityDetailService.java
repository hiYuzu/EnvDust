package com.kyq.env.service;

import java.util.List;

import com.kyq.env.model.TreeModel;
import com.kyq.env.pojo.AuthorityDetail;
import com.tcb.env.model.TreeModel;
import com.tcb.env.pojo.AuthorityDetail;

/**
 * [功能描述]：权限组控件明细操作服务类接口
 *
 * @author kyq
 */
public interface IAuthorityDetailService {

    /**
     * [功能描述]：查询权限明细控件数据
     */
    List<AuthorityDetail> getAuthorityDetail(String ahrCode);

    /**
     * [功能描述]：更新权限组控件数据
     */
    int updateAuthorityDetail(String ahrCode, List<TreeModel> listTree, int optuser) throws Exception;
}
