package com.zcheng.layout.scrolllayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;

import com.zcheng.layout.R;

/**
 * Created by User on 2016/8/9.
 */
public class ScrollActivity extends Activity{

    private MyScrollView scrollView;

    private WindowManager mWindowManager;
    private int screenWidth;

    private LinearLayout myImage;

    private int imgHeight;
    private int imgTop;
    private int scrTop;

    private View mView;

    private LayoutParams suspendLayoutParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scrolllayout);

        scrollView= (MyScrollView) findViewById(R.id.scrollView);
        myImage= (LinearLayout) findViewById(R.id.myImage);

        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = mWindowManager.getDefaultDisplay().getWidth();

        scrollView.setonScrollListener(new MyScrollView.onScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                Log.i("aaa", "" + scrollY);
                if (scrollY >= imgTop) {

                    if(mView==null)
                    showImg();
                } else if (scrollY <= imgTop + imgHeight) {

                    if(mView!=null)
                    hideImg();
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){

            imgHeight=myImage.getHeight();
            imgTop=myImage.getTop();

            scrTop=scrollView.getTop();
        }
    }

    public void showImg(){
        if(mView==null){

            mView= LayoutInflater.from(this).inflate(R.layout.layout_xf_item,null);

            if(suspendLayoutParams==null) {
                suspendLayoutParams = new LayoutParams();
                suspendLayoutParams.type = LayoutParams.TYPE_PHONE; //悬浮窗的类型，一般设为2002，表示在所有应用程序之上，但在状态栏之下
                suspendLayoutParams.format = PixelFormat.RGBA_8888;
                suspendLayoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | LayoutParams.FLAG_NOT_FOCUSABLE;  //悬浮窗的行为，比如说不可聚焦，非模态对话框等等
                suspendLayoutParams.gravity = Gravity.TOP;  //悬浮窗的对齐方式
                suspendLayoutParams.width = screenWidth;
                suspendLayoutParams.height = imgHeight;
                suspendLayoutParams.x = 0;  //悬浮窗X的位置
                suspendLayoutParams.y = scrTop;  ////悬浮窗Y的位置
            }
        }

        mWindowManager.addView(mView,suspendLayoutParams);
    }

    public void hideImg(){
        if(mView!=null){
            mWindowManager.removeView(mView);
            mView=null;
        }
    }
}
