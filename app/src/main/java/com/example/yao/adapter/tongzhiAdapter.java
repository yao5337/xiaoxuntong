package com.example.yao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yao.pojo.youMessage;
import com.example.yao.xiaoxuntong.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 89551 on 2016-09-23.
 */
public class tongzhiAdapter extends BaseAdapter {
    private List<youMessage> list;
    LayoutInflater inflater;
    public tongzhiAdapter(Context context, List<youMessage> list) {
        inflater = LayoutInflater.from(context);
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder=null;
        if (view==null){
            view=inflater.inflate(R.layout.item_tongzhi,null);
            holder=new viewHolder();
            holder.time= (TextView) view.findViewById(R.id.time_tongzhi);
            holder.title= (TextView) view.findViewById(R.id.title_tongzhi);
            holder.content= (TextView) view.findViewById(R.id.content_tongzhi);
            view.setTag(holder);
        }else {
            holder= (viewHolder) view.getTag();
        }
        long time = list.get(i).getTime();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String s = format.format(date);
        holder.time.setText(s);
        holder.title.setText(list.get(i).getName());
        holder.content.setText(list.get(i).getClazz());
        return view;
    }
    public class viewHolder{
        TextView time;
        TextView title;
        TextView content;
    }
}
