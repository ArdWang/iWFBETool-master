package com.wbt.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wbt.R;
import com.wbt.ui.base.BaseActivity;
import com.wbt.util.DateUtils;
import com.wbt.util.NormalShared;
import com.wbt.util.config.StatusConfig;
import com.wbt.util.customview.calender.MaterialCalendarDialog;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rnd on 2018/2/1.
 * 选择日期界面
 */

public class SelectDateActivity extends BaseActivity{

    private LinearLayout back,selectdate;

    private TextView titlename;

    private TextView txtDate;

    private int deviceid,localornet;

    private String probename;

    private Button btnselectchart,btnselectdata,btncancel;

    private String dates;

    private String addre;

    private SharedPreferences spcurrentDate;

    /**
     * 获取是否为c或者f的之间的转换
     */
    private static final String PREFS_CURRENT_DATE="SetCurrentDate";

    private NormalShared normalShared;

    private Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectdate);
        initView();
        initIntentData();

    }

    private void initView() {

        spcurrentDate = getSharedPreferences(PREFS_CURRENT_DATE,0);

        normalShared = new NormalShared();

        txtDate = findViewById(R.id.txtdate);

        titlename = findViewById(R.id.titlename);

        back = findViewById(R.id.back);

        selectdate = findViewById(R.id.ll_selectdate);

        btnselectchart = findViewById(R.id.btnselectchart);
        btnselectdata = findViewById(R.id.btnselectdata);
        btncancel = findViewById(R.id.btncancel);

        back.setOnClickListener(this);
        selectdate.setOnClickListener(this);

        btnselectchart.setOnClickListener(this);
        btnselectdata.setOnClickListener(this);
        btncancel.setOnClickListener(this);

    }

    private void initIntentData(){
        try{
            Intent intent = getIntent();

            deviceid = intent.getIntExtra(StatusConfig.DATA_DVID_KEY,0);
            probename = intent.getStringExtra(StatusConfig.DATA_PROBENAME_KEY);
            localornet = intent.getIntExtra("LocalAndNet",0);

            addre = intent.getStringExtra(StatusConfig.DATA_ADDRE_KEY);

            String currentDate = normalShared.getCurrentDate(spcurrentDate,addre,probename);

            String title = intent.getStringExtra("titlename");

            //头部的名字
            if(title!=null){
                titlename.setText(title);
            }

            if(!currentDate.isEmpty()){
                //String aa = DateUtils.formatToString(currentDate,"MM/dd/yyyy");
                dates = currentDate;
                txtDate.setText(currentDate);
            }else{
                date = new Date(System.currentTimeMillis());
                String aa = DateUtils.formatToString(date,"MM/dd/yyyy");
                dates = aa;
                txtDate.setText(aa);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,SerachAllActivity.class);
        Bundle bundle = new Bundle();
        switch (v.getId()){

            case R.id.back:

                finish();

                break;

            case R.id.ll_selectdate:
                Date todayDate = Calendar.getInstance().getTime();
                //显示日历对话框
                MaterialCalendarDialog calendarDialog = MaterialCalendarDialog.getInstance(this,todayDate);
                calendarDialog.setOnOkClickLitener(new MaterialCalendarDialog.OnOkClickLitener() {
                    @Override
                    public void onOkClick(Date date) {
                        //edt_starttime.setText(DateTimeHelper.formatToString(date,"yyyy-MM-dd"));
                        String aa = DateUtils.formatToString(date,"MM/dd/yyyy");
                        dates = aa;
                        txtDate.setText(aa);
                        normalShared.saveCurrentDate(spcurrentDate,addre,probename,aa);
                    }
                });

                calendarDialog.show(getSupportFragmentManager(),"t");

                break;

            case R.id.btnselectchart:
                //查询数据
                bundle.putString("titlename","Chart");
                bundle.putInt("LocalAndNet",localornet);
                bundle.putInt(StatusConfig.DATA_DVID_KEY,deviceid);
                bundle.putString(StatusConfig.DATA_PROBENAME_KEY,probename);
                bundle.putString(StatusConfig.DATA_ADDRE_KEY,addre);
                bundle.putInt("value",1);
                bundle.putString("date",dates);

                intent.putExtras(bundle);

                startActivity(intent);
                break;

            case R.id.btnselectdata:
                //查询数据
                bundle.putString("titlename","Date");
                bundle.putInt("LocalAndNet",localornet);
                bundle.putInt(StatusConfig.DATA_DVID_KEY,deviceid);
                bundle.putString(StatusConfig.DATA_PROBENAME_KEY,probename);
                bundle.putString(StatusConfig.DATA_ADDRE_KEY,addre);
                bundle.putInt("value",2);
                bundle.putString("date",dates);

                intent.putExtras(bundle);

                startActivity(intent);
                break;

            case R.id.btncancel:

                finish();

                break;
        }
    }
}
