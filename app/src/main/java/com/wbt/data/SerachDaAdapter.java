package com.wbt.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wbt.R;
import com.wbt.bean.DataBean;


/**
 * Created by rnd on 2018/2/1.
 *
 */

public class SerachDaAdapter extends RecyclerView.Adapter<SerachDaAdapter.ViewHolder>{

    private Context context;

    private OnItemClickListener onItemClickListener;

    public DataBean dataBean;

    public SerachDaAdapter(Context context,DataBean dataBean,OnItemClickListener onItemClickListener){
        this.context = context;
        this.dataBean = dataBean;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.serach_ac_item,parent,false);
        final ViewHolder holder = new ViewHolder(view,onItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            if (dataBean != null) {
                DataBean.DatasBean datasBean = dataBean.getDatas().get(position);
                if (datasBean.getProbe().equals("zero")) {
                    holder.llleft.setVisibility(View.GONE);
                    //holder.dataright.setVisibility(View.GONE);
                    //holder.nodata.setVisibility(View.VISIBLE);
                } else {
                    //String temp = NormalUtil.saveOneDecimalPoint(datasBean.getTemp()+"");
                    //holder.probetemp.setText(temp);
                    //holder.probehum.setText(datasBean.getHum());
                    /*if(dataBean.getDevicesymbol().equals("0")) {
                        holder.devicesymbol.setText("°C");
                    }else{
                        holder.devicesymbol.setText("°F");
                    }*/
                    holder.txtProbename.setText(datasBean.getProbe());
                    holder.txtDeviceaddre.setText(dataBean.getDeviceaddre());
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

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View serachView;
        TextView txtProbename;
        TextView txtDeviceaddre;

        LinearLayout llleft;

        private OnItemClickListener onItemClick;

        public ViewHolder(View view,OnItemClickListener onItemClickListener){
            super(view);
            serachView = view;
            onItemClick = onItemClickListener;
            txtProbename = view.findViewById(R.id.probe_name);
            txtDeviceaddre = view.findViewById(R.id.device_addre);
            llleft = view.findViewById(R.id.ll_left);

            serachView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            if(onItemClick!=null){
                onItemClick.OnItemClick(v,getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(View view,int pos);
    }
}
