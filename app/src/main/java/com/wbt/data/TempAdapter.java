package com.wbt.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wbt.R;
import com.wbt.bean.TempBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 *
 */

public class TempAdapter extends RecyclerView.Adapter<TempAdapter.ViewHolder>{
    private Context context;
    public List<TempBean.TempsBean> tempList;

    public TempAdapter(Context mcontext,List<TempBean.TempsBean> list){
        this.context = mcontext;
        tempList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context = parent.getContext();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_fg_item,parent,false);

        final TempAdapter.ViewHolder holder = new TempAdapter.ViewHolder(view);
        //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
        (holder.tempView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "onClick:" + tempList.get(holder.getAdapterPosition()).getTpname(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onClick() called with: v = [" + v + "]");
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TempBean.TempsBean tempsBean = tempList.get(position);
        if(tempsBean.getTpsymbol()!=null&&!tempsBean.getTpsymbol().isEmpty()) {
            int symbol = Integer.parseInt(tempsBean.getTpsymbol());
            if (symbol == 0) {
                holder.txtSymbol.setText("°C");
            } else {
                holder.txtSymbol.setText("°F");
            }
        }

        holder.txtTemp.setText(tempsBean.getTptemp());
        holder.txtHum.setText(tempsBean.getTphum());
        holder.txtAddre.setText(tempsBean.getTpdvid());

    }

    @Override
    public int getItemCount() {
        return tempList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View tempView;
        TextView txtTemp;
        TextView txtSymbol;
        TextView txtHum;
        TextView txtAddre;
        public ViewHolder(View view){
            super(view);
            tempView = view;
            //txtTemp = view.findViewById(R.id.txtTemp);
            //txtSymbol = view.findViewById(R.id.txtSymbol);
           // txtHum = view.findViewById(R.id.txtHum);
            //txtAddre = view.findViewById(R.id.txtAddre);
        }
    }
}
