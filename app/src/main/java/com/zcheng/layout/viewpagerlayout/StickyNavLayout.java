package com.zcheng.layout.viewpagerlayout;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.zcheng.layout.R;


/**
 * Created by User on 2016/8/15.
 */
public class StickyNavLayout extends LinearLayout implements NestedScrollingParent {

    private OverScroller mScroller;
    private int mTouchSlop;
    private int mMaximumFiling;
    private int mMinimumFiling;
    private View mTop;
    private View lv;
    private int mTopHight;
    private int mListView;
    public static String TAG = "abd";
    private ViewPager viewPager;

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.e(TAG, "onStartNestedScroll");
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        Log.e(TAG, "onNestedScrollAccepted");
    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.e(TAG, "onStopNestedScroll");
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.e(TAG, "onNestedScroll");
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.e(TAG, "onNestedPreScroll");
        boolean hiddenTop = dy > 0 && getScrollY() < mTopHight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.e(TAG, "onNestedFling");
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.e(TAG, "onNestedPreFling");
        //down - //up+
        if (getScrollY() >= mTopHight) return false;
        fling((int) velocityY);
        return true;
    }

    @Override
    public int getNestedScrollAxes() {
        Log.e(TAG, "getNestedScrollAxes");
        return 0;
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopHight);
        invalidate();
    }

    public StickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTop = findViewById(R.id.id_stickynavlayout_topview);
        lv = findViewById(R.id.id_stickynavlayout_indicator);
        View view = findViewById(R.id.id_stickynavlayout_viewpager);
        if (!(view instanceof ViewPager)) {
            throw new RuntimeException("比较这里出错了");
        }
        viewPager = (ViewPager) view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        ViewGroup.LayoutParams lp = viewPager.getLayoutParams();
        lp.height = getMeasuredHeight() - lv.getMeasuredHeight();

        setMeasuredDimension(widthMeasureSpec, mTop.getMeasuredHeight() + viewPager.getMeasuredHeight() + lv.getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTopHight = mTop.getMeasuredHeight();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
            if (NeverHeight == 0) {
                NeverHeight = mTop.getMeasuredHeight();
                Neverwidth = mTop.getMeasuredWidth();
            }
            isScroll = true;
        }
        if (y > mTopHight) {
            y = mTopHight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    private int allSroll = -1;
    private boolean isScroll = false;
    private int NeverHeight = 0;
    private int Neverwidth = 0;

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float downY = 0;
        float lastY = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:

                if (isScroll && NeverHeight + allSroll / 10 <= NeverHeight * 2) {

                    downY = ev.getY();
                    float y = (downY - lastY) / 20;
                    lastY = downY;

                    if (y > 0) {
                        allSroll += y;
                        if (allSroll > 10000) {
                            allSroll = 10000;
                        }

                        Log.i("qqq", "allSroll=" + allSroll);
                        Log.i("qqq", "y=" + y);
                        Log.i("qqq", "NeverHeight=" + NeverHeight + allSroll / 10);

                        if (NeverHeight + allSroll / 10 < NeverHeight * 2) {
                            ViewGroup.LayoutParams lp = mTop.getLayoutParams();
                            lp.height = NeverHeight + allSroll / 10;
                            lp.width = Neverwidth + allSroll / 20;
                            mTop.setLayoutParams(lp);
                            Log.i("www", "理想之中>>>>>>>>");
                        } else {
                            ViewGroup.LayoutParams lp = mTop.getLayoutParams();
                            lp.height = NeverHeight * 2;
                            lp.width = Neverwidth * 2;
                            mTop.setLayoutParams(lp);
                            isScroll = false;
                            Log.i("www", "变大了>>>>>>>>");
                        }
                    }

                } else {
                    Log.i("www", "我就是不走了 咬我呀");
                }
                break;
            case MotionEvent.ACTION_UP:
                if (allSroll != -1 && allSroll > 0) {
                    mHandler.sendEmptyMessageDelayed(1, 10);
                    Log.i("www", "up>>>>>>>>>>");
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            allSroll -= 100;

            if (allSroll < 0) {
                allSroll = -1;
                Log.i("www", "over>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                mHandler.removeMessages(1);
                isScroll = false;
                ViewGroup.LayoutParams lps = mTop.getLayoutParams();
                lps.height = NeverHeight;
                lps.width = Neverwidth;
                mTop.setLayoutParams(lps);
            } else {
                ViewGroup.LayoutParams lp = mTop.getLayoutParams();
                lp.height = (int) (NeverHeight + allSroll / 10);
                lp.width = Neverwidth + allSroll / 20;
                mTop.setLayoutParams(lp);

                mHandler.sendEmptyMessageDelayed(1, 10);
            }
        }
    };

}
