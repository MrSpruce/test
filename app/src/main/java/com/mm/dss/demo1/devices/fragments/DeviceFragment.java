/*
 *
 *  * Copyright (c) 1992-2016, ZheJiang Dahua Technology Stock CO.LTD.
 *  * The DAHUA LECHANGE Robot X Project.
 *  * All Rights Reserved.
 *
 */

package com.mm.dss.demo1.devices.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.business.adapter.DataAdapteeImpl;
import com.android.business.adapter.DataAdapterInterface;
import com.android.business.adapter.DeviceWithChannelList;
import com.android.business.adapter.DeviceWithChannelListBean;
import com.android.business.common.BroadCase;
import com.android.business.entity.ChannelInfo;
import com.android.business.entity.DataInfo;
import com.android.business.entity.DeviceInfo;
import com.android.business.entity.GroupInfo;
import com.android.business.entity.LogicalInfo;
import com.android.business.exception.BusinessException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mm.dss.demo1.PlayOnlineActivity;
import com.mm.dss.demo1.R;
import com.mm.dss.demo1.base.BaseFragment;
import com.mm.dss.demo1.devices.adapter.DeviceChannelAdapter;
import com.mm.dss.demo1.devices.adapter.DeviceTreeAdapter;
import com.mm.dss.demo1.group.ChannelFactory;
import com.mm.dss.demo1.group.GroupFactory;
import com.mm.dss.demo1.group.GroupHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeviceFragment extends BaseFragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "26499" + DeviceFragment.class.getSimpleName();
    private String [] group={"路桥乡","房寨镇","王桥乡","寿山寺乡","城关镇","柴堡镇","徐村乡","魏僧寨镇"};
    private LinearLayout llDeviceTitle;
    private TextView ivDeviceManage;
    private List<String> townGroup=new ArrayList<>();
    //private DeviceListSelectPopwindow deviceListSelectPopwindow;
   // private PullToRefreshListView prlvChannelList;
    private ListView lvChannelList;
    private ExpandableListView expandableListView;
    private GroupInfo groupInfo = null;
    private Map<String, List<String>> devIdMap = new HashMap<>();
    private Map<String, List<String>> chnIdMap = new HashMap<>();
    //各镇摄像头信息
    private List<ChannelInfo> channelInfos = new ArrayList<>();
    private List<ChannelInfo> lqxchannelInfo=new ArrayList<>();  //路桥乡
    private List<ChannelInfo> fzhzchannelInfo=new ArrayList<>(); //房寨镇
    private List<ChannelInfo> wqxchannelInfo=new ArrayList<>();  //王桥乡
    private List<ChannelInfo> sshsxchannelInfo=new ArrayList<>();//寿山寺乡
    private List<ChannelInfo> cgzchannelInfo=new ArrayList<>();  //城关镇
    private List<ChannelInfo> chbzchannelInfo=new ArrayList<>(); //柴堡镇
    private List<ChannelInfo> xcxchannelInfo=new ArrayList<>();  //徐乡村
    private List<ChannelInfo> wszhzchannelInfo=new ArrayList<>();//魏僧寨镇
    //摄像头组
    private List<List<ChannelInfo>>deviceList=new ArrayList<>();
    private RelativeLayout rlDeviceNull;
    //private DeviceChannelAdapter mDeviceChannelAdapter;
    private DeviceTreeAdapter deviceTreeAdapter;
    private boolean isFirstVisible = true;
    private DataAdapterInterface dataAdapterInterface;
    private DeviceBroadcastReceiver mReceiver;
    protected Handler mDeviceHander = new Handler() {
        public void handleMessage(Message msg) {
            if(!isAdded()) return;
            switch (msg.what){
                case 0:
                    if(getActivity() == null) return;
                    //if(prlvChannelList != null) prlvChannelList.onRefreshComplete();
                    dissmissProgressDialog();
                    List<DataInfo> infos = (List<DataInfo>) msg.obj;
//                    if(infos != null && infos.size() > 0) channelInfos.addAll(infos);
                    refreshChannelListData(false);
                    break;
                case 1:
                    if(getActivity() == null) return;
                    toast(R.string.device_get_failed);
                    //if(prlvChannelList != null) prlvChannelList.onRefreshComplete();
                    dissmissProgressDialog();
                    refreshChannelListData(true);
                    break;
                case 2:
                    GroupInfo info = (GroupInfo) msg.obj;
                    GroupHelper.getInstance().checkNullGroup();
                    getGroupDeviceList(info);
                    break;
            }
        }
    };



    class DeviceBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch(intent.getAction()){
                case BroadCase.Action.DEVICE_ACTION_PUSH_MODIFY_DEVICE:
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = GroupFactory.getInstance().getRootGroupInfo();
                    mDeviceHander.sendMessage(msg);
                    break;
                case BroadCase.Action.DEVICE_ACTION_PUSH_MODIFY_DEVICE_STATE:
                    String id = intent.getStringExtra("channelId");
                    int state = intent.getIntExtra("state", 1);
                    boolean ret = ChannelFactory.getInstance().setChannelInfoState(id, state);
                    if(ret) {
                        deviceTreeAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isFirstVisible){
            isFirstVisible = false;
            getGroupList();
        }
    }

    public DeviceFragment() {
        // Required empty public constructor
    }

    public static DeviceFragment newInstance(String param1, String param2) {
        DeviceFragment fragment = new DeviceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBroadcast();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device, container, false);  //
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        GroupHelper.getInstance().setContext(getActivity().getApplicationContext());
        initData();
        initEvent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // getActivity().unregisterReceiver(mReceiver);
    }

    private void initView(View view){
        initTitle(view);
        initBlankView(view);
        expandableListView=view.findViewById(R.id.device_list);
        //lvChannelList=view.findViewById(R.id.list);
        //prlvChannelList = (PullToRefreshListView) view.findViewById(R.id.deviceChannellist);
       // prlvChannelList.setMode(PullToRefreshBase.Mode.BOTH);
        //lvChannelList = prlvChannelList.getRefreshableView();
    }

    private void initTitle(View view){
        llDeviceTitle = (LinearLayout) view.findViewById(R.id.device_list_title);
        ivDeviceManage = (TextView) view.findViewById(R.id.device_title_manage);
        //initDialog();
    }

//    private void initDialog(){
//        deviceListSelectPopwindow = DeviceListSelectPopwindow.newInstance(getActivity(), new DeviceListSelectPopwindow.onDeviceListSelectPopupWindowListener() {
//            @Override
//            public void onGroupSelectedListener(GroupInfo groupInfo) {
//                getGroupDeviceList(groupInfo);
//            }
//
//            @Override
//            public void onGroupFailedListener() {
//
//            }
//        });
//    }

    private void initBlankView(View view){
        rlDeviceNull = (RelativeLayout) view.findViewById(R.id.common_blank_layout);
        TextView tvBlankBtn = (TextView) view.findViewById(R.id.common_blank_btn);
        tvBlankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGroupDeviceList(groupInfo);
            }
        });
    }

    private void initData(){
        dataAdapterInterface = DataAdapteeImpl.getInstance();
        setChannelListData();
    }

    private void registerBroadcast(){
        mReceiver = new DeviceBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCase.Action.DEVICE_ACTION_PUSH_MODIFY_DEVICE);
        filter.addAction(BroadCase.Action.DEVICE_ACTION_PUSH_MODIFY_DEVICE_STATE);
        getActivity().registerReceiver(mReceiver,filter);
    }

    private void setChannelListData(){
        //prlvChannelList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //@Override
            //public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                getGroupDeviceList(groupInfo);
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                getGroupDeviceList(groupInfo);        //获取所有设备列表
//            }
//        });
        deviceTreeAdapter=new DeviceTreeAdapter(getActivity());
        expandableListView.setAdapter(deviceTreeAdapter);
        //分组展开
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return false;
            }
        });
        //分组关闭
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {

            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                DataInfo dataInfo=deviceTreeAdapter.getChild(i,i1);
                ChannelInfo channelInfo = null;
                if(dataInfo instanceof ChannelInfo) {
                    channelInfo = (ChannelInfo) dataInfo;
                } else if(dataInfo instanceof LogicalInfo) {
                    channelInfo = (ChannelInfo) ((LogicalInfo) dataInfo).getDataInfo();
                }
                if(channelInfo.getState() == ChannelInfo.ChannelState.Online){
                    List<ChannelInfo> channelInfos = new ArrayList<>();
                    channelInfos.add(channelInfo);
                    Intent intent = new Intent(getActivity(), PlayOnlineActivity.class);
                    intent.putExtra("channel_info_list", (Serializable) channelInfos);
                    startActivity(intent);

                }else{
                    toast(R.string.device_channel_null);
                }

               return false;
            }
        });
        //mDeviceChannelAdapter = new DeviceChannelAdapter(getActivity());//实例化适配器
        //lvChannelList.setAdapter(mDeviceChannelAdapter);  //设置适配器    listview
//        lvChannelList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //加个监听
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "channel onItemClick position: " + position);
//                DataInfo dataInfo = mDeviceChannelAdapter.getItem(position - 1);
//                ChannelInfo channelInfo = null;
//                if(dataInfo instanceof ChannelInfo) {
//                    channelInfo = (ChannelInfo) dataInfo;
//                } else if(dataInfo instanceof LogicalInfo) {
//                    channelInfo = (ChannelInfo) ((LogicalInfo) dataInfo).getDataInfo();
//                }
//                if(channelInfo.getState() == ChannelInfo.ChannelState.Online){
//                    List<ChannelInfo> channelInfos = new ArrayList<>();
//                    channelInfos.add(channelInfo);
//                    Intent intent = new Intent(getActivity(), PlayOnlineActivity.class);
//                    intent.putExtra("channel_info_list", (Serializable) channelInfos);
//                    startActivity(intent);
//                }else{
//                    toast(R.string.device_channel_null);
//                }
//            }
//        });
    }

    private void getGroupList(){
        //showProgressDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
//                    GroupInfo groupInfo = dataAdapterInterface.queryGroupInfo(null);
//                    List<GroupInfo> groupInfos = dataAdapterInterface.queryGroup(null);//groupInfo == null ? null : groupInfo.getGroupId());
//                    if(groupInfos != null && groupInfos.size() > 0){
//                        GroupInfo firstInfo = groupInfos.get(0);
//                        if(!TextUtils.equals(groupInfo.getGroupId(), firstInfo.getGroupId()) && TextUtils.isEmpty(firstInfo.getGroupParentId())){
//                            groupInfo = firstInfo;
//                        }
//                    }
//
//                    for(GroupInfo info : groupInfos) {
//                        if (info.getDevList() != null) {
//                            //此处需要注意，每个组织下可能还有子组织，按照自身的平台（每个平台组织不一样）进行递归查询各个子组织，获取所有组织下对应的设备通道
//                                // to note here, next to each organization may sub-organizations, according to its own platform (each platform organize different) recursive query each organization, access to all equipment under the corresponding channel
//                            devIdMap.put(info.getGroupId(),info.getDevList());
//                        }
//                        if(info.getChannelList() != null) {
//                            chnIdMap.put(info.getGroupId(), info.getChannelList());
//                        }
//                    }
                GroupHelper.getInstance().LoadAllGroup();   //

            }
        }).start();
    }

    private void getGroupDeviceList(final GroupInfo groupInfo){
        this.groupInfo = groupInfo;
        channelInfos.clear();
        dissmissProgressDialog();
        showProgressDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
//                channelInfos = GroupHelper.getInstance().getChannelInfoList(groupInfo);
                channelInfos = ChannelFactory.getInstance().getAllChannelInfo(groupInfo);
                for (ChannelInfo ch:channelInfos) {
                    if (ch.getName().contains("路桥乡")){
                        lqxchannelInfo.add(ch);
                    }
                    if (ch.getName().contains("房寨镇")){
                        fzhzchannelInfo.add(ch);
                    }
                    if (ch.getName().contains("王桥乡")){
                        wqxchannelInfo.add(ch);
                    }
                    if (ch.getName().contains("寿山寺乡")){
                        sshsxchannelInfo.add(ch);
                    }
                    if (ch.getName().contains("城关镇")){
                        cgzchannelInfo.add(ch);
                    }
                    if (ch.getName().contains("柴堡镇")){
                        chbzchannelInfo.add(ch);
                    }
                    if (ch.getName().contains("徐村乡")){
                        xcxchannelInfo.add(ch);
                    }
                    if (ch.getName().contains("魏僧寨镇")){
                        wszhzchannelInfo.add(ch);
                    }
                }
                deviceList.add(lqxchannelInfo);
                deviceList.add(fzhzchannelInfo);
                deviceList.add(wqxchannelInfo);
                deviceList.add(sshsxchannelInfo);
                deviceList.add(cgzchannelInfo);
                deviceList.add(chbzchannelInfo);
                deviceList.add(xcxchannelInfo);
                deviceList.add(wszhzchannelInfo);
                for (String s:group) {
                    townGroup.add(s);
                }
//                channelInfos = ChannelFactory.getInstance().getChannelInfoListById(groupInfo.getChannelList());
                if(channelInfos != null && channelInfos.size() > 0) {
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = channelInfos;
                    mDeviceHander.sendMessage(msg);
                    //checkChannelState(channelInfos);
                } else {
                    mDeviceHander.sendEmptyMessage(1);
                }
            }
        }).start();
    }

//    private void checkChannelState(List<ChannelInfo> channelInfos) {
//        DeviceWithChannelList deviceWithChannelList = GroupHelper.getInstance().getDeviceWithChannelList(channelInfos);
//        if(deviceWithChannelList != null) {
//            if(deviceWithChannelList.getDevWithChannelList() != null) {
//                for(DeviceWithChannelListBean deviceWithChannelListBean : deviceWithChannelList.getDevWithChannelList()){
//                    DeviceInfo deviceInfo = deviceWithChannelListBean.getDeviceInfo();
//
//                    if (deviceWithChannelListBean.getChannelList() != null && deviceWithChannelListBean.getChannelList().size() > 0) {
//                        ChannelInfo channel = deviceWithChannelListBean.getChannelList().get(deviceWithChannelListBean.getChannelList().size() - 1);
//                        try {
//                            dataAdapterInterface.queryChannelStatus(deviceInfo.getSnCode(), channel.getIndex() + 1);
//                        } catch (BusinessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//    }

    private void refreshChannelListData(boolean isInit){
        if(deviceTreeAdapter != null){
            deviceTreeAdapter.setListData(deviceList,townGroup);
            deviceTreeAdapter.notifyDataSetChanged();
        }
        if (isInit) return;
        if(channelInfos == null || channelInfos.size() == 0){
            rlDeviceNull.setVisibility(View.VISIBLE);
           // prlvChannelList.setVisibility(View.INVISIBLE);
        }else{
            rlDeviceNull.setVisibility(View.INVISIBLE);
            //prlvChannelList.setVisibility(View.VISIBLE);
        }
    }

    private void initEvent(){
        ivDeviceManage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        if(v.getId() == R.id.device_title_manage) {
//            if(deviceListSelectPopwindow == null){
//                initDialog();
//            }
//            if(deviceListSelectPopwindow.isShowing()){
//                deviceListSelectPopwindow.dismiss();
//            }else{
//                deviceListSelectPopwindow.refreshData();
//                deviceListSelectPopwindow.showAsDropDown(llDeviceTitle, 0, 0);
//            }
//        }
    }
}


