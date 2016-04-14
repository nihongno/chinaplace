package com.yiluxiangbei.chinaplace.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yiluxiangbei on 2016/4/13.
 */
public class PlaceOpenHelper extends SQLiteOpenHelper {

    /**
     * province 省级数据表
     */
    public static final String CREATE_PROVINCE = "create table Province("
            + "id integer primary key autoincrement,"
            + "province_name text,"
            + "province_code text)";
    /**
     * city 市级数据表
     */
    public static final String CREATE_CITY = "create table City("
            + "id integer primary key autoincrement,"
            + "city_name text,"
            + "city_code text,"
            + "province_id integer)";
    /**
     * county 县级数据表
     */
    public static final String CREATE_COUNTY = "create table County("
            + "id integer primary key autoincrement,"
            + "county_name text,"
            + "county_code text,"
            + "city_id integer)";
    /**
     * town 镇级数据表
     */
    public static final String CREATE_TOWN = "create table Town("
            + "id integer primary key autoincrement,"
            + "town_name text,"
            + "town_code text,"
            + "county_id integer)";
    /**
     * village 村级数据表
     */
    public static final String CREATE_VILLAGE = "create table Village("
            + "id integer primary key autoincrement,"
            + "village_name text,"
            + "village_code text,"
            + "town_id integer)";

    public PlaceOpenHelper (Context context,String name,SQLiteDatabase.CursorFactory factory,int version) {
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
        db.execSQL(CREATE_TOWN);
        db.execSQL(CREATE_VILLAGE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {
        db.execSQL("drop table if exists Province");
        db.execSQL("drop table if exists City");
        db.execSQL("drop table if exists County");
        db.execSQL("drop table if exists Town");
        db.execSQL("drop table if exists Village");
        onCreate(db);
    }
}
