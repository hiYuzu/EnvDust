package com.kyq.env.pojo;

import java.sql.Timestamp;

import com.kyq.env.util.DefaultArgument;

/**
 * <p>[功能描述]：设备pojo</p>
 */
public class Device extends BasePojo {
    private int deviceId = DefaultArgument.INT_DEFAULT;
    private String deviceCode;
    private String deviceMn;
    private String deviceName;
    private Manufacturer manufacturer;
    private Status status;
    private String deviceIp;
    private int devicePort = DefaultArgument.INT_DEFAULT;
    private String devicePwd;
    private double deviceX = DefaultArgument.DOUBLE_DEFAULT;
    private double deviceY = DefaultArgument.DOUBLE_DEFAULT;
    private double gpsX = DefaultArgument.DOUBLE_DEFAULT;
    private double gpsY = DefaultArgument.DOUBLE_DEFAULT;
    private Area area;
    private String systemVersion;
    private Timestamp inspectTime;
    private String buildFirm;
    private User user;
    private String deviceAddress;
    private Oranization oranization;
    private String deviceKm;
    private boolean staMinute = DefaultArgument.DEL_DEFAULT;
    private boolean staHour = DefaultArgument.DEL_DEFAULT;
    private boolean staDay = DefaultArgument.DEL_DEFAULT;
    private String replyFlag;
    private boolean forceReply = DefaultArgument.DEL_DEFAULT;
    private String haveAhr;
    private boolean smsFlag = DefaultArgument.DEL_DEFAULT;
    private double pipeArea;
    private boolean staFlow;
    private DeviceProject deviceProject;
    private int hourCount = DefaultArgument.HOUR_COUNT;

    /**
     * @return the deviceMn
     */
    public String getDeviceMn() {
        return deviceMn;
    }

    /**
     * @param deviceMn the deviceMn to set
     */
    public void setDeviceMn(String deviceMn) {
        this.deviceMn = deviceMn;
    }

    /**
     * @return the buildFirm
     */
    public String getBuildFirm() {
        return buildFirm;
    }

    /**
     * @param buildFirm the buildFirm to set
     */
    public void setBuildFirm(String buildFirm) {
        this.buildFirm = buildFirm;
    }

    /**
     * @return the deviceAddress
     */
    public String getDeviceAddress() {
        return deviceAddress;
    }

    /**
     * @param deviceAddress the deviceAddress to set
     */
    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the oranization
     */
    public Oranization getOranization() {
        return oranization;
    }

    /**
     * @param oranization the oranization to set
     */
    public void setOranization(Oranization oranization) {
        this.oranization = oranization;
    }

    /**
     * @return the deviceId
     */
    public int getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
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
     * @return the manufacturer
     */
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the deviceIp
     */
    public String getDeviceIp() {
        return deviceIp;
    }

    /**
     * @param deviceIp the deviceIp to set
     */
    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    /**
     * @return the devicePort
     */
    public int getDevicePort() {
        return devicePort;
    }

    /**
     * @param devicePort the devicePort to set
     */
    public void setDevicePort(int devicePort) {
        this.devicePort = devicePort;
    }

    /**
     * @return the devicePwd
     */
    public String getDevicePwd() {
        return devicePwd;
    }

    /**
     * @param devicePwd the devicePwd to set
     */
    public void setDevicePwd(String devicePwd) {
        this.devicePwd = devicePwd;
    }

    /**
     * @return the deviceX
     */
    public double getDeviceX() {
        return deviceX;
    }

    /**
     * @param deviceX the deviceX to set
     */
    public void setDeviceX(double deviceX) {
        this.deviceX = deviceX;
    }

    /**
     * @return the deviceY
     */
    public double getDeviceY() {
        return deviceY;
    }

    /**
     * @param deviceY the deviceY to set
     */
    public void setDeviceY(double deviceY) {
        this.deviceY = deviceY;
    }

    /**
     * @return the gpsX
     */
    public double getGpsX() {
        return gpsX;
    }

    /**
     * @param gpsX the gpsX to set
     */
    public void setGpsX(double gpsX) {
        this.gpsX = gpsX;
    }

    /**
     * @return the gpsY
     */
    public double getGpsY() {
        return gpsY;
    }

    /**
     * @param gpsY the gpsY to set
     */
    public void setGpsY(double gpsY) {
        this.gpsY = gpsY;
    }

    /**
     * @return the area
     */
    public Area getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(Area area) {
        this.area = area;
    }

    /**
     * @return the systemVersion
     */
    public String getSystemVersion() {
        return systemVersion;
    }

    /**
     * @param systemVersion the systemVersion to set
     */
    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    /**
     * @return the inspectTime
     */
    public Timestamp getInspectTime() {
        return inspectTime;
    }

    /**
     * @param inspectTime the inspectTime to set
     */
    public void setInspectTime(Timestamp inspectTime) {
        this.inspectTime = inspectTime;
    }

    /**
     * @return the deviceKm
     */
    public String getDeviceKm() {
        return deviceKm;
    }

    /**
     * @param deviceKm the deviceKm to set
     */
    public void setDeviceKm(String deviceKm) {
        this.deviceKm = deviceKm;
    }

    /**
     * @return the staMinute
     */
    public boolean getStaMinute() {
        return staMinute;
    }

    /**
     * @param staMinute the staMinute to set
     */
    public void setStaMinute(boolean staMinute) {
        this.staMinute = staMinute;
    }

    /**
     * @return the staHour
     */
    public boolean getStaHour() {
        return staHour;
    }

    /**
     * @param staHour the staHour to set
     */
    public void setStaHour(boolean staHour) {
        this.staHour = staHour;
    }

    /**
     * @return the staDay
     */
    public boolean getStaDay() {
        return staDay;
    }

    /**
     * @param staDay the staDay to set
     */
    public void setStaDay(boolean staDay) {
        this.staDay = staDay;
    }

    /**
     * @return the replyFlag
     */
    public String getReplyFlag() {
        return replyFlag;
    }

    /**
     * @param replyFlag the replyFlag to set
     */
    public void setReplyFlag(String replyFlag) {
        this.replyFlag = replyFlag;
    }

    /**
     * @return the forceReply
     */
    public boolean getForceReply() {
        return forceReply;
    }

    /**
     * @param forceReply the forceReply to set
     */
    public void setForceReply(boolean forceReply) {
        this.forceReply = forceReply;
    }

    /**
     * @return the haveAhr
     */
    public String getHaveAhr() {
        return haveAhr;
    }

    /**
     * @param haveAhr the haveAhr to set
     */
    public void setHaveAhr(String haveAhr) {
        this.haveAhr = haveAhr;
    }

    /**
     * @return the smsFlag
     */
    public boolean getSmsFlag() {
        return smsFlag;
    }

    /**
     * @param smsFlag the smsFlag to set
     */
    public void setSmsFlag(boolean smsFlag) {
        this.smsFlag = smsFlag;
    }

    /**
     * @return the pipeArea
     */
    public double getPipeArea() {
        return pipeArea;
    }

    /**
     * @param pipeArea the pipeArea to set
     */
    public void setPipeArea(double pipeArea) {
        this.pipeArea = pipeArea;
    }

    /**
     * @return the staFlow
     */
    public boolean getStaFlow() {
        return staFlow;
    }

    /**
     * @param staFlow the staFlow to set
     */
    public void setStaFlow(boolean staFlow) {
        this.staFlow = staFlow;
    }

    public DeviceProject getDeviceProject() {
        return deviceProject;
    }

    public void setDeviceProject(DeviceProject deviceProject) {
        this.deviceProject = deviceProject;
    }

    public int getHourCount() {
        return hourCount;
    }

    public void setHourCount(int hourCount) {
        this.hourCount = hourCount;
    }
}
