package com.wbt.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.wbt.R;
import com.wbt.bean.Temperature;
import com.wbt.manager.chart.ChartManager;
import com.wbt.manager.chart.LineChartManager;
import com.wbt.ui.base.BaseActivity;
import com.wbt.util.BroadCast;
import com.wbt.util.DateUtils;
import com.wbt.util.NormalUtil;
import com.wbt.util.config.StatusConfig;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/27.
 * 图表界面
 * 此界面需要接受动态广播的数据
 */

public class ChartActivity extends BaseActivity{
    //控件块
    private TextView probename,deviceaddre;
    private TextView temptemp,tempsymbol;
    private TextView tempmax,tempmin,tempavg;
    private TextView humhum,humsymbol;
    private TextView hummax,hummin,humavg;

    private LineChart tempchart,humchart;

    private LinearLayout back;

    //图表帮助类
    private LineChartManager lineChartManager;

    private LineChartManager humChartManager;

    private ChartManager chartManager;

    private List<Temperature> templist;

    private int Count;

    private Date date;

    private DateFormat dateFormat;

    private String deviceAddre,probeName;

    private String currentsymbol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        initView();
        getIntentData();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCast.SEND_DATA);
        return intentFilter;
    }


    private void initView() {
        probename = findViewById(R.id.probe_name);
        deviceaddre = findViewById(R.id.device_addre);
        temptemp = findViewById(R.id.tempTemp);
        tempsymbol = findViewById(R.id.tempSymbol);
        tempmax = findViewById(R.id.temp_max);
        tempmin = findViewById(R.id.temp_min);
        tempavg = findViewById(R.id.temp_avg);
        humhum = findViewById(R.id.humHum);
        humsymbol = findViewById(R.id.humSymbol);
        hummax = findViewById(R.id.hum_max);
        hummin = findViewById(R.id.hum_min);
        humavg = findViewById(R.id.hum_avg);

        tempchart = findViewById(R.id.tempChart);
        humchart = findViewById(R.id.humChart);

        back = findViewById(R.id.back);

        back.setOnClickListener(this);
    }

    private void initData(){
        chartManager = new ChartManager(this);
        Count = 0;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        onLoadData();
    }


    private void getIntentData(){
        try{
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if(bundle!=null){

                probeName = bundle.getString(StatusConfig.DATA_PROBENAME_KEY);
                deviceAddre = bundle.getString(StatusConfig.DATA_DEVICEADDRE_KEY);

                probename.setText(probeName);
                deviceaddre.setText(deviceAddre);

                String probetemp = NormalUtil.saveOneDecimalPoint(bundle.getString(StatusConfig.DATA_PROBETEMP_KEY));
                temptemp.setText(probetemp);

                String atempsymbol = bundle.getString(StatusConfig.DATA_TEMPSYMBOL_KEY);
                if(atempsymbol!=null){
                    if(atempsymbol.equals("0")) {
                        tempsymbol.setText("°C");
                        currentsymbol = "°C";
                    }else{
                        tempsymbol.setText("°F");
                        currentsymbol = "°F";
                    }
                }else{
                    tempsymbol.setText("-");
                    currentsymbol = "°F";
                }

                String hum = bundle.getString(StatusConfig.DATA_PROBEHUM_KEY);

                if(hum!=null){
                    String j = hum.replace("%","");
                    humhum.setText(j);
                    humsymbol.setText("%");
                }else{
                    humhum.setText("-");
                    humsymbol.setText("-");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void onLoadData() {
        if(deviceAddre!=null&&probeName!=null){
            templist = chartManager.readChartTempDataDB(deviceAddre,probeName);

            if(templist.size()>0){
                setLoadTempData();
                setLoadHumData();
                chartManager.setListTempMaxOrMin(templist, currentsymbol, tempmax, tempmin, currentsymbol);
                chartManager.setListHumMaxOrMin(templist,hummax,hummin);
            }
        }
    }



    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //如果是相等
            if(BroadCast.SEND_DATA.equals(action)){
                String tempdata = intent.getStringExtra(StatusConfig.DATA_TEMP_KEY);
                Log.i("TAG is :: === >>", tempdata);
                //处理图表和温度数据
                setTempData(tempdata);
            }
        }
    };

    //probename+","+deviceaddre+","+probetemp+","+probesymbol+","+probehum;
    private void setTempData(String tempdata){
        try{
            if(tempdata!=null){
                String arraydata[] = tempdata.split(",");
                probeName = arraydata[0];
                deviceAddre = arraydata[1];

                probename.setText(probeName);
                deviceaddre.setText(deviceAddre);
                String temp = NormalUtil.saveOneDecimalPoint(arraydata[2]);
                temptemp.setText(temp);

                String tsmybol = arraydata[3];
                if(tsmybol!=null){
                    if(tsmybol.equals("0")) {
                        currentsymbol = "°C";
                        tempsymbol.setText("°C");
                    }else{
                        tempsymbol.setText("°F");
                        currentsymbol = "°F";
                    }
                }else{
                    currentsymbol = "°F";
                    tempsymbol.setText("-");
                }

                String hum = arraydata[4];

                if(hum!=null){
                    String j = hum.replace("%","");
                    humhum.setText(j);
                    humsymbol.setText("%");
                }else{
                    humhum.setText("-");
                    humsymbol.setText("-");
                }

                templist = chartManager.getCurrentData(templist, temp, arraydata[4]);

                //每次运行自增一次
                Count++;
                Log.i("i的值为", Count + "");

                //最大记录100个数据
                if (Count >= 100) {
                    getCurrentCount();
                }
                if(templist.size()>0) {
                    chartManager.setListTempMaxOrMin(templist, currentsymbol, tempmax, tempmin, currentsymbol);
                    chartManager.setListHumMaxOrMin(templist, hummax, hummin);
                    setLoadTempData();
                    setLoadHumData();
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 数据算法
     * 得到当前的时间和数据
     */
    private void getCurrentCount(){
        if(templist.size()>0){
            //清除所有数据
            templist.clear();
        }
        //继续加载数据
        onLoadData();
        //重新设置为0
        Count = 0;
    }

    /**
     * 加载温度数据
     */
    private void setLoadTempData() {
        if(lineChartManager==null){
            lineChartManager = new LineChartManager(this,tempchart,0,currentsymbol);
        }

        // 添加X轴坐标
        List<String> xVals = new ArrayList<>();
        for (int i = 0; i < templist.size(); i++) {
            Temperature temp = templist.get(i);
            Long a = temp.getTptime();
            String ab = DateUtils.getDateToString(a);
            String ss = ab.substring(11, 19);
            xVals.add(ss);
        }

        // 添加Y轴坐标
        List<Float> yVals = new ArrayList<>();
        for (int i = 0; i < templist.size(); i++) {
            Temperature temp = templist.get(i);
            String y = temp.getTptemp();
            if(currentsymbol.equals("°C")){
                if(!y.equals("")&&!y.equals(null)) {
                    yVals.add(Float.parseFloat(y));
                }
            }else if(currentsymbol.equals("°F")){
                float valf = Float.parseFloat(y);
                float val = NormalUtil.ConvertCelciusToFahren((float)valf);
                String tempFvalue =val+"";
                String value = NormalUtil.saveOneDecimalPoint(tempFvalue);
                if(!value.equals("")&&!value.equals(null)) {
                    yVals.add(Float.parseFloat(value));
                }
            }
        }

        if(templist.size()>0) {
            lineChartManager.showLineChart(xVals, yVals, getResources().getColor(R.color.colorBlue), "Diagram (degree/time)"+" "+getCurrentDataTime());
        }

        lineChartManager.setDescription("");
    }


    /**
     * 加载湿度数据
     */
    private void setLoadHumData() {
        if(humChartManager==null){
            humChartManager = new LineChartManager(this,humchart,1,currentsymbol);
        }

        // 添加X轴坐标
        List<String> xVals = new ArrayList<>();
        for (int i = 0; i < templist.size(); i++) {
            Temperature temp = templist.get(i);
            Long a = temp.getTptime();
            String ab = DateUtils.getDateToString(a);
            String ss = ab.substring(11, 19);
            if(!ss.isEmpty()) {
                xVals.add(ss);
            }
        }

        // 添加Y轴坐标
        List<Float> yVals = new ArrayList<>();
        for (int i = 0; i < templist.size(); i++) {
            Temperature temp = templist.get(i);
            String y = temp.getTphum();
            String yy = y.replace("%","");
            if(yy!=null&&!yy.isEmpty()) {
                yVals.add(Float.parseFloat(yy));
            }
        }

        if(templist.size()>0) {
            humChartManager.showLineChart(xVals, yVals, getResources().getColor(R.color.colorBlue), "Humidity (percent/time)"+" "+getCurrentDataTime());
        }

        humChartManager.setDescription("");
    }

    /**
     * 转换时间格式
     * @return
     */
    private String getCurrentDataTime(){
        date = new Date(System.currentTimeMillis());
        String times = dateFormat.format(date);
        String ss[] = times.split(" ");
        String sss[] = ss[0].split("-");
        String timee = sss[1]+"/"+sss[2]+"/"+sss[0];
        return timee;
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
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mGattUpdateReceiver);
    }






}
