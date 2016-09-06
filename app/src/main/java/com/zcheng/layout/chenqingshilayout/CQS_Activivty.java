package com.zcheng.layout.chenqingshilayout;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zcheng.layout.R;

/**
 * Created by User on 2016/8/25.
 */
public class CQS_Activivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cqs);

//        if (Build.VERSION.SDK_INT > 21) {
//            //当前界面的DecorView
//            View decorView = getWindow().getDecorView();
//
//            //SYSTEM_UI_FLAG_FULLSCREEN表示全屏的意思，也就是会将状态栏隐藏
//            //int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
//
//            //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN和SYSTEM_UI_FLAG_LAYOUT_STABLE,主体占据全空间
//            //int option=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//
//            //将状态栏和导航栏同时隐藏
//            int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
//                    View.SYSTEM_UI_FLAG_FULLSCREEN |
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
//
//            //调用它的setSystemUiVisibility()方法来设置系统UI元素的可见性
//            decorView.setSystemUiVisibility(option);
//
//            //设置状态栏透明色
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//
//            //设置导航栏透明
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            Log.i("aaa", "" + getWindow().getStatusBarColor());

//        ActionBar actionbar=getSupportActionBar();
//        actionbar.hide();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
