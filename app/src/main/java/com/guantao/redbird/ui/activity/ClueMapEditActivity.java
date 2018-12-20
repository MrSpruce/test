package com.guantao.redbird.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.model.LatLng;
import com.guantao.redbird.myapplication.MainActivity;
import com.guantao.redbird.myapplication.R;
import com.guantao.redbird.utils.MapUtils;
import com.guantao.redbird.utils.ToastUtil;

import org.xutils.DbManager;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;


public class ClueMapEditActivity extends Activity {

    @ViewInject(R.id.baiduMap)
    private MapView baiduMapView;
    @ViewInject(R.id.ibt_map_dingwei)
    private ImageButton ibt_map_dingwei;
    @ViewInject(R.id.ibt_map_big)
    private ImageButton ibt_map_big;
    @ViewInject(R.id.ibt_map_small)
    private ImageButton ibt_map_small;
    @ViewInject(R.id.ibt_map_save)
    private ImageButton ibt_map_save;
    @ViewInject(R.id.ibt_map_qiehuan)
    private ImageButton ibt_map_qiehuan;
    @ViewInject(R.id.ibt_confirm)
    private ImageButton ibt_confirm;
    @ViewInject(R.id.ibt_map_bi)
    private ImageButton ibt_map_bi;
    @ViewInject(R.id.ibt_back)
    private ImageButton ibt_back;

    private Context mContext;
    private BaiduMap mBaiduMap;
    private String blockId;
    private String userId;
    private String blockType;
    private String block_boundary_coordinate;
    private DbManager db;
    private BitmapDescriptor marker_icon;
    private ArrayList<LatLng> boundaryList;
    private ArrayList<Marker> markerList;
    private ArrayList<Polyline> lineList;

    private LatLng ll;
    private ArrayList<LatLng> latLngArrayList;
    private ArrayList<Marker> overlayArrayList;
    private ArrayList<LatLng> linePointsArrayList;
    private ArrayList<Polyline> lineArrayList;
    private MarkerOptions options;

    private Overlay surfaceOverlay;
    private String boundary;
    private boolean isWeiXing = false;
    private boolean isSave = false;
    private boolean isEdit = false;
    private String area="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue_map_edit);

        //注入view和事件
        x.view().inject(this);

        mContext = this;

        Bundle bundle = getIntent().getExtras();
        //blockId = bundle.getString("blockId");
        // userId = bundle.getString("userId");
        //blockType = bundle.getString("blockType");
        //block_boundary_coordinate = bundle.getString("block_boundary_coordinate");

        //获取数据库


        mBaiduMap = baiduMapView.getMap();

        //隐藏缩放按钮
        baiduMapView.showZoomControls(false);

        MapUtils.getLocation(mContext, baiduMapView, mBaiduMap);

        //设置marker图标
        marker_icon = BitmapDescriptorFactory.fromResource(R.drawable.img_dian);

        boundaryList = new ArrayList<LatLng>();
        markerList = new ArrayList<Marker>();
        lineList = new ArrayList<Polyline>();
        //如果有边界坐标，显示marker，折线，覆盖面

        //保存LatLng坐标
        latLngArrayList = new ArrayList<LatLng>();
        //保存Overlay---marker点
        overlayArrayList = new ArrayList<Marker>();
        //保存Overlay---折线
        lineArrayList = new ArrayList<Polyline>();


        //地图单击事件监听
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }
            @Override
            public void onMapClick(final LatLng latLng) {
                if(!isEdit)
                    return;
                //获取经纬度
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                // 定义Maker坐标点
                LatLng point = new LatLng(latitude, longitude);
                //添加坐标点
                latLngArrayList.add(latLng);
                //添加marker
                // 构建MarkerOption，用于在地图上添加Marker
                options = new MarkerOptions().position(point).icon(marker_icon);
                // 在地图上添加Marker，并显示
                Marker marker = (Marker) mBaiduMap.addOverlay(options);
                //保存marker到集合中
                overlayArrayList.add(marker);
                //构建折线Overlay
                if (latLngArrayList.size() > 1 && overlayArrayList.size() > 1 ) {
                    //保存折线点的坐标的集合
                    linePointsArrayList = new ArrayList<LatLng>();
                    //添加坐标
                    linePointsArrayList.add(latLngArrayList.get(latLngArrayList.size()-1));
                    linePointsArrayList.add(latLngArrayList.get(latLngArrayList.size()-2));
                    //创建折线Overlay
                    OverlayOptions ooPolyline = new PolylineOptions().width(8)
                            .color(Color.RED).points(linePointsArrayList);
                    //添加在地图中
                    Polyline  mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);

                    //将折线Overlay保存到集合中
                    lineArrayList.add(mPolyline);
                }
            }
        });

        mBaiduMap.setOnMapDoubleClickListener(new BaiduMap.OnMapDoubleClickListener() {
            @Override
            public void onMapDoubleClick(LatLng latLng) {
                if(isEdit){
                    isEdit=!isEdit;


                    int Count = latLngArrayList.size();
                    //最少3个点
                    if (Count  < 3) {
                        return;
                    }

                    //构建用户绘制多边形的Option对象
                    OverlayOptions polygonOption = new PolygonOptions()
                            .points(latLngArrayList)
                            .stroke(new Stroke(5, 0xAA00FF00))
                            .fillColor(0xAAFFFF00);

                    //在地图上添加多边形Option，用于显示
                    mBaiduMap.addOverlay(polygonOption);

                    area = MapUtils.getArea(latLngArrayList);
                    //在地图上添加文字
                    OverlayOptions ooText = new TextOptions().bgColor(0xAAFFFF00)
                            .fontSize(48).fontColor(0xFFFF00FF).text("面积：" + area + " m²")
                            .position(latLngArrayList.get(0));
                    mBaiduMap.addOverlay(ooText);
                }
            }
        });
    }

    /**
     * 点击事件
     * @param v
     */
    @Event(value = {R.id.ibt_map_dingwei,R.id.ibt_map_big,R.id.ibt_map_small,R.id.ibt_map_save,R.id.ibt_map_qiehuan,
            R.id.ibt_map_bi,R.id.ibt_confirm,R.id.ibt_back,R.id.bt_drawMap_add,R.id.bt_drawMap_qingchu,
            R.id.bt_drawMap_backup,R.id.bt_drawMap_baocun})
    private void onClick(View v){
        switch (v.getId()) {
            case R.id.ibt_map_dingwei://定位
                MapUtils.getLocation(mContext, baiduMapView, mBaiduMap);
                break;
            case R.id.ibt_map_big://放大一级地图
                MapStatusUpdate zoomIn = MapStatusUpdateFactory.zoomIn();
                mBaiduMap.setMapStatus(zoomIn);
                break;
            case R.id.ibt_map_small://缩小一级地图
                MapStatusUpdate zoomOut = MapStatusUpdateFactory.zoomOut();
                mBaiduMap.setMapStatus(zoomOut);
                break;
            case R.id.bt_drawMap_baocun :
                if(area.trim().isEmpty()|| latLngArrayList.size()<3)
                {
                    ToastUtil.showToast(mContext, "无统计结果");
                    break;
                }
                //将计算的面积和坐标点回传
                Intent intent = new Intent();
                Bundle resultBundle = new Bundle();
                resultBundle.putString("area", area);//面积
                resultBundle.putSerializable("arrayListLatLng", latLngArrayList);
                intent.putExtras(resultBundle);
                setResult(1, intent);
                finish();
                /*intent.putExtras(bundle);
                ClueMapEditActivity.this.setResult(10, intent);
                ClueMapEditActivity.this.finish();*/
                break;
            case R.id.ibt_map_save://保存--移除marker点和折线

                if (markerList != null && markerList.size() > 0) {

                    for (Marker marker : markerList) {
                        marker.setDraggable(false);
                        marker.remove();
                    }
                }
                if (lineList != null && lineList.size() > 0) {

                    for (Polyline polyline : lineList) {
                        polyline.remove();
                    }
                }
//			boundaryList.clear();
                markerList.clear();
                lineList.clear();
                isSave = true;
                break;
            case R.id.ibt_map_qiehuan://切换
                if (isWeiXing) {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                }else {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                }
                isWeiXing = isWeiXing ? false : true ;
                break;

            case R.id.ibt_map_bi://笔--再次开始编辑
                isEdit = isEdit?false:true;
                break;
            case R.id.bt_drawMap_add:  //进行编辑-添加边界节点
                //isEdit = isEdit?false:true
                if(isEdit)
                {
                    addNewPoint();
                }
                break;
            case R.id.bt_drawMap_backup://撤销最近的地图节点
                if (!isEdit)
                    return;
                //撤销marker
                //
                if (overlayArrayList.size() > 0) {
                    overlayArrayList.get(overlayArrayList.size()-1).remove();
                    overlayArrayList.remove(overlayArrayList.size()-1);
                    latLngArrayList.remove(latLngArrayList.size()-1);
                }else {
                    Toast.makeText(mContext, "已无marker点..", Toast.LENGTH_SHORT).show();
                }
                //撤销折线
                if (lineArrayList.size() > 0 ) {

                    lineArrayList.get(lineArrayList.size()-1).remove();
                    lineArrayList.remove(lineArrayList.size()-1);
                }else {
                    Toast.makeText(mContext, "已无折线..", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_drawMap_qingchu:
                clearAll();

                break;
            case R.id.ibt_confirm://确定--确定无误后，将修改的边界保存到服务器和本地

                if ((markerList != null && markerList.size() > 0) || (lineList != null && lineList.size() > 0) ) {

                    ToastUtil.showToast(mContext, "请先保存当前编辑");

                }else {
                    if (boundaryList != null && boundaryList.size() > 0) {
                        StringBuilder boundaryStringBuilder = new StringBuilder();
                        for (LatLng latLng : boundaryList) {

                            boundaryStringBuilder.append(latLng.latitude+","+latLng.longitude+"-");
                        }
                        boundary = boundaryStringBuilder.toString().substring(0, boundaryStringBuilder.length()-1);
                        //保存
                        //updateBoundary();

                        //跳转界面
                        Intent resultIntent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("boundary", boundary);
                        resultIntent.putExtras(bundle);

                        setResult(RESULT_OK, resultIntent);
//					BlockMapEditActivity.this.setResult(1, resultIntent);

                        finish();
                    }

                }

                break;
            case R.id.ibt_back://返回

                finish();

                break;
            default:
                break;
        }
    }

    /**
     * 添加点
     */
    private void addNewPoint(){
        LatLng latLng = MainActivity.locationLatlng;
        //获取经纬度
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        // 定义Maker坐标点
        LatLng point = new LatLng(latitude, longitude);
        //添加坐标点
        latLngArrayList.add(latLng);
        //添加marker
        // 构建MarkerOption，用于在地图上添加Marker
        options = new MarkerOptions().position(point).icon(marker_icon);
        // 在地图上添加Marker，并显示
        Marker marker = (Marker) mBaiduMap.addOverlay(options);
        //保存marker到集合中
        overlayArrayList.add(marker);
        //构建折线Overlay
        if (latLngArrayList.size() > 1 && overlayArrayList.size() > 1 ) {
            //保存折线点的坐标的集合
            linePointsArrayList = new ArrayList<LatLng>();
            //添加坐标
            linePointsArrayList.add(latLngArrayList.get(latLngArrayList.size()-1));
            linePointsArrayList.add(latLngArrayList.get(latLngArrayList.size()-2));
            //创建折线Overlay
            OverlayOptions ooPolyline = new PolylineOptions().width(8)
                    .color(Color.RED).points(linePointsArrayList);
            //添加在地图中
            Polyline  mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);

            //将折线Overlay保存到集合中
            lineArrayList.add(mPolyline);
        }

    }

    /**
     * 点击画笔，显示marker点和折线
     */
    private void showEditBoundary() {

        if (boundaryList != null) {

            isSave = false;

            //marker
            // 构建MarkerOption，用于在地图上添加Marker
            for (LatLng latLng : boundaryList) {

                MarkerOptions options = new MarkerOptions().position(latLng).draggable(true).icon(marker_icon);
                // 在地图上添加Marker，并显示
                Marker marker = (Marker) mBaiduMap.addOverlay(options);
                markerList.add(marker);
            }
            //折线
            if (boundaryList.size() > 1) {

                //创建折线Overlay
                OverlayOptions ooPolyline = new PolylineOptions().width(8)
                        .color(Color.RED).points(boundaryList);
                //添加在地图中
                Polyline polyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
                lineList.add(polyline);
            }
        }
    }

    /**
     * 移除当前的marker点，折线，面
     */
    private void clearAll() {
        //去除marker和折线
        if (overlayArrayList != null && overlayArrayList.size() != 0) {

            for (Marker marker : overlayArrayList) {
                marker.setDraggable(false);
                marker.remove();
            }
        }
        if (lineArrayList != null && lineArrayList.size() != 0) {

            for (Polyline polyline : lineArrayList) {
                polyline.remove();
            }
        }
        //保存完后清空集合数据
        latLngArrayList.clear();
        overlayArrayList.clear();//marker点集合
        lineArrayList.clear();//折线集合
        mBaiduMap.clear();
    }



    /**
     * 移动marker点的处理
     * @param marker
     */
    private void markerDrag(Marker marker) {
        LatLng position = marker.getPosition();
        boundaryList.set(markerList.indexOf(marker), position);
        markerList.set(markerList.indexOf(marker), marker);

        //撤销折线
        if (lineList != null && lineList.size() > 0) {

            for (Polyline polyline : lineList) {
                polyline.remove();
            }
            lineList.clear();
        }
        //重画折线
        if (boundaryList != null && boundaryList.size() > 1) {

            //创建折线Overlay
            OverlayOptions ooPolyline = new PolylineOptions().width(8)
                    .color(Color.RED).points(boundaryList);
            //添加在地图中
            Polyline polyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
            lineList.add(polyline);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 退出时停止定位
       /* if (MyApplication.mLocClient != null) {
            MyApplication.mLocClient.stop();
        }*/
        // 退出时关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);

       /* if (MyApplication.mkOfflineMap != null) {
            MyApplication.mkOfflineMap.destroy();
            MyApplication.mkOfflineMap = null;
        }*/
        mBaiduMap.clear();
        baiduMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        baiduMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        baiduMapView.onPause();
    }







}
