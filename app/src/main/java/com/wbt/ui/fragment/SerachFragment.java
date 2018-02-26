package com.wbt.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wbt.R;
import com.wbt.bean.DeviceBean;
import com.wbt.data.SerachAdapter;
import com.wbt.mvp.model.device.IDeviceListener;
import com.wbt.mvp.presenter.DevicePresenter;
import com.wbt.ui.activity.DataActivity;
import com.wbt.ui.activity.MainActivity;
import com.wbt.ui.activity.SerachActivity;
import com.wbt.ui.base.BaseFragment;
import com.wbt.util.DividerItemDecoration;
import com.wbt.util.HandlerUtils;
import com.wbt.util.config.StatusConfig;
import com.wbt.util.customview.CustomProgress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 *
 */

public class SerachFragment extends BaseFragment implements HandlerUtils.OnReceiveMessageListener,
        SerachAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {
    private MainActivity ma;
    private DevicePresenter devicePresenter;
    private List<DeviceBean.DevicesBean> deviceslist;
    private HandlerUtils.HandlerHolder handlerHolder;
    private String ErrorMsg;
    private RecyclerView swarptraget;
    private SwipeToLoadLayout swipeToLoadLayout;
    private SerachAdapter serachAdapter;
    private CustomProgress customProgress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ma = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.serach_fg_layout,container,false);
        initView(view);
        initData();

        if(customProgress==null){
            customProgress = CustomProgress.show(getActivity(),"Loading...",true,null);
        }

        getDevice();
        return view;
    }

    private void initView(View view){
        swipeToLoadLayout = view.findViewById(R.id.swipeToLoadLayout);
        swarptraget = view.findViewById(R.id.swipe_target);
        swarptraget.setLayoutManager(new LinearLayoutManager(ma));

        swarptraget.setLayoutManager(new LinearLayoutManager(ma));
        swarptraget.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));

        swipeToLoadLayout.setRefreshHeaderView(LayoutInflater.from(ma).inflate(R.layout.layout_twitter_header, swipeToLoadLayout, false));
        /*设置上拉加载更多布局*/
        swipeToLoadLayout.setLoadMoreFooterView(LayoutInflater.from(ma).inflate(R.layout.layout_classic_footer, swipeToLoadLayout, false));
        // 设置监听器
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);//上拉;
    }

    private void getDevice(){
        if(MainActivity.userid!=null&&MainActivity.username!=null) {
            devicePresenter.getDevice(MainActivity.userid,MainActivity.username, iDeviceListener);
        }else{
            handlerHolder.sendEmptyMessage(2);
        }
    }

    private void initData(){
        devicePresenter = new DevicePresenter();
        deviceslist = new ArrayList<>();
        handlerHolder = new HandlerUtils.HandlerHolder(this);
    }

    IDeviceListener iDeviceListener = new IDeviceListener() {
        @Override
        public void onDeviceSuccess(List<DeviceBean.DevicesBean> devicesList) {
            deviceslist = devicesList;
            if(devicesList.size()>0){
                handlerHolder.sendEmptyMessage(200);
            }
        }

        @Override
        public void onDeviceFail(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(1);
        }

        @Override
        public void onDeviceError(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(0);
        }
    };


    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDevice();
                swipeToLoadLayout.setLoadingMore(false);
            }
        },2000);
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDevice();
                swipeToLoadLayout.setRefreshing(false);
                //mAdapter.add("Load More" + mLoadMoreNum);
                //mLoadMoreNum++;
            }
        }, 2000);
    }

    @Override
    public void handlerMessage(Message msg) {
        switch (msg.what){
            case 0:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(getActivity(),"获取数据失败:"+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 1:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }

                Toast.makeText(getActivity(),"获取数据失败："+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 2:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }

                Toast.makeText(getActivity(),"获取数据失败",Toast.LENGTH_SHORT).show();
                break;

            case 200:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }

                if(serachAdapter==null){
                    serachAdapter = new SerachAdapter(getActivity(),deviceslist,this);
                    swarptraget.setAdapter(serachAdapter);
                }else{
                    serachAdapter.devicesList = deviceslist;
                    serachAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void OnItemClick(View view, int pos) {
        //Toast.makeText(getActivity(),"666666",Toast.LENGTH_SHORT).show();
        DeviceBean.DevicesBean devicesBean = deviceslist.get(pos);
        Intent intent = new Intent(ma, SerachActivity.class);
        intent.putExtra(StatusConfig.DATA_DVID_KEY,devicesBean.getDeviceid());
        intent.putExtra(StatusConfig.DATA_ADDRE_KEY,devicesBean.getDeviceaddre());
        startActivity(intent);

    }
}
