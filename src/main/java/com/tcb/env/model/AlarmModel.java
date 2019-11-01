package com.tcb.env.model;


/**
 * [功能描述]：页面传递报警映射类
 *
 * @author kyq
 */
public class AlarmModel extends BaseModel {

    private int alarmId;
    private String alarmType;
    private String alarmTypeName;
    private String deviceCode;
    private String deviceName;
    private String alarmInfo;
    private String alarmAction;
    private String actionUser;
    private String alarmStatus;
    private String alarmStatusInfo;
    private String alarmTime;
    private String actionTime;
    private String beginAlarmTime;
    private String endAlarmTime;
    private boolean executeUpdate;
    private String areaId;
    private String areaName;
    private boolean sendFlag;
    private String levelNo;

    /**
     * @return the alarmId
     */
    public int getAlarmId() {
        return alarmId;
    }

    /**
     * @param alarmId the alarmId to set
     */
    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    /**
     * @return the alarmType
     */
    public String getAlarmType() {
        return alarmType;
    }

    /**
     * @return the alarmTypeName
     */
    public String getAlarmTypeName() {
        return alarmTypeName;
    }

    /**
     * @param alarmTypeName the alarmTypeName to set
     */
    public void setAlarmTypeName(String alarmTypeName) {
        this.alarmTypeName = alarmTypeName;
    }

    /**
     * @param alarmType the alarmType to set
     */
    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
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
     * @return the deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @param deviceName the deviceName to set
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * @return the alarmInfo
     */
    public String getAlarmInfo() {
        return alarmInfo;
    }

    /**
     * @param alarmInfo the alarmInfo to set
     */
    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    /**
     * @return the alarmAction
     */
    public String getAlarmAction() {
        return alarmAction;
    }

    /**
     * @param alarmAction the alarmAction to set
     */
    public void setAlarmAction(String alarmAction) {
        this.alarmAction = alarmAction;
    }

    /**
     * @return the actionUser
     */
    public String getActionUser() {
        return actionUser;
    }

    /**
     * @param actionUser the actionUser to set
     */
    public void setActionUser(String actionUser) {
        this.actionUser = actionUser;
    }

    /**
     * @return the alarmStatus
     */
    public String getAlarmStatus() {
        return alarmStatus;
    }

    /**
     * @param alarmStatus the alarmStatus to set
     */
    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    /**
     * @return the alarmStatusInfo
     */
    public String getAlarmStatusInfo() {
        return alarmStatusInfo;
    }

    /**
     * @param alarmStatusInfo the alarmStatusInfo to set
     */
    public void setAlarmStatusInfo(String alarmStatusInfo) {
        this.alarmStatusInfo = alarmStatusInfo;
    }

    /**
     * @return the alarmTime
     */
    public String getAlarmTime() {
        return alarmTime;
    }

    /**
     * @param alarmTime the alarmTime to set
     */
    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    /**
     * @return the actionTime
     */
    public String getActionTime() {
        return actionTime;
    }

    /**
     * @param actionTime the actionTime to set
     */
    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    /**
     * @return the beginAlarmTime
     */
    public String getBeginAlarmTime() {
        return beginAlarmTime;
    }

    /**
     * @param beginAlarmTime the beginAlarmTime to set
     */
    public void setBeginAlarmTime(String beginAlarmTime) {
        this.beginAlarmTime = beginAlarmTime;
    }

    /**
     * @return the endAlarmTime
     */
    public String getEndAlarmTime() {
        return endAlarmTime;
    }

    /**
     * @param endAlarmTime the endAlarmTime to set
     */
    public void setEndAlarmTime(String endAlarmTime) {
        this.endAlarmTime = endAlarmTime;
    }

    /**
     * @return the executeUpdate
     */
    public boolean getExecuteUpdate() {
        return executeUpdate;
    }

    /**
     * @param executeUpdate the executeUpdate to set
     */
    public void setExecuteUpdate(boolean executeUpdate) {
        this.executeUpdate = executeUpdate;
    }

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

    public boolean getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(boolean sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(String levelNo) {
        this.levelNo = levelNo;
    }

}
