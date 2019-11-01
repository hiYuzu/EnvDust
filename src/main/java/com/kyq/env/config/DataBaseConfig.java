package com.kyq.env.config;

import org.springframework.stereotype.Component;

/**
 * 系统默认数据库名称参数
 *
 * @author kyq
 */
@Component("DataBaseConfig")
public class DataBaseConfig {

    private String dbName = "envdust";
    private String dbOldName = "envdust_old";

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return the dbOldName
     */
    public String getDbOldName() {
        return dbOldName;
    }

    /**
     * @param dbOldName the dbOldName to set
     */
    public void setDbOldName(String dbOldName) {
        this.dbOldName = dbOldName;
    }

}
