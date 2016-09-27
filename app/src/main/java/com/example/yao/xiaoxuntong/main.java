package com.example.yao.xiaoxuntong;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yao.Fragment.tongxun_fragment;
import com.example.yao.Fragment.wode_fragment;
import com.example.yao.Fragment.xiaoxi_fragment;
import com.example.yao.adapter.vpAdapter;
import com.example.yao.adapter.xiaoxiAdapter;
import com.example.yao.pojo.youMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(value = R.layout.activity_main)
public class main extends AppCompatActivity {
    @ViewInject(value = R.id.vp)
    private ViewPager vp;
    List<Fragment> list;
    public static  String userId;
    public static  int flag_u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        View view=new View(this);
        view.setId(R.id.xiaoxi_imag);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        flag_u=intent.getIntExtra("flag",0);
        setSelect(view);
        list=new ArrayList<Fragment>();
        list.add(new xiaoxi_fragment());
        list.add(new tongxun_fragment());
        list.add(new wode_fragment());
        vp.setAdapter(new vpAdapter(getSupportFragmentManager(),list));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setPostion(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Event(value = {R.id.xiaoxi_btn,R.id.tongzhi_btn,R.id.xiaoxi_imag,R.id.tongxun_imag,R.id.wode_imag},type = View.OnClickListener.class)
    private void onClick(View view){
         setSelect(view);
        switch (view.getId()){
            case R.id.xiaoxi_imag:{
                vp.setCurrentItem(0);
            }
            break;
            case R.id.tongxun_imag:{
                vp.setCurrentItem(1);
            }
            break;
            case R.id.wode_imag:{
                vp.setCurrentItem(2);
            }
            break;
        }
    }
    @ViewInject(value = R.id.xiaoxi_imag)
    private ImageView xiaoxi_imag;
    @ViewInject(value = R.id.tongxun_imag)
    private ImageView tongxun_imag;
    @ViewInject(value = R.id.wode_imag)
    private ImageView wode_imag;
    public void setSelect(View view){
        xiaoxi_imag.setSelected(view.getId()==R.id.xiaoxi_imag);
        tongxun_imag.setSelected(view.getId()==R.id.tongxun_imag);
        wode_imag.setSelected(view.getId()==R.id.wode_imag);
    }
    public void setPostion(int postion){
        xiaoxi_imag.setSelected(postion==0);
        tongxun_imag.setSelected(postion==1);
        wode_imag.setSelected(postion==2);
    }
}
