package com.example.yao.xiaoxuntong;

import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.yao.MyDialog.MyDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
@ContentView(value = R.layout.activity_zhuce)
public class zhuce extends AppCompatActivity {
    @ViewInject(value = R.id.xingming)
    private EditText xingming;
    @ViewInject(value = R.id.xuehao)
    private EditText xuehao;
    @ViewInject(value = R.id.banji)
    private EditText banji;
    @ViewInject(value = R.id.dianhua)
    private EditText dianhua;
    @ViewInject(value = R.id.mima)
    private EditText mima;
    @ViewInject(value = R.id.mima_c)
    private EditText mima_c;
    @ViewInject(value = R.id.rg)
    private RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setTitle("注册");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    @Event(value = R.id.zhuce_z,type = View.OnClickListener.class)
    private void onClick(View view){
        final MyDialog dialog = new MyDialog(this);
        dialog.show();
        String xing = xingming.getText().toString();
        String id = xuehao.getText().toString();
        String clazz = banji.getText().toString();
        String phone = dianhua.getText().toString();
        String pass = mima.getText().toString();
        String pass_c = mima_c.getText().toString();
        int selected = rg.getCheckedRadioButtonId();
        int type=0;
        switch (selected){
            case R.id.s:{
                type=1;
            }
            break;
            case R.id.t:{
                type=2;
            }
            break;
            default:
                break;
        }

        if (xing.isEmpty()||id.isEmpty()||clazz.isEmpty()||phone.isEmpty()||pass.isEmpty()||pass_c.isEmpty()||type==0){
            Toast.makeText(zhuce.this, "星号内容不可为空", Toast.LENGTH_SHORT).show();

        }else {
            if (pass.equals(pass_c)){
                RequestParams params = new RequestParams("http://10.2.130.169:8080/xiaoxuntong/servlet/MyServlet");
                JSONObject object = new JSONObject();
                try {
                    object.put("flag",3);
                    object.put("xingming",xing);
                    object.put("userid",id);
                    object.put("clazz",clazz);
                    object.put("phone",phone);
                    object.put("pass",pass);
                    object.put("type",type);
                    params.addBodyParameter("params",object.toString());
                    x.http().post(params, new Callback.CommonCallback<JSONObject>() {

                        @Override
                        public void onSuccess(JSONObject result) {
                            dialog.dismiss();
                            try {
                                boolean success = result.getBoolean("success");
                                if (success){
                                    Toast.makeText(zhuce.this, "注册成功", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            dialog.dismiss();
                            Toast.makeText(zhuce.this, "网络错误", Toast.LENGTH_SHORT).show();
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

            }else {
                Toast.makeText(zhuce.this, "两次密码不相同，请重新输入", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fanhui_in,R.anim.fanhui_out);
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

}
