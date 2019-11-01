package com.kyq.env.model;

import com.kyq.env.util.DefaultArgument;

/**
 * [功能描述]：页面传递Area
 *
 * @author kyq
 */
public class AreaModel extends BaseModel {

    private int areaId = DefaultArgument.INT_DEFAULT;
    private String areaName;
    private int parentId = DefaultArgument.INT_DEFAULT;
    private String parentName;
    private int levelId = DefaultArgument.INT_DEFAULT;
    private int levelFlag = DefaultArgument.INT_DEFAULT;
    private String levelName;
    private String areaPath;
    private String cityId;

    /**
     * @return the areaId
     */
    public int getAreaId() {
        return areaId;
    }

    /**
     * @param areaId the areaId to set
     */
    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    /**
     * @return the areaName
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * @param areaName the areaName to set
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * @return the parentId
     */
    public int getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the parentName
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * @param parentName the parentName to set
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * @return the levelId
     */
    public int getLevelId() {
        return levelId;
    }

    /**
     * @param levelId the levelId to set
     */
    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    /**
     * @return the levelFlag
     */
    public int getLevelFlag() {
        return levelFlag;
    }

    /**
     * @param levelFlag the levelFlag to set
     */
    public void setLevelFlag(int levelFlag) {
        this.levelFlag = levelFlag;
    }

    /**
     * @return the levelName
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * @param levelName the levelName to set
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    /**
     * @return the areaPath
     */
    public String getAreaPath() {
        return areaPath;
    }

    /**
     * @param areaPath the areaPath to set
     */
    public void setAreaPath(String areaPath) {
        this.areaPath = areaPath;
    }

    /**
     * @return the cityId
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

}
