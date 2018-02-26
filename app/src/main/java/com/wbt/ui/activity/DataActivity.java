package com.wbt.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.wbt.R;
import com.wbt.bean.DataBean;
import com.wbt.bean.DeviceBean;
import com.wbt.bean.Temperature;
import com.wbt.data.DataAdapter;
import com.wbt.mvp.model.data.IDataListener;
import com.wbt.mvp.presenter.DataPresenter;
import com.wbt.ui.base.BaseActivity;
import com.wbt.util.BroadCast;
import com.wbt.util.DBManager;
import com.wbt.util.DateUtils;
import com.wbt.util.HandlerUtils;
import com.wbt.util.NormalShared;
import com.wbt.util.NormalUtil;
import com.wbt.util.config.StatusConfig;
import com.wbt.util.customview.CustomProgress;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/1/27.
 *
 */
public class DataActivity extends BaseActivity implements HandlerUtils.OnReceiveMessageListener,DataAdapter.OnItemClickListener{
    private RecyclerView datarecyclerview;
    private SwipeRefreshLayout dataswipeRefresh;
    private LinearLayout back;
    private TextView titlename;
    private DataAdapter dataAdapter;
    private HandlerUtils.HandlerHolder handlerHolder;
    private DataPresenter dataPresenter;
    private DataBean databean;
    private String ErrorMsg;
    //获取系统时间
    private SimpleDateFormat datefromat;
    //当前时间
    private Date currentdate;
    private String time;
    private String dataaddre;
    private int poss;

    /**
     * 获取是否为c或者f的之间的转换
     */
    private static final String PREFS_SET_CORF="SetCORF";

    //存储度数和华氏度
    private SharedPreferences spsetcorf;

    private NormalShared normalShared;

    //private CustomProgress customProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        initView();
        getIntentData();
        initData();
    }

    private void initView() {
        //摄氏度和华氏度之间的转换
        spsetcorf = getSharedPreferences(PREFS_SET_CORF, 0);
        datarecyclerview = findViewById(R.id.data_recylerview);
        dataswipeRefresh = findViewById(R.id.data_swipe_refresh);
        back = findViewById(R.id.back);
        titlename = findViewById(R.id.titlename);

        back.setOnClickListener(this);

        datarecyclerview.setLayoutManager(new LinearLayoutManager(this));
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
        normalShared = new NormalShared();
        poss = -1;
        handlerHolder = new HandlerUtils.HandlerHolder(this);
        dataPresenter = new DataPresenter();
        datefromat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        handlerHolder.post(runnable);
    }

    private void getIntentData(){
        try{
            Intent intent = getIntent();
            dataaddre = intent.getStringExtra(StatusConfig.DATA_ADDRE_KEY);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getData();
            handlerHolder.postDelayed(runnable,15000);
        }
    };


    private void getData(){
        if(dataaddre!=null) {
            dataPresenter.getData(dataaddre, iDataListener);
        }
    }


    IDataListener iDataListener = new IDataListener() {
        @Override
        public void onDataSuccess(DataBean dataBean) {
            databean = dataBean;
            if(databean!=null){
                handlerHolder.sendEmptyMessage(200);
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

                saveTempDataDB(databean);

                if(dataAdapter==null){
                    dataAdapter = new DataAdapter(this,databean,this,normalShared);
                    datarecyclerview.setAdapter(dataAdapter);
                }else{
                    dataAdapter.dataBean = databean;
                    dataAdapter.notifyDataSetChanged();
                }

                //发送广播数据 15秒发送1次 ios是用通知去发送到另外一个界面当中
                if(databean!=null&&poss>=0){
                    sendDataBroadCast(poss,databean);
                }

                break;
        }
    }

    /**
     * 发送数据广播
     */
    private void sendDataBroadCast(int pos, DataBean dataBean){
        String deviceaddre,probesymbol,probename,probetemp,probehum;
        try{
            if(dataBean!=null){
                deviceaddre = dataBean.getDeviceaddre();
                probesymbol = dataBean.getDevicesymbol();
                DataBean.DatasBean datasBean = dataBean.getDatas().get(pos);
                if(datasBean!=null){
                    if(datasBean.getProbe().equals("zero")){
                        return;
                    }else{
                        probename = datasBean.getProbe();
                        probetemp = NormalUtil.saveOneDecimalPoint(datasBean.getTemp()+"");
                        probehum = datasBean.getHum();

                        String all = probename+","+deviceaddre+","+probetemp+","+probesymbol+","+probehum;

                        Intent intent = new Intent();
                        intent.setAction(BroadCast.SEND_DATA);
                        intent.putExtra(StatusConfig.DATA_TEMP_KEY, all);
                        //发送广播
                        sendBroadcast(intent);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
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

    /**
     * 存储数据到本地的Sql数据库中去
     */
    private void saveTempDataDB(DataBean dataBean){
        try {
            if (dataBean != null) {
                //获取当前存储的系统的时间
                currentdate = new Date(System.currentTimeMillis());
                time = datefromat.format(currentdate);
                //专门转为时间的类
                Long ltime = DateUtils.getStringToDate(time);

                for(DataBean.DatasBean datasBean:dataBean.getDatas()) {
                    if (datasBean.getProbe().equals("zero")) {
                        return;
                    } else {
                        Temperature temperature = new Temperature();
                        //存储地址
                        temperature.setTpdvid(dataBean.getDeviceaddre());
                        temperature.setTpname(datasBean.getProbe());
                        temperature.setTphum(datasBean.getHum());
                        //如果为华氏度的时候 要转为摄氏度才能够存储
                        if (dataBean.getDevicesymbol().equals("1")) {
                            float a = (float) datasBean.getTemp();
                            float b = NormalUtil.convertFahrenheitToCelcius(a);
                            String temp = NormalUtil.saveOneDecimalPoint(b + "");
                            temperature.setTptemp(temp);
                            temperature.setTpsymbol("°C");
                        } else {
                            String temp = NormalUtil.saveOneDecimalPoint(datasBean.getTemp() + "");
                            temperature.setTptemp(temp);
                            temperature.setTpsymbol("°C");
                        }
                        temperature.setTptime(ltime);
                        DBManager.getInstance(this).insertTemp(temperature);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void OnItemClick(View view, int pos) {
        poss = pos;
        Intent intent = new Intent(this,ChartActivity.class);
        if(databean!=null){
            DataBean.DatasBean datasBean = databean.getDatas().get(pos);
            if(datasBean!=null){
                Bundle bundle = new Bundle();
                bundle.putString(StatusConfig.DATA_PROBENAME_KEY,datasBean.getProbe());
                bundle.putString(StatusConfig.DATA_DEVICEADDRE_KEY, databean.getDeviceaddre());
                bundle.putString(StatusConfig.DATA_TEMPSYMBOL_KEY, databean.getDevicesymbol());
                bundle.putString(StatusConfig.DATA_PROBETEMP_KEY, datasBean.getTemp()+"");
                bundle.putString(StatusConfig.DATA_PROBEHUM_KEY,datasBean.getHum());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handlerHolder!=null){
            handlerHolder.removeCallbacksAndMessages(null);
        }

    }
}
