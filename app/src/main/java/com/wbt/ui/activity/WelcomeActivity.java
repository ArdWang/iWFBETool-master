package com.wbt.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.wbt.R;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnd on 2018/2/2.
 *
 */

public class WelcomeActivity extends FragmentActivity{

    private static final int times = 3000;

    List<String> permissionList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initStatusBar();
        getPermission();
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

    private void getPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.BLUETOOTH);
        }

        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    @Override
    protected void onResume() {
        try {
            if (isok()) {
                //String a = Environment.getExternalStorageState() + "/CySmart";
                String b = "/storage/emulated/0/iWFBETool";
                File file = new File(b);
                if (!file.exists()) {
                    file.mkdirs();
                    Log.i("Create_file", "文件夹不存在创建文件夹");
                } else {
                    Log.i("Create_file", "文件夹存在不需要创建");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onResume();
    }

    private boolean isok() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);

                finish();
            }
        }, times);
    }
}
