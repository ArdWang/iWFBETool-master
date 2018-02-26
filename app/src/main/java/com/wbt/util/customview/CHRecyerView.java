package com.wbt.util.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

/**
 * Created by rnd on 2018/1/31.
 *
 */

public class CHRecyerView extends RecyclerView{

    public CHRecyerView(Context context) {
        super(context);
    }

    public CHRecyerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ViewParent parent =this;
        while(!((parent = parent.getParent()) instanceof ViewPager)) ;// 循环查找viewPager
        parent.requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ViewParent parent =this;
        while(!((parent = parent.getParent()) instanceof ViewPager)) ;// 循环查找viewPager
        parent.requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        ViewParent parent =this;
        while(!((parent = parent.getParent()) instanceof ViewPager)) ;// 循环查找viewPager
        parent.requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(ev);
    }
}
