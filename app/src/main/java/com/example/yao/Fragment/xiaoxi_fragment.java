package com.example.yao.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.yao.adapter.tongzhiAdapter;
import com.example.yao.adapter.xiaoxiAdapter;
import com.example.yao.pojo.youMessage;
import com.example.yao.xiaoxuntong.R;
import com.example.yao.xiaoxuntong.chat;
import com.example.yao.xiaoxuntong.main;
import com.example.yao.xiaoxuntong.tongzhi_add;
import com.example.yao.xiaoxuntong.xiaoxi_add;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 89551 on 2016-09-21.
 */
public class xiaoxi_fragment extends Fragment implements View.OnClickListener {
    private RadioButton xiao,tong;
    private ImageView add;
    List<youMessage> list;
    private ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xiaoxi, null);
        xiao = (RadioButton) view.findViewById(R.id.xiaoxi_btn);
        tong = (RadioButton) view.findViewById(R.id.tongzhi_btn);
        add= (ImageView) view.findViewById(R.id.add_wo);
        lv = (ListView) view.findViewById(R.id.lv_xiaoxi);
        list=new ArrayList<youMessage>();
        xiao.setOnClickListener(this);
        tong.setOnClickListener(this);
        add.setOnClickListener(this);
        xiaoxiHttp();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xiao.isSelected()){
                    Intent it = new Intent(getActivity(),xiaoxi_add.class);
                    startActivity(it);
                }else {
                    if (main.flag_u==1){
                        Intent it = new Intent(getActivity(),xiaoxi_add.class);
                        startActivity(it);
                    }else if (main.flag_u==2){
                        Intent it = new Intent(getActivity(),tongzhi_add.class);
                        startActivity(it);
                    }
                }
            }
        });
        return view;
    }

    public void selected(View view){
        xiao.setSelected(view.getId()==R.id.xiaoxi_btn);
        tong.setSelected(view.getId()==R.id.tongzhi_btn);
    }

    @Override
    public void onClick(View view) {
        selected(view);
        switch (view.getId()){
            case R.id.xiaoxi_btn:{
               xiaoxiHttp();
            }
            break;
            case R.id.tongzhi_btn:{
                try {
                    tongzhiHttp();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

        }
    }

    public void xiaoxiHttp(){
        RequestParams params = new RequestParams("http://10.2.130.169:8080/xiaoxuntong/servlet/MyServlet");
        JSONObject object = new JSONObject();
        try {
            object.put("flag",5);
            object.put("userId", main.userId);
            params.addBodyParameter("params",object.toString());
            x.http().post(params, new Callback.CommonCallback<JSONObject>() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        list.clear();
                        JSONArray success = result.getJSONArray("success");
                        for (int i = 0 ;i<success.length();i++){
                            youMessage message = new youMessage();
                            JSONObject jsonObject = success.getJSONObject(i);
                            message.setName(jsonObject.getString("youName"));
                            message.setTime(jsonObject.getJSONObject("time").getLong("time"));
                            list.add(message);
                        }
                        xiaoxiAdapter adapter = new xiaoxiAdapter(getContext(),list);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(getActivity(),chat.class);
                                intent.putExtra("yname",list.get(i).getName());
                                startActivity(intent);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void tongzhiHttp() throws JSONException {
        RequestParams params = new RequestParams("http://10.2.130.169:8080/xiaoxuntong/servlet/MyServlet");
        JSONObject object = new JSONObject();
        object.put("flag",6);
        params.addBodyParameter("params",object.toString());
        x.http().post(params, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    list.clear();
                    JSONArray success = result.getJSONArray("success");
                    for (int i = 0 ;i<success.length();i++){
                        youMessage message = new youMessage();
                        JSONObject jsonObject = success.getJSONObject(i);
                        message.setId(jsonObject.getString("name"));
                        message.setName(jsonObject.getString("title"));
                        message.setClazz(jsonObject.getString("content"));
                        message.setTime(jsonObject.getJSONObject("time").getLong("time"));
                        list.add(message);
                    }
                    tongzhiAdapter adapter = new tongzhiAdapter(getContext(),list);
                    lv.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
