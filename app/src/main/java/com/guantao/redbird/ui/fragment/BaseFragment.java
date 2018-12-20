package com.guantao.redbird.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    public View view;
    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = (Context)getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = initView(inflater, container);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 加载布局
     */
    public abstract View initView(LayoutInflater inflater, ViewGroup container);
    /**
     * 填充数据
     */
    public abstract void initData();

    //设置隐藏显示fragment
    //menuVisible : 系统维护的值    true:显示,false:隐藏
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        //getView();//获取fragment对象
        if(getView() != null){
            getView().setVisibility(menuVisible?View.VISIBLE:View.GONE);
        }
    }
}
