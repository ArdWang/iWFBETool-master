package com.wbt.manager.chart;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wbt.bean.Temperature;
import com.wbt.util.DBManager;
import com.wbt.util.DateUtils;
import com.wbt.util.NormalUtil;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by rnd on 2017/10/18.
 *
 */

public class ChartManager {

    private DateFormat time;
    private DateFormat time1;
    private DateFormat time2;
    private Date date;
    private Date date1;
    private Context context;
    private long edtime;
    private TextView temphighalarm,templowalarm;
    private LinearLayout onClick;
    private boolean isalarm;
    private HandlerAlarm handleralarm;

    public ChartManager(Context mcontext){
        context = mcontext;
        //this.temphighalarm = temphighalarm;
        //this.templowalarm = templowalarm;
        //this.onClick = onClick;
        handleralarm = new HandlerAlarm(this);
        //this.lowonClick = lowonClick;
    }

    /**
     * 测试用来读取数据库中的数据
     */
    /*public List<Temperature> readTempDataDB(String addre){
        String text="";
        String ctimes;
        String atimes;
        time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date(System.currentTimeMillis());
        ctimes = time1.format(date);
        atimes = time.format(date);
        String a[] = ctimes.split(" ");
        String b[] = a[1].split(":");
        int c = Integer.parseInt(b[0]);
        int d = 0;
        if(c==0){
            d=0;
        }else{
            d = c-1;
        }
        String btime = d+":"+b[1]+":"+b[2];
        String etime = a[1];
        String bgtime = atimes+" "+btime;
        String egtime = atimes+" "+etime;
        long at = DateUtils.getStringToDate(bgtime);
        long bt = DateUtils.getStringToDate(egtime);

        return DBManager.getInstance(context).queryOneTempChartList(addre,bt,at);

    }


    public List<Temperature> readQdayData(String addre,long bt,long at){
        return DBManager.getInstance(context).queryOneTempChartList(addre,bt,at);
    }*/


    public List<Temperature> readChartTempDataDB(String addre,String probename){
        return DBManager.getInstance(context).query100TempChartData(addre,probename);
    }




    /*public List<Temperature> readTemp(){
        return DBManager.getInstance(context).queryTempChartList();
    }*/


    /**
     * 获取当前的温度数据
     * @param list
     * @param tempTemp
     * @return
     */
    public List<Temperature> getCurrentData(List<Temperature> list,String tempTemp,String tempHum){
        date1 = new Date(System.currentTimeMillis());
        time2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ctime = time2.format(date1);
        long c = DateUtils.getStringToDate(ctime);
        Temperature temp = new Temperature();
        if(edtime!=c&&!tempTemp.isEmpty()&&!tempHum.isEmpty()){
            temp.setTptime(c);
            edtime = c;
            temp.setTptemp(tempTemp);
            temp.setTphum(tempHum);
            list.add(temp);
        }
        return list;
    }

    /**
     * 获取最大和最小值
     * @param templist
     * @param corf
     */
    public void setListTempMaxOrMin(List<Temperature> templist, String corf, TextView tempmax,TextView tempmin,String symbol) {
        float max = 0;
        float min = 0;
        for (int i = 0; i < templist.size(); i++) {
            Temperature tmp = templist.get(i);
            if(tmp.getTptemp()!=null) {
                float tempf;
                float temp = Float.parseFloat(tmp.getTptemp());
                if(corf.equals("°C")) {  //温度的时候
                    if (i == 0) {
                        max = temp;
                        min = temp;
                    }
                    if (temp > max) {
                        max = temp;
                    }
                    if (temp < min) {
                        min = temp;
                    }
                }else if(corf.equals("°F")){  //华氏度的时候
                    float val = NormalUtil.ConvertCelciusToFahren((float)temp);
                    String tempFvalue =val+"";
                    String value = NormalUtil.saveOneDecimalPoint(tempFvalue);
                    tempf= Float.parseFloat(value);
                    if (i == 0) {
                        max = tempf;
                        min = tempf;
                    }
                    if (tempf > max) {
                        max = tempf;
                    }
                    if (tempf < min) {
                        min = tempf;
                    }
                }
            }
        }

        tempmax.setText("MAX "+max+symbol);
        tempmin.setText("MIN "+min+symbol);
    }

    /**
     * 获取最大和最小值
     * @param templist
     */
    public void setListHumMaxOrMin(List<Temperature> templist,TextView hummax,TextView hummin) {
        float max=0;
        float min = 0;
        for(int i=0;i<templist.size();i++){
            Temperature tmp = templist.get(i);
            if(tmp!=null){
                String hum = tmp.getTphum();
                String hums = hum.replace("%","");
                float a = Float.parseFloat(hums);
                if (i == 0) {
                    max = a;
                    min = a;
                }
                if (a > max) {
                    max = a;
                }
                if (a < min) {
                    min =a;
                }
            }
        }

        hummax.setText("MAX  "+max+"%");
        hummin.setText("MIN  "+min+"%");
    }


    /**
     * 高温和低温的转换
     * @param setcorf
     * @param tempAlarm
     * @param txtalarm
     */
    /*public void setTempConvert(String setcorf,String tempAlarm,TextView txtalarm){
        //如果2个符号想等的时候
        if (setcorf.equals("°C")) {
            int afc;
            float a = Float.parseFloat(tempAlarm);
            if(a>0) {
                afc = (int) (a + 0.5);
            }else{
                afc = (int) a;
            }
            txtalarm.setText(afc+"°C");
        } else {
            float a = Float.parseFloat(tempAlarm);
            float af = NormalUtil.ConvertCelciusToFahren(a);
            int afc;
            if(af>0) {
                afc = (int)(af+0.5);
            }else{
                afc = (int)af;
            }
            txtalarm.setText(afc + "°F");
        }
    }*/


    /**
     * 图片动画效果
     * @param imghalarm
     */
    public void imgAnimation(ImageView imghalarm){
        //摇摆
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 40f, 0f, 0f);
        translateAnimation.setDuration(300);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        imghalarm.setAnimation(translateAnimation); //这里iv就是我们要执行动画的item，例如一个imageView
        //lowimgalarm.setAnimation(translateAnimation);
        translateAnimation.start();
    }



    /**
     * 采用弱的引用
     */
    static class HandlerAlarm extends Handler {
        WeakReference<ChartManager> mActivityReference;
        HandlerAlarm(ChartManager deviceManager) {
            mActivityReference= new WeakReference<>(deviceManager);
        }
        @Override
        public void handleMessage(Message msg) {
            ChartManager chartManager = mActivityReference.get();
            if (chartManager!= null) {
                chartManager.Todo(msg);
            }
        }
    }


    private void Todo(Message msg){
        switch (msg.what){
            case 1000:
                //高温开关打开
                temphighalarm.setVisibility(View.VISIBLE);
                onClick.setVisibility(View.VISIBLE);
                break;

            case 1002:
                if(!isalarm) {
                    onClick.setVisibility(View.GONE);
                }
                temphighalarm.setVisibility(View.GONE);
                break;

            //低温开启
            case 2000:
                templowalarm.setVisibility(View.VISIBLE);
                onClick.setVisibility(View.VISIBLE);
                break;

            case 2002:
                if(!isalarm) {
                    onClick.setVisibility(View.GONE);
                }
                templowalarm.setVisibility(View.GONE);
                break;
        }

    }


    /*@SuppressLint("HandlerLeak")
    private Handler handleralarm = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1000:
                    //高温开关打开
                    temphighalarm.setVisibility(View.VISIBLE);
                    onClick.setVisibility(View.VISIBLE);
                    break;

                case 1002:
                    onClick.setVisibility(View.GONE);
                    temphighalarm.setVisibility(View.GONE);
                    break;

                //低温开启
                case 2000:
                    templowalarm.setVisibility(View.VISIBLE);
                    onClick.setVisibility(View.VISIBLE);
                    break;

                case 2002:
                    onClick.setVisibility(View.GONE);
                    templowalarm.setVisibility(View.GONE);
                    break;
            }
        }
    };*/

}

