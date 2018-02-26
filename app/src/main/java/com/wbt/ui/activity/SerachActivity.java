package com.wbt.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wbt.R;
import com.wbt.bean.DataBean;
import com.wbt.data.DataAdapter;
import com.wbt.data.SerachDaAdapter;
import com.wbt.mvp.model.data.IDataListener;
import com.wbt.mvp.presenter.DataPresenter;
import com.wbt.ui.base.BaseActivity;
import com.wbt.util.DividerItemDecoration;
import com.wbt.util.HandlerUtils;
import com.wbt.util.config.StatusConfig;
import com.wbt.util.customview.CustomProgress;
import com.wbt.util.customview.dialog.SelectDialog;

/**
 * Created by rnd on 2018/2/1.
 *
 */

public class SerachActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener,HandlerUtils.OnReceiveMessageListener,
        SerachDaAdapter.OnItemClickListener{
    private RecyclerView swarptraget;
    private SwipeToLoadLayout swipeToLoadLayout;

    private DataPresenter dataPresenter;
    private DataBean databean;
    private String ErrorMsg;

    private SerachDaAdapter serachDaAdapter;

    private int deviceid;

    private String addre;

    private HandlerUtils.HandlerHolder handlerHolder;

    private CustomProgress customProgress;

    private SelectDialog selectDialog;

    private LinearLayout back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_serach);

        initView();

        initIntentData();

        initData();

        getData();

    }

    private void initView() {
        back = findViewById(R.id.back);
        swipeToLoadLayout = findViewById(R.id.swipeToLoadLayout);
        swarptraget = findViewById(R.id.swipe_target);
        swarptraget.setLayoutManager(new LinearLayoutManager(this));

        swarptraget.setLayoutManager(new LinearLayoutManager(this));
        swarptraget.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));

        swipeToLoadLayout.setRefreshHeaderView(LayoutInflater.from(this).inflate(R.layout.layout_twitter_header, swipeToLoadLayout, false));
        /*设置上拉加载更多布局*/
        swipeToLoadLayout.setLoadMoreFooterView(LayoutInflater.from(this).inflate(R.layout.layout_classic_footer, swipeToLoadLayout, false));
        // 设置监听器
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);//上拉;
        back.setOnClickListener(this);
    }


    private void initIntentData(){
        try{
            Intent intent = getIntent();
            deviceid = intent.getIntExtra(StatusConfig.DATA_DVID_KEY,0);
            addre = intent.getStringExtra(StatusConfig.DATA_ADDRE_KEY);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initData(){
        dataPresenter = new DataPresenter();
        databean = new DataBean();
        handlerHolder = new HandlerUtils.HandlerHolder(this);
    }

    private void getData(){
        if(!addre.isEmpty()){
            dataPresenter.getData(addre,iDataListener);
        }else{
            handlerHolder.sendEmptyMessage(1);
        }
    }


    IDataListener iDataListener = new IDataListener() {
        @Override
        public void onDataSuccess(DataBean dataBean) {
            databean = dataBean;
            if(databean!=null){
                handlerHolder.sendEmptyMessage(200);
            }else{
                handlerHolder.sendEmptyMessage(1);
            }
        }

        @Override
        public void onDataFail(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(1);
        }

        @Override
        public void onDataError(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(0);
        }
    };


    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                swipeToLoadLayout.setLoadingMore(false);
            }
        },2000);
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
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
                /*if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }*/
                Toast.makeText(this,"获取数据失败:"+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 1:
                /*if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }*/
                Toast.makeText(this,"获取数据失败:"+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 200:
                /*if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }*/

                if(serachDaAdapter==null){
                    serachDaAdapter = new SerachDaAdapter(this,databean,this);
                    swarptraget.setAdapter(serachDaAdapter);
                }else{
                    serachDaAdapter.dataBean = databean;
                    serachDaAdapter.notifyDataSetChanged();
                }

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void OnItemClick(View view, int pos) {
        //Toast.makeText(this,pos+"",Toast.LENGTH_SHORT).show();
        DataBean.DatasBean datasBean = databean.getDatas().get(pos);
        final String probename = datasBean.getProbe();
        SelectDialog.Builder builder = new SelectDialog.Builder(this);

        builder.setPositiveButton(R.string.select_dialog_local, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(SerachActivity.this,SelectDateActivity.class);
                intent.putExtra(StatusConfig.DATA_DVID_KEY,deviceid);
                intent.putExtra(StatusConfig.DATA_PROBENAME_KEY,probename);
                intent.putExtra(StatusConfig.DATA_ADDRE_KEY,addre);
                intent.putExtra("titlename","Search");
                intent.putExtra("LocalAndNet",0);
                startActivity(intent);
            }
        }).setnegativeButton(R.string.select_dialog_net, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(SerachActivity.this,SelectDateActivity.class);
                intent.putExtra(StatusConfig.DATA_DVID_KEY,deviceid);
                intent.putExtra(StatusConfig.DATA_PROBENAME_KEY,probename);
                intent.putExtra(StatusConfig.DATA_ADDRE_KEY,addre);
                intent.putExtra("LocalAndNet",1);
                intent.putExtra("titlename","Search");
                startActivity(intent);
            }
        });

        selectDialog = builder.create();
        selectDialog.show();
        selectDialog.setCanceledOnTouchOutside(true);


    }
}
