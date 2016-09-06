package com.zcheng.layout.myscrolllayout;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by User on 2016/8/22.
 */
public class MyLinearLayout extends ViewGroup {

    private int mSlop;

    private float minterceptX = 0;
    private float minterceptY = 0;

    private float interceptX = 0;
    private float interceptY = 0;

    private int mLeft;
    private int mRight;

    private Scroller mScroller;

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        //创建实例
        mScroller = new Scroller(context);
        ViewConfiguration mViewConfiguration = ViewConfiguration.get(context);
        //在构造函数中获取了最小滑动距离TouchSlop
        mSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(mViewConfiguration);
    }

    /*首先。
    重写onInterceptTouchEvent拦截事件，
    记录当前坐标。点下时，默认不拦截，只有当滑动还未完成的情况下，
    才继续拦截。在移动时，对滑动冲突进行了处理，当水平方向的移动距离大于竖直方向的移动距离，
    并且移动距离大于最小滑动距离时，我们判断此时为水平滑动，拦截事件自己处理；
    否则不拦截，交由子View处理。提起手指时，同样不拦截事件。

    重写onTouchEvent处理事件，记录当前坐标。在手指按下时，
    与拦截事件时做相似处理。在ACTION_MOVE时，向左滑动，如果滑动距离超过左边界，
    则对滑动距离进行处理，相对的滑动距离超出又边界，也是一样处理，
    之后把滑动的距离交给scrollBy进行处理。当手指抬起时，
    根据当前的滚动值来判定应该滚动到哪个子控件的界面，
    然后使用scrollTo滑动到那个子控件。


    重写了onMeasure和onLayout方法，
    在onMeasure中测量每一个子控件的大小值，
    在onLayout中对每一个子view在水平方向上进行布局。
    子view的layout的right增加父类的paddingLeft参数
    ，来处理设置padding的情况。*/
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;

        float xintercept = event.getX();
        float yintercept = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:

                float detalX = xintercept - minterceptX;
                float detalY = yintercept - minterceptY;

                if (Math.abs(detalX) > Math.abs(detalY) && Math.abs(detalX) > mSlop) {
                    intercept = true;
                    Log.i("aaa", "2222222222");
                } else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }

        interceptX = xintercept;
        interceptY = yintercept;
        minterceptX = xintercept;
        minterceptY = yintercept;

        return intercept;
    }

    private float detalX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                detalX = touchX - minterceptX;
                float detalY = touchY - minterceptY;
                float scrollStart = touchX;

                //如果超出边界，则把滑动距离缩小到1/3
                if (getScrollX() - detalX < mLeft) {

                    scrollStart = getScrollX() - mLeft;
//                    scrollStart = detalX / 3;
                } else if (getScrollX() + getWidth() - detalX > mRight) {
                    scrollStart = mRight - getWidth() - getScrollX();
//                    scrollStart = detalX / 3;
                }


                scrollBy((int) -scrollStart, 0);
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targeIndex = (getScrollX() + getWidth() / 2) / getWidth();

                if(targeIndex>getChildCount()-1){
                    targeIndex=getChildCount()-1;
                }else if(targeIndex<0) {
                    targeIndex=0;
                }

                //如果超过右边界，则回弹到最后一个View
                int dx = targeIndex * getWidth() - getScrollX();
//                scrollTo(getScrollX() + dx, 0);
                smoothScrollTo(dx);
                break;

        }

        minterceptX = touchX;
        minterceptY = touchY;

        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {

                View child = getChildAt(i);
                child.layout(i * getMeasuredWidth(), 0, i * getMeasuredWidth() + child.getMeasuredWidth() + getPaddingLeft(), child.getMeasuredWidth());
            }

            mLeft = 0;
            mRight = getChildCount() * getMeasuredWidth();
        }
    }

    /*创建Scroller实例
* 2、使用startScroll方法，对其进行初始化
* 3、重写computeScroll()方法，在其内部调用scrollTo或ScrollBy方法，完成滑动过程。*/
    private void smoothScrollTo(int dex) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();

        mScroller.startScroll(scrollX, 0, dex, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {

            int currX = mScroller.getCurrX();
            int currY = mScroller.getCurrY();

            scrollTo(currX, currY);
            postInvalidate();
        }
    }
}

