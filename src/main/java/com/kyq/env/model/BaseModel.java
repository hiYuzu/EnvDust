package com.kyq.env.model;

import com.kyq.env.util.DefaultArgument;
import com.tcb.env.util.DefaultArgument;

import java.io.Serializable;

/**
 * [功能描述]：页面传递User基类
 *
 * @author kyq
 */
public class BaseModel implements Serializable {

    /**
     * 操作者
     */
    private String optUserName;
    /**
     * 操作时间
     */
    private String optTime;
    /**
     * 每页行数
     */
    private int rows = DefaultArgument.INT_DEFAULT;
    /**
     * 当前页
     */
    private int page = DefaultArgument.INT_DEFAULT;

    /**
     * @return the optUserName
     */
    public String getOptUserName() {
        return optUserName;
    }

    /**
     * @param optUserName the optUserName to set
     */
    public void setOptUserName(String optUserName) {
        this.optUserName = optUserName;
    }

    /**
     * @return the optTime
     */
    public String getOptTime() {
        return optTime;
    }

    /**
     * @param optTime the optTime to set
     */
    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }
}
