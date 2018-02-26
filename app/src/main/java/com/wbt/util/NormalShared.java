package com.wbt.util;

import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/1/27.
 * 存储状态
 */

public class NormalShared {
    /**
     * 获取是否为设置存储的 华氏度和摄氏度
     * @param corf
     */
    public void saveCORF(SharedPreferences sharedPreferences, String addre, String corf,String probe){
        SharedPreferences.Editor corfEditor = sharedPreferences.edit();
        corfEditor.putString(probe+addre+"CORF", corf);
        corfEditor.commit();
    }

    /**
     * 得到存储的 华氏度和摄氏度 默认的情况为华氏度
     * @return
     */
    public String getCORF(SharedPreferences sharedPreferences,String probe,String addre){
        if(!addre.isEmpty()) {
            String corf = sharedPreferences.getString(probe+addre + "CORF", "");
            if (!corf.equals(null) && !corf.equals("")) {
                return corf;
            } else {
                return "°C";
            }
        }else{
            return "°C";
        }
    }

    /**
     * 存储当前选择的时间
     * @param sharedPreferences
     * @param addre
     * @param probe
     * @param date
     */
    public void saveCurrentDate(SharedPreferences sharedPreferences,String addre,String probe,String date){
        SharedPreferences.Editor corfEditor = sharedPreferences.edit();
        corfEditor.putString(probe+addre+"CurrentDate", date);
        corfEditor.commit();
    }

    /**
     * 得到当前存储的日期
     * @param sharedPreferences
     * @param addre
     * @param probe
     * @return
     */
    public String getCurrentDate(SharedPreferences sharedPreferences,String addre,String probe){
        if(addre!=null&&probe!=null){
            String currentDate = sharedPreferences.getString(probe+addre+"CurrentDate","");
            if(currentDate!=null){
                return currentDate;
            }
        }
        return "";
    }

}
