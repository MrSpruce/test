package com.guantao.redbird.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.guantao.redbird.myapplication.R;
import com.guantao.redbird.ui.fragment.BaseFragment;
import com.guantao.redbird.ui.fragment.SystemManageFragment;
import com.guantao.redbird.ui.fragment.VideoScaningFragment;
import com.guantao.redbird.utils.ToastUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class ClueActivity extends FragmentActivity {


    @ViewInject(R.id.rg_clue_switch)
    private RadioGroup rg_clue_switch;

    @ViewInject(R.id.clue_fragment)       //
    private FrameLayout clue_fragment;

    private Context mContext;

    private int index = 0;
    private FragmentPagerAdapter fragmentPagerAdapter;
    //调查员Id
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_clue);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("UserId");

        //注入view和事件
        x.view().inject(this);

        mContext = this;


        // 2.构建fragment数据适配器以及创建fragment
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Fragment getItem(int position) {

                BaseFragment baseFragment = null;
               /* if(position==0)
                {
                    baseFragment= AddressManageFragment.getInstance(userId);
                }else */
                if (position == 0) {    //线索发现
                    //baseFragment = ClueReportFragment.getInstance(userId);
                    ToastUtil.showToast(mContext, "建设中");
                } else if (position == 1) {  //线索管理
                    //baseFragment = ClueManageFragment.getInstance(userId);
                    ToastUtil.showToast(mContext, "建设中");
                } else if (position == 2) {//系统设置
                    baseFragment = SystemManageFragment.getInstance(userId);
                } else if (position == 3) {
                    ToastUtil.showToast(mContext, "建设中");
                    //baseFragment = AddressManageFragment.getInstance(userId);
                } else if (position == 4) {
                    ToastUtil.showToast(mContext, "建设中");
                    //baseFragment = InspectionFragment.getInstance(userId);
                }else if (position == 5) {
                    ToastUtil.showToast(mContext, "建设中");
                    baseFragment = VideoScaningFragment.getInstance(userId);
                }

                return baseFragment;
            }
        };

        rg_clue_switch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_clue_report://切换列表
                        index = 0;
                        break;
                    case R.id.rb_block_showMap://切换地图
                        index = 1;
                        break;
                    case R.id.rb_address_list:
                        index = 3;
                        break;
                    case R.id.rb_system_set://系统设置
                        index = 2;
                        break;
                    case R.id.rb_signin_report:
                        index = 4;
                        break;
                    case R.id.rb_video_scan://视频观看
                        index = 5;
                        break;
                    default:
                        break;
                }
                //1.获取要替换的fragment,并设置要替换哪个控件
                Fragment fragment = (Fragment) fragmentPagerAdapter.instantiateItem(clue_fragment, index);
                //2.替换操作
                fragmentPagerAdapter.setPrimaryItem(null, 2, fragment);
                //3.更新操作
                fragmentPagerAdapter.finishUpdate(null);

            }
        });

        //默认系统设置
        rg_clue_switch.check(R.id.rb_system_set);

    }




}
