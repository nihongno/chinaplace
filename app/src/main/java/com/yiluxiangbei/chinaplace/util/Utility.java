package com.yiluxiangbei.chinaplace.util;

import android.text.TextUtils;

import com.yiluxiangbei.chinaplace.model.ChinaPlaceDB;
import com.yiluxiangbei.chinaplace.model.City;
import com.yiluxiangbei.chinaplace.model.County;
import com.yiluxiangbei.chinaplace.model.Province;
import com.yiluxiangbei.chinaplace.model.Town;
import com.yiluxiangbei.chinaplace.model.Village;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by yiluxiangbei on 2016/4/14.
 */
public class Utility {

    /**
     * 解析处理服务器返回的省级数据
     */
    public synchronized static boolean handleProvinceResponse(ChinaPlaceDB chinaPlaceDB,String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject object = jsonObject.getJSONObject("str");
                JSONArray array = object.getJSONArray("regions");
                if (array != null && array.length() > 0) {
                    for (int i = 0;i < array.length();i++) {
                        Province province = new Province();
                        JSONObject data = array.getJSONObject(i);
                        province.setProvinceName(data.getString("name"));
                        province.setProvinceCode(data.getString("id"));
                        chinaPlaceDB.saveProvince(province);
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 解析处理服务器返回的市级数据
     */
    public static boolean handleCityResponse(ChinaPlaceDB chinaPlaceDB,String response,int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject object = jsonObject.getJSONObject("str");
                JSONArray array = object.getJSONArray("regions");
                if (array != null && array.length() > 0) {
                    for (int i = 0;i < array.length();i++) {
                        City city = new City();
                        JSONObject data = array.getJSONObject(i);
                        city.setCityName(data.getString("name"));
                        city.setCityCode(data.getString("id"));
                        city.setProvinceId(provinceId);
                        chinaPlaceDB.saveCity(city);
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 解析处理服务器返回的县级数据
     */
    public static boolean handleCountyResponse(ChinaPlaceDB chinaPlaceDB,String response,int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject object = jsonObject.getJSONObject("str");
                JSONArray array = object.getJSONArray("regions");
                if (array != null && array.length() > 0) {
                    for (int i = 0;i < array.length();i++) {
                        County county = new County();
                        JSONObject data = array.getJSONObject(i);
                        county.setCountyName(data.getString("name"));
                        county.setCountyCode(data.getString("id"));
                        county.setCityId(cityId);
                        chinaPlaceDB.saveCounty(county);
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 解析处理服务器返回的镇级数据
     */
    public static boolean handleTownResponse(ChinaPlaceDB chinaPlaceDB,String response,int countyId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject object = jsonObject.getJSONObject("str");
                JSONArray array = object.getJSONArray("regions");
                if (array != null && array.length() > 0) {
                    for (int i = 0;i < array.length();i++) {
                        Town town = new Town();
                        JSONObject data = array.getJSONObject(i);
                        town.setTownName(data.getString("name"));
                        town.setTownCode(data.getString("id"));
                        town.setCountyId(countyId);
                        chinaPlaceDB.saveTown(town);
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 处理服务器返回的村级数据
     */
    public static boolean handleVillageResponse(ChinaPlaceDB chinaPlaceDB,String response,int townId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject object = jsonObject.getJSONObject("str");
                JSONArray array = object.getJSONArray("regions");
                if (array != null && array.length() > 0) {
                    for (int i = 0;i < array.length();i++) {
                        Village village = new Village();
                        JSONObject data = array.getJSONObject(i);
                        village.setVillageName(data.getString("name"));
                        village.setVillageCode(data.getString("id"));
                        village.setTownId(townId);
                        chinaPlaceDB.saveVillage(village);
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
