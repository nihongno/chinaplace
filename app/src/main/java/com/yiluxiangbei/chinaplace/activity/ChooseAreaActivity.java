package com.yiluxiangbei.chinaplace.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yiluxiangbei.chinaplace.R;
import com.yiluxiangbei.chinaplace.model.ChinaPlaceDB;
import com.yiluxiangbei.chinaplace.model.City;
import com.yiluxiangbei.chinaplace.model.County;
import com.yiluxiangbei.chinaplace.model.Province;
import com.yiluxiangbei.chinaplace.model.Town;
import com.yiluxiangbei.chinaplace.model.Village;
import com.yiluxiangbei.chinaplace.util.HttpCallbackListener;
import com.yiluxiangbei.chinaplace.util.HttpUtil;
import com.yiluxiangbei.chinaplace.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yiluxiangbei on 2016/4/14.
 */
public class ChooseAreaActivity extends Activity {

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    public static final int LEVEL_TOWN = 3;
    public static final int LEVEL_VILLAGE = 4;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ChinaPlaceDB chinaPlaceDB;
    private List<String> dataList = new ArrayList<String>();

    /**
     * 省、市、县、镇、村 -- 列表
     */
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private List<Town> townList;
    private List<Village> villageList;
    /**
     * 被选中的省、市、县、镇
     */
    private Province selectedProvince;
    private City selectedCity;
    private County selectedCounty;
    private Town selectedTown;
    /**
     * 选中的级别
     */
    private int currentLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_area);
        listView = (ListView) findViewById(R.id.list_view);
        titleText = (TextView) findViewById(R.id.title_text);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        chinaPlaceDB = ChinaPlaceDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    String pName = selectedProvince.getProvinceName();
                    if (pName.equals("台湾省")||pName.equals("香港特别行政区")||pName.equals("澳门特别行政区")) {
                        Toast.makeText(ChooseAreaActivity.this,selectedProvince.getProvinceName() + "的信息不存在",Toast.LENGTH_SHORT).show();
                    }  else {
                        queryCities();
                    }
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    selectedCounty = countyList.get(position);
                    queryTowns();
                } else if (currentLevel == LEVEL_TOWN) {
                    selectedTown = townList.get(position);
                    queryVillages();
                }
            }
        });
        queryProvinces();
    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器查询
     */
    private void queryProvinces() {
        provinceList = chinaPlaceDB.loadProvince();
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        } else {
            queryFormServer(null,"province");
        }
    }
    /**
     * 查询选中省的所有城市，优先从数据库查询，如果查询不到再去从服务器查询
     */
    private void queryCities() {
        cityList = chinaPlaceDB.loadCity(selectedProvince.getId());
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
        } else {
            queryFormServer(selectedProvince.getProvinceCode(),"city");
        }
    }
    /**
     * 查询选中市的所有县，优先从数据库查询，查询不到再去服务器查询
     */
    private void queryCounties() {
        countyList = chinaPlaceDB.loadCounty(selectedCity.getId());
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedCity.getCityName());
            currentLevel = LEVEL_COUNTY;
        } else {
            queryFormServer(selectedCity.getCityCode(),"county");
        }
    }
    /**
     * 查询选中县的所有镇，优先从数据库上查询，查询不到再去服务器查询
     */
    private void queryTowns() {
        townList = chinaPlaceDB.loadTown(selectedCounty.getId());
        if (townList.size() > 0) {
            dataList.clear();
            for (Town town : townList) {
                dataList.add(town.getTownName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedCounty.getCountyName());
            currentLevel = LEVEL_TOWN;
        } else {
            queryFormServer(selectedCounty.getCountyCode(),"town");
        }
    }
    /**
     * 查询选中镇的所有村，优先从数据库上查询，查询不到再去服务器查询
     */
    private void queryVillages() {
        villageList = chinaPlaceDB.loadVillage(selectedTown.getId());
        if (villageList.size() > 0) {
            dataList.clear();
            for (Village village : villageList) {
                dataList.add(village.getVillageName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedTown.getTownName());
            currentLevel = LEVEL_VILLAGE;
        } else {
            queryFormServer(selectedTown.getTownCode(),"village");
        }
    }

    /**
     * 根据传入的代号和类型从服务器上读取省市县镇村数据
     */
    private void queryFormServer(final String code,final String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://areadata.api.juhe.cn/AreaHandler.ashx?key=1ed9a92f4c9b1d47da927c0bd2d481b4&action=getArea&areaID=" + code;
        } else {
            address = "http://areadata.api.juhe.cn/AreaHandler.ashx?key=1ed9a92f4c9b1d47da927c0bd2d481b4&action=getArea&areaID=";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponse(chinaPlaceDB, response);
                } else if ("city".equals(type)) {
                    result = Utility.handleCityResponse(chinaPlaceDB, response, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountyResponse(chinaPlaceDB, response, selectedCity.getId());
                } else if ("town".equals(type)) {
                    result = Utility.handleTownResponse(chinaPlaceDB, response, selectedCounty.getId());
                } else if ("village".equals(type)) {
                    result = Utility.handleVillageResponse(chinaPlaceDB, response, selectedTown.getId());
                }
                if (result) {
                    //通过runOnUiThread()方法回到主线程处理逻辑
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            } else if ("town".equals(type)) {
                                queryTowns();
                            } else if ("village".equals(type)) {
                                queryVillages();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }
    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
    /**
     * 捕获Back按键,根据当前级别判断，此时返回列表省 市 县 镇 还是退出
     */
    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_VILLAGE) {
            queryTowns();
        } else if (currentLevel == LEVEL_TOWN) {
            queryCounties();
        } else if (currentLevel == LEVEL_COUNTY) {
            queryCities();
        } else if (currentLevel == LEVEL_CITY) {
            queryProvinces();
        } else {
            finish();
        }
    }
}
