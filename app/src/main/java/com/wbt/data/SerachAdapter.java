package com.wbt.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wbt.R;
import com.wbt.bean.DeviceBean;
import java.util.List;

/**
 * Created by rnd on 2018/2/1.
 *
 */

public class SerachAdapter extends RecyclerView.Adapter<SerachAdapter.ViewHolder>{
    private Context context;
    public List<DeviceBean.DevicesBean> devicesList;
    private OnItemClickListener onItemClickListener;

    public SerachAdapter(Context context,List<DeviceBean.DevicesBean> devicesList,OnItemClickListener onItemClickListener){
        this.context = context;
        this.devicesList = devicesList;
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.serach_fg_item,parent,false);
        final SerachAdapter.ViewHolder holder = new SerachAdapter.ViewHolder(view,onItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(SerachAdapter.ViewHolder holder, int position) {
        DeviceBean.DevicesBean devicesBean = devicesList.get(position);
        if(devicesBean!=null){
            holder.txtDevicename.setText(devicesBean.getDevicename());
            holder.txtDeviceaddre.setText(devicesBean.getDeviceaddre());
            //holder.txtDeviceonline.setText("Online");
        }
    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View serachView;
        TextView txtDevicename;
        TextView txtDeviceaddre;
        //TextView txtDeviceonline;

        private OnItemClickListener onItemClick;

        public ViewHolder(View view,OnItemClickListener onItemClickListener){
            super(view);
            serachView = view;
            onItemClick = onItemClickListener;
            txtDevicename = view.findViewById(R.id.device_name);
            txtDeviceaddre = view.findViewById(R.id.device_addre);
            //txtDeviceonline = view.findViewById(R.id.device_online);

            serachView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick!=null){
                onItemClick.OnItemClick(view,getAdapterPosition());
            }
        }
    }

    //自定义view
    public interface OnItemClickListener{
        void OnItemClick(View view,int pos);
    }



}
