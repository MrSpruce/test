package com.guantao.redbird.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.guantao.redbird.myapplication.PortSettingActivity;
import com.guantao.redbird.myapplication.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 系统设置
 */
@ContentView(R.layout.fragment_system_manage)
public class SystemManageFragment extends BaseFragment{

    @ViewInject(R.id.bt_apply_setting)
    private Button bt_apply_setting;   //上报设置
    @ViewInject(R.id.bt_update_pwd)
    private Button bt_update_pwd;   //修改密码
    @ViewInject(R.id.bt_port_setting)
    private Button bt_port_setting;   //端口设置
    private String mUserId;

    public SystemManageFragment() {
        ;
    }

    /**
     * 实例化fragment
     * @param userId
     * @return
     */
    public static SystemManageFragment getInstance(String userId){

        SystemManageFragment systemManageFragment = new SystemManageFragment();
        systemManageFragment.mUserId = userId;
        return systemManageFragment;
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

    @Event(value = {R.id.bt_apply_setting,R.id.bt_update_pwd,R.id.bt_port_setting})
    private void onClick(View v){
        switch (v.getId()) {
            case R.id.bt_apply_setting://上报信息设置
                //ToastUtil.showToast(context, "跳转成功！");
                //Intent map_intent = new Intent(context, ApplySetActivity.class);
                //startActivity(map_intent);
                break;
            case R.id.bt_update_pwd://修改密码
                //Intent intent = new Intent(context, UpdatePwdActivity.class);
                //startActivity(intent);
                break;
            case R.id.bt_port_setting://端口设置
                Intent intent = new Intent(context, PortSettingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
