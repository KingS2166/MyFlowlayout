package com.zcheng.layout.flowlayout;

import android.graphics.drawable.GradientDrawable;

/**
 * Created by User on 2016/7/14.
 */
public class GridientDrawableUtil{

    public static GradientDrawable setText(int rgb,float conerRadius){

        GradientDrawable gd=new GradientDrawable();
        gd.setColor(rgb);//颜色
        gd.setStroke(1, 0x001);//边框
        gd.setGradientType(GradientDrawable.RECTANGLE);//形状
        gd.setCornerRadius(conerRadius);//圆角

        return gd;
    }
}
