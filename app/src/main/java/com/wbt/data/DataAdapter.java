package com.wbt.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.wbt.R;
import com.wbt.bean.DataBean;
import com.wbt.bean.Temperature;
import com.wbt.manager.chart.ChartManager;
import com.wbt.manager.chart.LineChartManager;
import com.wbt.util.DBManager;
import com.wbt.util.DateUtils;
import com.wbt.util.NormalShared;
import com.wbt.util.NormalUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2018/1/27.
 *
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{
    private Context context;
    private OnItemClickListener onItemClickListener;
    public DataBean dataBean;

    private List<Temperature> templist0;

    private List<Temperature> templist1;

    /**
     * 获取是否为c或者f的之间的转换
     */
    private static final String PREFS_SET_CORF="SetCORF";

    //存储度数和华氏度
    private SharedPreferences spsetcorf;
    private NormalShared normalShared;

    private Date date;

    private SimpleDateFormat simpleDateFormat;

    //图表帮助类
    private LineChartManager lineChartManager0;

    private LineChartManager lineChartManager1;

    private ChartManager chartManager;


    private String symbol;

    public DataAdapter(Context context,DataBean dataBean,OnItemClickListener onItemClickListener,NormalShared normalShared){
        templist0 = new ArrayList<>();
        templist1 = new ArrayList<>();
        chartManager = new ChartManager(context);
        this.context = context;
        this.dataBean = dataBean;
        this.onItemClickListener = onItemClickListener;
        this.normalShared = normalShared;
        //摄氏度和华氏度之间的转换
        spsetcorf = context.getSharedPreferences(PREFS_SET_CORF, 0);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_fg_item,parent,false);
        final DataAdapter.ViewHolder holder = new DataAdapter.ViewHolder(view,onItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            if (dataBean != null) {
                DataBean.DatasBean datasBean = dataBean.getDatas().get(position);

                String symbol;
                if(dataBean.getDevicesymbol().equals("0")) {
                    symbol="°C";
                }else{
                    symbol="°F";
                }

                //存储温度符号
                normalShared.saveCORF(spsetcorf,dataBean.getDeviceaddre(),symbol,datasBean.getProbe());
                if (datasBean.getProbe().equals("zero")) {
                    holder.dataleft.setVisibility(View.GONE);
                    holder.dataright.setVisibility(View.GONE);
                    holder.nodata.setVisibility(View.VISIBLE);
                } else {

                    String temp = NormalUtil.saveOneDecimalPoint(datasBean.getTemp()+"");
                    holder.probename.setText(datasBean.getProbe());
                    holder.probetemp.setText(temp);
                    holder.probehum.setText(datasBean.getHum());
                    if(dataBean.getDevicesymbol().equals("0")) {
                        symbol = "°C";
                        holder.devicesymbol.setText("°C");
                    }else{
                        symbol = "°F";
                        holder.devicesymbol.setText("°F");
                    }
                    holder.deviceaddre.setText(dataBean.getDeviceaddre());


                    //第一个探头数据
                    if(position==0){
                        //查询数据库里面数据
                        if(templist0.size()<=0) {
                            templist0 = DBManager.getInstance(context).query5TempChartData(dataBean.getDeviceaddre(), datasBean.getProbe());
                            setLoadTempData(holder.lineChart, symbol, lineChartManager0,templist0);
                        }else{
                            templist0 = chartManager.getCurrentData(templist0,temp,datasBean.getHum());
                            setLoadTempData(holder.lineChart, symbol, lineChartManager0,templist0);
                            if(templist0.size()>=20){
                                templist0.clear();
                            }
                        }
                    }
                    //第二个探头数据
                    else if(position==1){
                        //查询数据库里面数据
                        if(templist1.size()<=0) {
                            templist1 = DBManager.getInstance(context).query5TempChartData(dataBean.getDeviceaddre(), datasBean.getProbe());
                            setLoadTempData(holder.lineChart, symbol, lineChartManager1,templist1);
                        }else{
                            templist1 = chartManager.getCurrentData(templist1,temp,datasBean.getHum());
                            setLoadTempData(holder.lineChart, symbol, lineChartManager1,templist1);
                            if(templist1.size()>=20){
                                templist1.clear();
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataBean.getDatas().size();
    }


    /**
     * 加载温度数据 显示的曲线图表
     */
    private void setLoadTempData(LineChart tempchart,String currentsymbol,LineChartManager lineChartManager,List<Temperature> templist) {

        if (lineChartManager == null) {
            lineChartManager = new LineChartManager(context, tempchart, 0,currentsymbol);
        }

        // 添加X轴坐标
        List<String> xVals = new ArrayList<>();
        for (int i = 0; i < templist.size(); i++) {
            Temperature temp = templist.get(i);
            Long a = temp.getTptime();
            String ab = DateUtils.getDateToString(a);
            String ss = ab.substring(14, 19);
            xVals.add(ss);
        }

        // 添加Y轴坐标
        List<Float> yVals = new ArrayList<>();
        for (int i = 0; i < templist.size(); i++) {
            Temperature temp = templist.get(i);
            String y = temp.getTptemp();
            if (currentsymbol.equals("°C")) {
                if (!y.equals("") && !y.isEmpty()) {
                    yVals.add(Float.parseFloat(y));
                }
            } else if (currentsymbol.equals("°F")) {
                float valf = Float.parseFloat(y);
                float val = NormalUtil.ConvertCelciusToFahren(valf);
                String tempFvalue = val + "";
                String value = NormalUtil.saveOneDecimalPoint(tempFvalue);
                if (!value.equals("") && !value.isEmpty()) {
                    yVals.add(Float.parseFloat(value));
                }
            }
        }

        if (templist.size() > 0) {
            lineChartManager.showLineChart(xVals, yVals, context.getResources().getColor(R.color.colorBlue), "Diagram (degree/time)");
        }
        lineChartManager.setDescription("");

    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View dataView;
        OnItemClickListener onItemClickListener;
        TextView nodata;
        TextView probetemp;
        TextView probehum;
        TextView deviceaddre;
        TextView probename;
        TextView devicesymbol;
        LinearLayout dataleft;
        LinearLayout dataright;

        private LineChart lineChart;



        public ViewHolder(View view,OnItemClickListener onItemClickListener) {
            super(view);
            dataView = view;
            this.onItemClickListener = onItemClickListener;
            nodata = view.findViewById(R.id.no_data);
            probetemp = view.findViewById(R.id.probe_temp);
            probehum = view.findViewById(R.id.probe_hum);
            deviceaddre = view.findViewById(R.id.device_addre);
            probename = view.findViewById(R.id.probe_name);
            dataleft = view.findViewById(R.id.data_left);
            dataright = view.findViewById(R.id.data_right);
            devicesymbol = view.findViewById(R.id.device_symbol);
            lineChart = view.findViewById(R.id.lineChart);

            dataView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener!=null){
                onItemClickListener.OnItemClick(view,getAdapterPosition());
            }
        }
    }


    //自定义view
    public interface OnItemClickListener{
        void OnItemClick(View view,int pos);
    }

}
