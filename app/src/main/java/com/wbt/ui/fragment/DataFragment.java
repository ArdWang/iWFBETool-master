package com.wbt.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wbt.R;
import com.wbt.bean.TempBean;
import com.wbt.bean.Temperature;
import com.wbt.data.TempAdapter;
import com.wbt.mvp.model.temp.ITempListener;
import com.wbt.mvp.presenter.TempPresenter;
import com.wbt.ui.activity.MainActivity;
import com.wbt.ui.base.BaseFragment;
import com.wbt.util.DBManager;
import com.wbt.util.HandlerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 *
 */

public class DataFragment extends BaseFragment implements HandlerUtils.OnReceiveMessageListener{
    private MainActivity ma;
    private RecyclerView datarecyclerview;
    private SwipeRefreshLayout dataswipeRefresh;
    private TempPresenter tempPresenter;

    //private Handler timeHandler;

    private HandlerUtils.HandlerHolder handlerHolder;

    private List<TempBean.TempsBean> templist;

    private String ErrorMsg;

    private TempAdapter tempAdapter;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ma = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.data_fg_layout,container,false);
        initView(view);
        initData();
        return view;

    }

    private void initView(View view) {
        datarecyclerview = view.findViewById(R.id.data_recylerview);
        dataswipeRefresh = view.findViewById(R.id.data_swipe_refresh);

        datarecyclerview.setLayoutManager(new LinearLayoutManager(ma));
        //msgrecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),
                //DividerItemDecoration.VERTICAL_LIST));
        /**
         * 下拉刷新
         */
        dataswipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refreshMessage();
                getData();
                dataswipeRefresh.setRefreshing(false);
            }
        });

    }

    private void initData(){
        tempPresenter = new TempPresenter();
        handlerHolder = new HandlerUtils.HandlerHolder(this);
        templist = new ArrayList<>();
        // = new Handler();
        handlerHolder.post(runnable);


    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getData();
            handlerHolder.postDelayed(runnable,15000);
        }
    };

    private void getData(){
        tempPresenter.getTemp("5c:cf:7f:d0:85:65",iTempListener);
    }


    ITempListener iTempListener = new ITempListener() {
        @Override
        public void onTempSuccess(List<TempBean.TempsBean> list) {
            templist = list;
            if(templist.size()>0){
                handlerHolder.sendEmptyMessage(200);
            }
        }

        @Override
        public void onTempFail(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(1);
        }

        @Override
        public void onTempError(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(0);
        }
    };


    @Override
    public void handlerMessage(Message msg) {
        switch (msg.what){
            case 0:
                Toast.makeText(getActivity(),"获取数据失败："+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 1:
                Toast.makeText(getActivity(),"获取数据失败："+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 200:
                //存入数据到数据库
                for(TempBean.TempsBean tempsBean:templist){
                    Temperature temp = new Temperature();
                    temp.setTpname(tempsBean.getTpname());
                    temp.setTpdvid(tempsBean.getTpdvid());
                    temp.setTptemp(tempsBean.getTptemp());
                    temp.setTpsymbol(tempsBean.getTpsymbol());
                    temp.setTphum(tempsBean.getTphum());
                    temp.setTptime(tempsBean.getTptime());
                    DBManager.getInstance(ma).insertTemp(temp);
                }

                //执行查询
                if(tempAdapter==null){
                    tempAdapter = new TempAdapter(ma,templist);
                    datarecyclerview.setAdapter(tempAdapter);
                }else{
                    tempAdapter.tempList=templist;
                    tempAdapter.notifyDataSetChanged();
                }


                break;
        }
    }




}
