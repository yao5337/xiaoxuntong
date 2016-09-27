package com.example.yao.MyDialog;

import android.app.Dialog;
import android.content.Context;

import com.example.yao.xiaoxuntong.R;
import com.example.yao.xiaoxuntong.login;

/**
 * Created by 89551 on 2016-09-13.
 */
public class MyDialog extends Dialog{
    public MyDialog(Context context) {
        super(context);
        setContentView(R.layout.progress);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
