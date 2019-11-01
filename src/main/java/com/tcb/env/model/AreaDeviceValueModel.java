package com.tcb.env.model;

/**
 * [功能描述]：区域设备model
 *
 * @author kyq
 */
public class AreaDeviceValueModel extends BaseModel {

    private String areaId;
    private String areaName;
    private String deviceCode;
    private String thingCode;
    private String dataType;
    private String dataTime;
    private String dataValue;

    /**
     * @return the areaId
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * @param areaId the areaId to set
     */
    public void setAreaId(String areaId) {
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
     * @return the deviceCode
     */
    public String getDeviceCode() {
        return deviceCode;
    }

    /**
     * @param deviceCode the deviceCode to set
     */
    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    /**
     * @return the thingCode
     */
    public String getThingCode() {
        return thingCode;
    }

    /**
     * @param thingCode the thingCode to set
     */
    public void setThingCode(String thingCode) {
        this.thingCode = thingCode;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the dataTime
     */
    public String getDataTime() {
        return dataTime;
    }

    /**
     * @param dataTime the dataTime to set
     */
    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    /**
     * @return the dataValue
     */
    public String getDataValue() {
        return dataValue;
    }

    /**
     * @param dataValue the dataValue to set
     */
    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

}
