package com.wbt.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wbt.R;
import com.wbt.ui.activity.MainActivity;
import com.wbt.ui.base.BaseFragment;
import com.wbt.ui.fragment.device.AddFragment;
import com.wbt.ui.fragment.device.EditFragment;
import com.wbt.util.customview.NoScrollViewPager;
import com.wbt.util.customview.PagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnd on 2018/1/30.
 *
 */

public class AddDeviceFragment extends BaseFragment{
    private MainActivity ma;
    private TabLayout mTabLayout;
    private NoScrollViewPager mViewPager;

    private List<String> mTitleList = new ArrayList<>();//页卡标题集合

    private List<Fragment> mFragmentList = new ArrayList<>();//页卡视图集合

    /**
     * 这里最好该换成fragment 可以做5个
     */
    private Fragment add, edit; //页卡视图

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ma = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.add_device_fg_layout,container,false);
        initView(view);
        //initData();
        return view;
    }

    private void initView(View view){
        mViewPager = view.findViewById(R.id.discover_view);
        mTabLayout = view.findViewById(R.id.discover_tab);

        mViewPager.setNoScroll(true);

        //mViewPager.setNestedpParent((ViewGroup) mViewPager.getParent());

        add = new AddFragment();
        edit = new EditFragment();

        mFragmentList.add(edit);
        mFragmentList.add(add);

        //添加页卡标题
        mTitleList.add("Edit Device");
        mTitleList.add("Add Device");

        PagerFragment mAdapter = new PagerFragment(ma.getSupportFragmentManager(),mFragmentList,mTitleList);
        //myFragmentPagerAdapter = new MyFragmentPagerAdapter(ma.getSupportFragmentManager(),mViewList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        //mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
        mTabLayout.setupWithViewPager(mViewPager);
        //设置viewpager的缓存页面的个数
        /**
         * 完美解决重复加载的问题
         * 添加以下2段代码即可
         */
        mViewPager.setOffscreenPageLimit(6);
        mViewPager.setAdapter(mAdapter);
    }
}
