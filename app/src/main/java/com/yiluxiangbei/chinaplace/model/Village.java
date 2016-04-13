package com.yiluxiangbei.chinaplace.model;

/**
 * Created by yiluxiangbei on 2016/4/13.
 */
public class Village {

    private int id;
    private String villageName;
    private String villageCode;
    private int townId;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getVillageName() {
        return villageName;
    }
    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }
    public String getVillageCode() {
        return villageCode;
    }
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }
    public int getTownId() {
        return townId;
    }
    public void setTownId(int townId) {
        this.townId = townId;
    }
}
