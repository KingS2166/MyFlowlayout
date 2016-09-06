package com.zcheng.layout.scrolllayout;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by User on 2016/8/9.
 */
public class MyScrollView extends ScrollView{

    private onScrollListener mScrollListener;
    private float mlastScroll;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * 设置滚动接口
     * @param onScrollListener
     */
    public void setonScrollListener(onScrollListener mScrollListener){

        this.mScrollListener=mScrollListener;
    }

    /**
     * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
     */
    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            if(mlastScroll!=getScrollY()){

                mlastScroll = getScrollY();
                mHandler.sendMessageDelayed(mHandler.obtainMessage(),5);
            }

            if(mScrollListener!=null){
                mScrollListener.onScroll(getScrollY());
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if(mScrollListener!=null){
            mScrollListener.onScroll(getScrollY());
        }

        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:


                break;
        }
        return super.onTouchEvent(ev);
    }

    public interface onScrollListener{
        void onScroll(int scrollY);
    }
}
