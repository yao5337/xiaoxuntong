package com.example.yao.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yao.pojo.youMessage;
import com.example.yao.xiaoxuntong.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 89551 on 2016-09-23.
 */
public class xiaoxiAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private List<youMessage> list;
    public xiaoxiAdapter(Context context, List<youMessage> list) {
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
            holder=new viewHolder();
            view=inflater.inflate(R.layout.item_xiaoxi,null);
            holder.youname= (TextView) view.findViewById(R.id.youname);
            holder.content= (TextView) view.findViewById(R.id.youcontent);
            holder.time= (TextView) view.findViewById(R.id.time);
            view.setTag(holder);
        }else {
            holder= (viewHolder) view.getTag();
        }
        holder.youname.setText(list.get(i).getName());
        long time = list.get(i).getTime();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format1 = format.format(date);
        holder.time.setText(format1);
        return view;
    }
    public class viewHolder{
        TextView youname;
        TextView content;
        TextView time;
    }
}
