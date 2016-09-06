package com.zcheng.layout.viewpagerlayout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by Zcengcheng on 2016/8/12.
 */
public class HoriontalViewPager extends ViewPager {

    private int mTouchSlop;
    private float mDownX;
    private float mDownY;

    public HoriontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HoriontalViewPager(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(ev);
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mDownX = x;
                mDownY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                float dx = Math.abs(x - mDownX);
                float dy = Math.abs(y - mDownY);

                if (!intercept && dx > mTouchSlop && dx * 0.5f > dy) {
                    intercept = true;
                }
                break;
            default:
                break;
        }
        return intercept;
    }
}
