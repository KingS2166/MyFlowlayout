package com.zcheng.layout.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016/7/15.
 */
public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;//总宽度
        int height = 0;//总高度

        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //onMeasure中必须手动计算
            measureChild(childView, getMeasuredWidth(), getMeasuredHeight());
            int widthView = childView.getMeasuredWidth();
            int heightView = childView.getMeasuredHeight();

            //通过generateLayoutParams，可以获取magin
            MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();
            if (lineWidth + widthView + mp.rightMargin + mp.leftMargin > widthSize) {
                //换行
                width = Math.max(lineWidth, width);
                height += lineHeight;

                lineHeight = heightView + mp.topMargin + mp.bottomMargin;
                lineWidth = widthView + mp.rightMargin + mp.leftMargin;
            } else {
                //不换行
                lineWidth += widthView + mp.rightMargin + mp.leftMargin;
                height = Math.max(lineHeight, height);
            }

            //最后一个手动添加
            if (i == childCount - 1) {
                //换行
                width = Math.max(lineWidth, width);
                height += lineHeight;

                lineHeight += heightView + mp.topMargin + mp.bottomMargin;
                lineWidth = widthView + mp.rightMargin + mp.leftMargin;
            }
        }

        Log.i("aaa", "width=" + width + ">>>>height=" + height);
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }

    private ArrayList<List<View>> allWidth = new ArrayList<>();
    private ArrayList<Integer> allHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        List<View> listView = new ArrayList<>();
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //onMeasure中必须手动计算
            measureChild(childView, getMeasuredWidth(), getMeasuredHeight());
            int widthView = childView.getMeasuredWidth();
            int heightView = childView.getMeasuredHeight();

            //通过generateLayoutParams，可以获取magin
            MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();
            if (lineWidth + widthView + mp.rightMargin + mp.leftMargin > width) {
                Log.i("aaa", "huanhang>>>>>>>.");
                //换行
                allWidth.add(listView);
                allHeight.add(lineHeight);

                listView = new ArrayList<>();
                lineWidth = 0;
                lineHeight = heightView + mp.topMargin + mp.bottomMargin;
            } else {
                //不换行

                lineWidth += widthView + mp.rightMargin + mp.leftMargin;
                lineHeight = Math.max(lineHeight, heightView + mp.topMargin + mp.bottomMargin);
                listView.add(childView);
            }

            //最后一个手动添加
            if (i == childCount - 1) {
                allWidth.add(listView);
                allHeight.add(lineHeight);
            }
        }

        int left=0;
        int top=0;

        for (int i = 0; i < allWidth.size(); i++) {
            int height = allHeight.get(i);

            List<View> allView = allWidth.get(i);
            for (View view : allView) {
                int widthView = view.getMeasuredWidth();
                int heightView = view.getMeasuredHeight();
                MarginLayoutParams mp = (MarginLayoutParams) view.getLayoutParams();

                int lc=left+mp.leftMargin;
                int tc=top+mp.topMargin;
                int rc=lc+widthView;
                int bc=tc+heightView;

                view.layout(lc,tc,rc,bc);
                left+=widthView+mp.rightMargin+mp.leftMargin;
            }

            left=0;
            top+=height;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //首先根据该方法获取margin
        MarginLayoutParams mp = new MarginLayoutParams(getContext(), attrs);
        return mp;
    }
}
