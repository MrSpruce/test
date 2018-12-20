package com.mm.dss.demo1.devices.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.business.entity.ChannelInfo;
import com.android.business.entity.DataInfo;
import com.android.business.entity.LogicalInfo;
import com.mm.dss.demo1.R;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class DeviceTreeAdapter extends BaseExpandableListAdapter {
    private Context context;
    //private List<ChannelInfo> mDataSet;
    //private String [] group={"路桥乡","房寨镇","王桥乡","寿山寺乡","城关镇","柴堡镇","徐村乡","魏僧寨镇"};
    private List<String> townGroup=new ArrayList<>();
    private List<List<ChannelInfo>> deviceList=new ArrayList<>();

    public DeviceTreeAdapter(Context context){
        this.context=context;

    }
    public void setListData(List<List<ChannelInfo>> deviceList,List<String> townGroup){
            this.townGroup=townGroup;
            this.deviceList=deviceList;
        }




    @Override
    public int getGroupCount() {
        return townGroup.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return deviceList.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return townGroup.get(i);
    }

    @Override
    public DataInfo getChild(int i, int i1) {
        return deviceList.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.group, null);
        TextView Townname=view.findViewById(R.id.town_group);
        Townname.setText(townGroup.get(i)+"("+deviceList.get(i).size()+")");
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View covertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(covertView == null){
            covertView = LayoutInflater.from(context).inflate(R.layout.fragment_channel_list_item, null);
            holder = new ViewHolder(covertView);
            covertView.setTag(holder);
        }else{
            holder = (ViewHolder)covertView.getTag();
        }
        final DataInfo dataInfo = getChild(i,i1);
        ChannelInfo info = null;
        if(dataInfo != null) {
            if(dataInfo instanceof ChannelInfo) {
                info = (ChannelInfo) dataInfo;
            } else if(dataInfo instanceof LogicalInfo){
                info = (ChannelInfo) ((LogicalInfo) dataInfo).getDataInfo();
            }
        }
        final ChannelInfo channelInfo = (ChannelInfo)dataInfo;

        if(channelInfo != null) {
            if (channelInfo.getVideoInputInfo() != null && channelInfo.getVideoInputInfo().getCameraType() == ChannelInfo.CameraType.CameraPtz) {
                holder.channelThumbnail.setImageResource(R.drawable.common_channel_icon_ptz);
            } else {
                holder.channelThumbnail.setImageResource(R.drawable.common_channel_icon_normal);
            }
            holder.channelThumbnail.setSelected(channelInfo.getState() == ChannelInfo.ChannelState.Online);
            holder.channelName.setText(channelInfo.getName());  //设置摄像头地址

//            holder.channelPlayback.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    List<ChannelInfo> channelInfos = new ArrayList<>();
//                    channelInfos.add(channelInfo);
//                    Intent intent = new Intent(context, PlayBackActivity.class);
//                    intent.putExtra("channel_info_list", (Serializable) channelInfos);
//                    context.startActivity(intent);
//                }
//            });
//            holder.channelPlayback.setSelected(true);
//            if (channelInfo.getState() == ChannelInfo.ChannelState.Online) {
//                holder.channelName.setTextColor(Color.BLACK);
//                holder.channelInfo.setTextColor(Color.GRAY);
//
//            } else {
//                holder.channelName.setTextColor(Color.GRAY);
//                holder.channelInfo.setTextColor(Color.GRAY);
//            }
        }
        return covertView;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}

class ViewHolder{
    public ImageView channelThumbnail;
    public TextView channelName;
    private TextView channelInfo;
    private TextView channelPlayback;

    public ViewHolder(View view){
        channelThumbnail = (ImageView) view.findViewById(R.id.channelListChannel);
        channelName = (TextView) view.findViewById(R.id.channelListChannelName);
        //channelInfo = (TextView) view.findViewById(R.id.channelListChannelInfo);
        //channelPlayback = (TextView) view.findViewById(R.id.channelListPlayback);
    }
}