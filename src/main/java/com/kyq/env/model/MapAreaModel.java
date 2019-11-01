package com.kyq.env.model;

import java.util.List;

/**
 * [功能描述]：百度地图覆盖区域确定
 */
public class MapAreaModel extends BaseModel {

    private String maId;   //区域id
    private boolean maVisible; //区域码
    private String maName; //区域名称
    private String maUser; //区域负责人
    private List<MapAreaPointModel> points;

    public String getMaId() {
        return maId;
    }

    public void setMaId(String maId) {
        this.maId = maId;
    }


    public boolean isMaVisible() {
        return maVisible;
    }

    public void setMaVisible(boolean maVisible) {
        this.maVisible = maVisible;
    }

    public String getMaName() {
        return maName;
    }

    public void setMaName(String maName) {
        this.maName = maName;
    }

    public String getMaUser() {
        return maUser;
    }

    public void setMaUser(String maUser) {
        this.maUser = maUser;
    }

    public List<MapAreaPointModel> getPoints() {
        return points;
    }

    public void setPoints(List<MapAreaPointModel> points) {
        this.points = points;
    }
}
