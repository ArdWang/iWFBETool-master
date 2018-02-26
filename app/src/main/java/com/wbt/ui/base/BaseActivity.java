package com.wbt.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wbt.util.ActivityCollector;

/**
 * Created by rnd on 2018/1/18.
 *
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.d("BaseActivity", getClass().getSimpleName());
        //添加
        ActivityCollector.addActivity(this);
        //initStatusBar();
    }

    /**
     * 定义了之后不需要从写代码了
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * 初始化沉浸式状态栏
     */
    private void initStatusBar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除掉
        ActivityCollector.removeActivity(this);
    }

}
