package com.yiluxiangbei.chinaplace.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yiluxiangbei.chinaplace.db.PlaceOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yiluxiangbei on 2016/4/13.
 */
public class ChinaPlaceDB {

    /**
     * 数据库名
     */
    public static final String DB_NAME = "china_place";
    /**
     * 数据库版本
     */
   // public static final int VERSION = 1;
    private static ChinaPlaceDB chinaPlaceDB;
    private SQLiteDatabase db;

    /**
     * 构造方法私有化
     */
    private ChinaPlaceDB(Context context) {
        PlaceOpenHelper dbHelper = new PlaceOpenHelper(context,DB_NAME,null,3);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取ChinaPlaceDB实例
     */
    public synchronized static ChinaPlaceDB getInstance(Context context) {
        if (chinaPlaceDB == null) {
            chinaPlaceDB = new ChinaPlaceDB(context);
        }
        return chinaPlaceDB;
    }
    /**
     * 将province实例（上的信息）储存到数据库
     */
    public void saveProvince(Province province) {
        ContentValues values = new ContentValues();
        values.put("province_name",province.getProvinceName());
        values.put("province_code",province.getProvinceCode());
        db.insert("Province", null, values);
    }
    /**
     * 从数据库上读取所有省份数据
     */
    public List<Province> loadProvince() {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    /**
     * 将city实例信息储存到数据库
     */
    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City",null,values);
        }
    }
    /**
     * 从数据库读取某省下所有城市数据
     */
    public List<City> loadCity(int provinceId) {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City",null,"province_id = ?",new String[] {String.valueOf(provinceId)},null,null,null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    /**
     * 将county实例信息储存到数据库
     */
    public void saveCounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            db.insert("County",null,values);
        }
    }
    /**
     * 从数据库读取某市下所有县数据
     */
    public List<County> loadCounty(int cityId) {
      //  String id = String.valueOf(cityId);
       // Log.d("ChinaPlaceDB","city - id :" + id);
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County",null,"city_id = ?",new String[] {String.valueOf(cityId)},null,null,null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    /**
     * 将town实例信息储蓄到数据库
     */
    public void saveTown(Town town) {
        if (town != null) {
            ContentValues values = new ContentValues();
            values.put("town_name",town.getTownName());
            values.put("town_code",town.getTownCode());
            values.put("county_id",town.getCountyId());
            db.insert("Town",null,values);
        }
    }
    /**
     * 从数据库读取某县下所有城镇数据
     */
    public List<Town> loadTown(int countyId) {
      //  String id = String.valueOf(countyId);
       // Log.d("ChinaPlaceDB","county - id :" + id);
        List<Town> list = new ArrayList<Town>();
        Cursor cursor = db.query("Town",null,"county_id = ?",new String[]{ String.valueOf(countyId) },null,null,null);
        if (cursor.moveToFirst()) {
            do {
                Town town = new Town();
                town.setId(cursor.getInt(cursor.getColumnIndex("id")));
                town.setTownName(cursor.getString(cursor.getColumnIndex("town_name")));
                town.setTownCode(cursor.getString(cursor.getColumnIndex("town_code")));
                town.setCountyId(countyId);
                list.add(town);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    /**
     * 将village实例信息储存到数据库
     */
    public void saveVillage(Village village) {
        if (village != null) {
            ContentValues values = new ContentValues();
            values.put("village_name",village.getVillageName());
            values.put("village_code",village.getVillageCode());
            values.put("town_id",village.getTownId());
            db.insert("Village",null,values);
        }
    }
    /**
     * 从数据库读取某镇下所有乡村信息
     */
    public List<Village> loadVillage(int townId) {
        List<Village> list = new ArrayList<Village>();
        Cursor cursor = db.query("Village",null,"town_id = ?",new String[]{String.valueOf(townId)},null,null,null);
        if (cursor.moveToFirst()) {
            do {
                Village village = new Village();
                village.setId(cursor.getInt(cursor.getColumnIndex("id")));
                village.setVillageName(cursor.getString(cursor.getColumnIndex("village_name")));
                village.setVillageCode(cursor.getString(cursor.getColumnIndex("village_code")));
                village.setTownId(townId);
                list.add(village);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
