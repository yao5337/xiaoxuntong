package com.example.yao.xiaoxuntong;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yao.MyDialog.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
@ContentView(value = R.layout.activity_login)
public class login extends AppCompatActivity {

    @ViewInject(value = R.id.user)
    private EditText user;
    @ViewInject(value = R.id.pass)
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setTitle("校讯通");
        View view = new View(this);
        flag=1;
        view.setId(R.id.student_l);
        setSelect(view);
    }
    int flag;
    String user_s;
    String pass_s;
    @Event(value = {R.id.student_l,R.id.teacher_l,R.id.login,R.id.wangji_l,R.id.zhuce_l},type = View.OnClickListener.class)
    private void OnClick(View view){
        setSelect(view);
        switch (view.getId()){
            case R.id.student_l:{
                flag=1;
            }
            break;
            case R.id.teacher_l:{
                flag=2;
            }
            break;
            case R.id.login:{
                final MyDialog dialog = new MyDialog(this);
                user_s=user.getText().toString();
                pass_s = pass.getText().toString();
                if (user_s.isEmpty()||pass_s.isEmpty()){
                    Toast.makeText(login.this, "请输入账号或密码", Toast.LENGTH_SHORT).show();
                }else  {
                    dialog.show();
                    RequestParams params = new RequestParams("http://10.2.130.169:8080/xiaoxuntong/servlet/MyServlet");
                    JSONObject object = new JSONObject();
                    try {
                        object.put("flag",flag);
                        object.put("userId",user_s);
                        object.put("pass",pass_s);
                        params.addBodyParameter("params",object.toString());
                        x.http().post(params, new Callback.CommonCallback<JSONObject>() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                try {
                                    dialog.dismiss();
                                    boolean success = result.getBoolean("success");
                                    if (success){
                                        Intent intent = new Intent(login.this,main.class);
                                        intent.putExtra("userId",user_s);
                                        intent.putExtra("flag",flag);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(login.this, "输入错误", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                dialog.dismiss();
                                Toast.makeText(login.this, "网络错误", Toast.LENGTH_SHORT).show();
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
            }
            break;
            case R.id.zhuce_l:{
                Intent it = new Intent(this,zhuce.class);
                startActivity(it);
                overridePendingTransition(R.anim.in,R.anim.out);
            }
            break;
            case R.id.wangji_l:{
                Intent it = new Intent(this,wangjimima.class);
                startActivity(it);
                overridePendingTransition(R.anim.in,R.anim.out);
            }
            break;
        }
    }

    @ViewInject(value = R.id.student_l)
    private TextView student;
    @ViewInject(value = R.id.teacher_l)
    private TextView teacher;
    public void setSelect(View view){
        student.setSelected(view.getId()==R.id.student_l);
        teacher.setSelected(view.getId()==R.id.teacher_l);
        if (student.isSelected()){
            student.setTextColor(Color.WHITE);
        }else {
            student.setTextColor(0xff989898);
        }
        if (teacher.isSelected()){
            teacher.setTextColor(Color.WHITE);
        }else {
            teacher.setTextColor(0xff989898);
        }
    }

}
