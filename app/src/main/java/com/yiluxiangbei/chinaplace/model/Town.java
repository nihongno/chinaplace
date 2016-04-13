package com.yiluxiangbei.chinaplace.model;

/**
 * Created by yiluxiangbei on 2016/4/13.
 */
public class Town {

    private int id;
    private String townName;
    private String townCode;
    private int countyId;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTownName() {
        return townName;
    }
    public void setTownName(String townName) {
        this.townName = townName;
    }
    public String getTownCode() {
        return townCode;
    }
    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }
    public int getCountyId() {
        return countyId;
    }
    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }
}
