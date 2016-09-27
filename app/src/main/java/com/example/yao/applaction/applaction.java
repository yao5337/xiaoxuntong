package com.example.yao.applaction;

import android.app.Application;

import org.xutils.x;

/**
 * Created by 89551 on 2016-09-07.
 */
public class applaction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
