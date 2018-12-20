package com.guantao.redbird.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guantao.redbird.myapplication.PortSettingActivity;
import com.guantao.redbird.myapplication.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * 系统设置
 */
@ContentView(R.layout.fragment_video_scanning)
public class VideoScaningFragment extends BaseFragment{


    private String mUserId;

    public VideoScaningFragment() {
        ;
    }

    /**
     * 实例化fragment
     * @param userId
     * @return
     */
    public static VideoScaningFragment getInstance(String userId){

        VideoScaningFragment videoScanningFragment = new VideoScaningFragment();
        videoScanningFragment.mUserId = userId;
        return videoScanningFragment;
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {

        //注入view和事件
        view = x.view().inject(this, inflater, container);

        return view;
    }

    @Override
    public void initData() {

    }




}
