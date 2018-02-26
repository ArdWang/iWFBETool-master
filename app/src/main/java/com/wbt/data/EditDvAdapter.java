package com.wbt.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.wbt.R;
import com.wbt.bean.DeviceBean;
import java.util.List;

/**
 * Created by rnd on 2018/1/30.
 *
 */
public class EditDvAdapter extends RecyclerView.Adapter<EditDvAdapter.ViewHolder>{

    private Context context;

    public List<DeviceBean.DevicesBean> deviceslist;

    private OnDragClickListener onDragClickListener;

    public EditDvAdapter(Context context,List<DeviceBean.DevicesBean> deviceslist,
                         OnDragClickListener onDragClickListener){

        this.context = context;
        this.deviceslist = deviceslist;
        this.onDragClickListener = onDragClickListener;
    }

    @Override
    public EditDvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_dc_fg_item,parent,false);
        final EditDvAdapter.ViewHolder holder = new EditDvAdapter.ViewHolder(view,onDragClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(EditDvAdapter.ViewHolder holder, int position) {
        DeviceBean.DevicesBean devicesBean = deviceslist.get(position);
        if(devicesBean!=null){
            holder.devtitle.setText(devicesBean.getDevicename());
            holder.devaddre.setText(devicesBean.getDeviceaddre());
            //holder.txtDeviceonline.setText("Online");
        }
    }

    @Override
    public int getItemCount() {
        return deviceslist.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View devview;
        //ImageView msgimg;
        TextView devtitle;
        TextView devaddre;
        //TextView msgisred;

        //LinearLayout msgcon;
        Button btnDelete;
        Button btnEdit;

        private OnDragClickListener onClickListener;

        public ViewHolder(View view,OnDragClickListener onDragClickListener){
            super(view);
            devview = view;

            onClickListener = onDragClickListener;

            //msgimg = (ImageView) view.findViewById(R.id.msg_image);
            devtitle = view.findViewById(R.id.dev_title);
            devaddre = (TextView)view.findViewById(R.id.dev_addre);
            //msgisred = (TextView) view.findViewById(R.id.msg_isred);

            //msgcon = view.findViewById(R.id.msg_con);
            btnEdit = view.findViewById(R.id.btnEdit);
            btnDelete = view.findViewById(R.id.btnDelete);

            btnEdit.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnEdit:
                    if(onClickListener!=null){
                        onClickListener.onEditClick(v,getAdapterPosition());
                    }
                    break;

                case R.id.btnDelete:
                    if(onClickListener!=null){
                        onClickListener.onDeleteClick(v,getAdapterPosition());
                    }
                    break;
            }
        }
    }

    //自定义拖动接口
    public interface OnDragClickListener{
        void onEditClick(View view,int pos);
        void onDeleteClick(View view,int pos);
    }

}
