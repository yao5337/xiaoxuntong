package com.example.yao.xiaoxuntong;

import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.example.yao.MyDialog.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

@ContentView(value = R.layout.activity_wangjimima)
public class wangjimima extends AppCompatActivity {

    @ViewInject(value = R.id.rg_wang)
    private RadioGroup rg;
    @ViewInject(value = R.id.id_wang)
    private EditText id;
    @ViewInject(value = R.id.phone_wang)
    private EditText phone;
    @ViewInject(value = R.id.get_wang)
    private EditText yanzheng;
    @ViewInject(value = R.id.pass_wang)
    private EditText pass;
    @ViewInject(value = R.id.queren_wang)
    private Button queren;
    @ViewInject(value = R.id.getB_wang)
    private Button getButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        SMSSDK.initSDK(this, "17167728d9db0", "78e19e91f3c277928eba5b8fe504bae1");
        setTitle("修改密码");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        dialog=new MyDialog(this);
        EventHandler eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj=data;
                getMessage.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }
    Handler getMessage=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int result = msg.arg2;
            int event = msg.arg1;
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    JSONObject object = new JSONObject();
                    try {
                        object.put("flag",4);
                        object.put("userid",Xid);
                        object.put("type",type);
                        object.put("phone",phoneN);
                        object.put("pass",Npass);
                        RequestParams params = new RequestParams("http://10.2.130.169:8080/xiaoxuntong/servlet/MyServlet");
                        params.addBodyParameter("params",object.toString());
                        x.http().post(params, new org.xutils.common.Callback.CommonCallback<JSONObject>() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                dialog.dismiss();
                                try {
                                    boolean success = result.getBoolean("success");
                                    if (success){
                                        Toast.makeText(wangjimima.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(wangjimima.this, "信息错误", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                dialog.dismiss();
                                Toast.makeText(wangjimima.this, "网络错误", Toast.LENGTH_SHORT).show();
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


                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    Toast.makeText(wangjimima.this, "获取成功", Toast.LENGTH_SHORT).show();

                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                dialog.dismiss();
                Toast.makeText(wangjimima.this, "提交失败", Toast.LENGTH_SHORT).show();
                getButton.setSelected(false);
                time.onFinish();
            }
        }
    };

    int i =60;
    int type;
    String yanzhengN;
    String Xid;
    String Npass;
    String phoneN;
    MyDialog dialog;
    MyTime time;
    @Event(value = {R.id.getB_wang,R.id.queren_wang},type = View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.getB_wang:{
                phoneN = phone.getText().toString();
                if (phoneN.isEmpty()){
                    Toast.makeText(wangjimima.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }else {
                    SMSSDK.getVerificationCode("86",phoneN);
                    time = new MyTime(60000,1000);
                    time.start();
                }
            }
            break;
            case R.id.queren_wang:{

                yanzhengN = yanzheng.getText().toString();
                Xid = id.getText().toString();
                Npass = pass.getText().toString();
                int i = rg.getCheckedRadioButtonId();
                switch (i){
                    case R.id.s_wang:{
                        type=1;
                    }
                    break;
                    case R.id.t_wang:{
                        type=2;
                    }
                    break;
                }
                if (Xid.isEmpty()||phoneN.isEmpty()||yanzhengN.isEmpty()||Npass.isEmpty()){
                    Toast.makeText(wangjimima.this, "请输入信息", Toast.LENGTH_SHORT).show();
                }else {
                    dialog.show();
                    SMSSDK.submitVerificationCode("86",phoneN,yanzhengN);
                }
            }
            break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                overridePendingTransition(R.anim.fanhui_in,R.anim.fanhui_out);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fanhui_in,R.anim.fanhui_out);
    }

    public class MyTime extends CountDownTimer {
        public MyTime(long i, long i1) {
            super(i,i1);
        }

        @Override
        public void onTick(long l) {
            getButton.setSelected(true);
            getButton.setText(l / 1000 + "秒后重新发送");
            getButton.setClickable(false);
        }

        @Override
        public void onFinish() {
            getButton.setSelected(false);
            getButton.setText("获取验证码");
            getButton.setClickable(true);
        }
    }

}
