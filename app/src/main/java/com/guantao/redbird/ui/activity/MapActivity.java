package com.guantao.redbird.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.model.LatLng;
import com.guantao.redbird.myapplication.MainActivity;
import com.guantao.redbird.myapplication.R;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends Activity {


    @ViewInject(R.id.baiduMap)
    private MapView baiduMapView;
    @ViewInject(R.id.ibt_map_dingwei)
    private ImageButton ibt_map_dingwei;
    @ViewInject(R.id.ibt_map_qiehuan)
    private ImageButton ibt_map_qiehuan;
    @ViewInject(R.id.ibt_map_big)
    private ImageButton ibt_map_big;
    @ViewInject(R.id.ibt_map_small)
    private ImageButton ibt_map_small;
    @ViewInject(R.id.ibt_map_bi)
    private ImageButton ibt_map_bi;
    @ViewInject(R.id.bt_previous)
    private Button bt_previous;
    @ViewInject(R.id.bt_map_edit)
    private Button bt_map_edit;
    @ViewInject(R.id.bt_sure)
    private Button bt_sure;
    @ViewInject(R.id.ibt_back)
    private ImageButton ibt_back;
    @ViewInject(R.id.rl_bottom)
    private RelativeLayout rl_bottom;//底部栏
    @ViewInject(R.id.rl_drawMap_bottom)
    private RelativeLayout rl_drawMap_bottom;//底部画图栏
    @ViewInject(R.id.bt_drawMap_add)
    private Button bt_drawMap_add;//添加边界节点
    @ViewInject(R.id.bt_drawMap_backup)
    private Button bt_drawMap_backup;//撤销最近的地图节点
    @ViewInject(R.id.bt_drawMap_qingchu)
    private Button bt_drawMap_qingchu;//清除当前的边界
    @ViewInject(R.id.bt_drawMap_baocun)
    private Button bt_drawMap_baocun;//保存当前的边界

    private Context mContext;
    private DbManager db;
    private BaiduMap mBaiduMap;

    private LatLng ll;
    private ArrayList<LatLng> latLngArrayList;
    private ArrayList<Marker> overlayArrayList;
    private ArrayList<LatLng> linePointsArrayList;
    private ArrayList<Polyline> lineArrayList;
    private MarkerOptions options;
    private BitmapDescriptor marker_icon;
    private String blockId;
    private String userId;
    private String blockType;
    private String block_boundary_coordinate;
    //	private String boundary;
    private Overlay surface_Overlay;
    private boolean isWeiXing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_map);

        //注入view和事件
        x.view().inject(this);

        mContext = this;
        initMapView(); //初始化地图





    }


    private void initMapView() {
        baiduMapView = (MapView) findViewById(R.id.baiduMap);
        mBaiduMap = baiduMapView.getMap();
        // 定位数据
        MyLocationData data = new MyLocationData.Builder()
                // 定位精度bdLocation.getRadius()
                .accuracy(MainActivity.locationDbInfo.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(MainActivity.locationDbInfo.getDirection())
                // 纬度
                .latitude(MainActivity.locationDbInfo.getLatitude())
                // 经度
                .longitude(MainActivity.locationDbInfo.getLongitude())
                // 构建
                .build();

        // 设置定位数据
        mBaiduMap.setMyLocationData(data);

        //根据给定增量缩放地图级别
        MapStatusUpdate origin_msu = MapStatusUpdateFactory.newLatLngZoom(MainActivity.locationLatlng, 18.0f);
        mBaiduMap.setMapStatus(origin_msu);
    }





}
