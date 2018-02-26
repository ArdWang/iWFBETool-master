package com.wbt.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wbt.R;

import java.util.List;
import java.util.Map;

public class SearchTempAdapter extends BaseAdapter {
	private Context context;		//上下文对象引用
	private List<Map<String,String>> templist;
	private ViewHolder holder;
	private LayoutInflater mInflater;


	public SearchTempAdapter(Context context, List<Map<String,String>> templist){
		this.context = context;
		this.templist = templist;
		this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		try{
			if(convertView==null){
				convertView = mInflater.inflate(R.layout.serach_item, null);
				holder = new ViewHolder();
				holder.tempwendu = convertView.findViewById(R.id.temp_wendu);
				holder.temptime = convertView.findViewById(R.id.temp_time);
				holder.tempname =  convertView.findViewById(R.id.temp_name);
				holder.tempaddre =  convertView.findViewById(R.id.temp_addre);
				holder.tempwenduname =  convertView.findViewById(R.id.temp_wendu_name);
				holder.temphum = convertView.findViewById(R.id.temp_hum);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}

			if(templist.size()<=position){
				return convertView;
			}
			Map<String,String> map = templist.get(position);
			holder.tempname.setText(map.get("name"));
			if(map.get("temp").equals("999")){
				holder.tempwendu.setText("HHH");
			}else if(map.get("temp").equals("-999")){
				holder.tempwendu.setText("LLL");
			}else if(map.get("temp").equals("1000")){
				holder.tempwendu.setText("---");
			}else{
				holder.tempwendu.setText(map.get("temp"));
			}
			holder.temptime.setText(map.get("time"));
			holder.tempaddre.setText(map.get("addre"));
			holder.tempwenduname.setText(map.get("symbol"));
			//新增湿度
			holder.temphum.setText(map.get("hum"));

		}catch(Exception ex){
			ex.printStackTrace();
		}
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return templist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return templist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	class ViewHolder {
		// 所有控件对象引用
		public TextView tempwendu;
		public TextView temptime;
		public TextView tempname;
		private TextView temphum;
		public TextView tempaddre;
		private TextView tempwenduname;

	}
}
