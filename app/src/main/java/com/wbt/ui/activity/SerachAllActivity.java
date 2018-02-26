package com.wbt.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wbt.R;
import com.wbt.ui.base.BaseFragmentActivity;
import com.wbt.ui.fragment.serach.SerachChartFragment;
import com.wbt.ui.fragment.serach.SerachDataFragment;
import com.wbt.util.config.StatusConfig;



/**
 * Created by rnd on 2018/2/1.
 *
 */

public class SerachAllActivity extends BaseFragmentActivity{

    private LinearLayout back;
    private TextView titlename;
    private int value;

    public static String date;

    public static String probename;

    public static int deviceid;

    public static int localornet;

    public static String addre;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allserach);
        initView();
        initData();
    }

    private void initView() {
        back = findViewById(R.id.back);
        titlename = findViewById(R.id.titlename);
        back.setOnClickListener(this);
    }


    private void initData(){
        try{
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            value = bundle.getInt("value");
            date = bundle.getString("date");
            deviceid = bundle.getInt(StatusConfig.DATA_DVID_KEY,0);
            probename = bundle.getString(StatusConfig.DATA_PROBENAME_KEY);
            addre = bundle.getString(StatusConfig.DATA_ADDRE_KEY);
            localornet = bundle.getInt("LocalAndNet");

            showCurrentFragment(value,intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void showCurrentFragment(int index, Intent intent){
        Fragment fragment = null;
        switch (index){
            case 1:
                fragment = new SerachChartFragment();
                intent = getIntent();
                titlename.setText(intent.getStringExtra("titlename"));
                break;

            case 2:
                fragment = new SerachDataFragment();
                intent = getIntent();
                titlename.setText(intent.getStringExtra("titlename"));
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.currentSet, fragment).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }

}
