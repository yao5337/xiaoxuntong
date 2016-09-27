package com.example.yao.pojo;

import java.util.Date;

/**
 * Created by 89551 on 2016-09-23.
 */
public class youMessage {
    private String id;
    private String name;
    private String clazz;
    private int xiaoxi;
    private long time;


    public youMessage(String id, String name, String clazz, int xiaoxi, long time) {
        this.id = id;
        this.name = name;
        this.clazz = clazz;
        this.xiaoxi = xiaoxi;
        this.time = time;
    }

    public youMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public int getXiaoxi() {
        return xiaoxi;
    }

    public void setXiaoxi(int xiaoxi) {
        this.xiaoxi = xiaoxi;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "youMessage{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", clazz='" + clazz + '\'' +
                ", xiaoxi=" + xiaoxi +
                ", time=" + time +
                '}';
    }
}
