package com.zcheng.layout.viewpagerlayout;

import android.annotation.TargetApi;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.zcheng.layout.R;

import java.util.ArrayList;
import java.util.List;

public class FragActivity extends FragmentActivity {

    private String[] str = {"正宗页卡1", "正宗页卡2", "正宗页卡3", "正宗页卡4"};
    private OneFragment fragOne;
    private TwoFragment fragTwo;
    private FourFragment fragFour;
    private ThreeFragment fragThree;
    private ViewPager viewpager;
    private List<Fragment> listFra;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ImageView mCustom;
    private int old = 0;
    private SimpleViewPagerIndicator sp;
    private View view;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(this).inflate(R.layout.item_recycler, null);
        setContentView(view);
//        showPop(Parent);
//
//        Parent.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPop.showAsDropDown(v);
//            }
//        });
//    }
//
//    private  View view;
//    private PopupWindow mPop;
//    public void showPop(View parent) {
//
//        view = LayoutInflater.from(this).inflate(R.layout.activity_viewpager,Parent,false);
//        DisplayMetrics dm = this.getResources().getDisplayMetrics();
//        int hp=dm.heightPixels;
//        Log.i("aaa",""+hp);
//        mPop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, hp);
//        mPop.setBackgroundDrawable(new BitmapDrawable());
//        mPop.setAnimationStyle(R.style.myAinm);
//        mPop.setFocusable(true);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        listFra = new ArrayList<>();
        listFra.add(new OneFragment());
        listFra.add(new TwoFragment());
        listFra.add(new ThreeFragment());
        listFra.add(new FourFragment());

        viewpager = (ViewPager) view.findViewById(R.id.id_stickynavlayout_viewpager);
        viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), listFra));

        sp = (SimpleViewPagerIndicator) view.findViewById(R.id.sp);
        sp.setTitles(str);

        mCustom = (ImageView) view.findViewById(R.id.ivCustum);
        initPageCard();

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("aa", "position>>>>>" + position);
                Log.i("bb", "positionOffset>>>>>" + positionOffset);
                Log.i("cc", "positionOffsetPixels>>>>>" + positionOffsetPixels);
                changAlpha(position, positionOffset);
                sp.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                changAlpha(position);
                selectFragment(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        view.findViewById(R.id.tvOne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                selectFragment(0);
                viewpager.setCurrentItem(0);
            }
        });

        view.findViewById(R.id.tvTwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                selectFragment(1);
                viewpager.setCurrentItem(1);
            }
        });

        view.findViewById(R.id.tvthree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                selectFragment(2);
                viewpager.setCurrentItem(2);
            }
        });
        view.findViewById(R.id.tvfour).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                selectFragment(3);
                viewpager.setCurrentItem(3);
            }
        });
    }


    public void selectFragment(int index) {

        hideFragment();
        setOffset(index);
        switch (index) {
            case 0:
                view.findViewById(R.id.tvOne).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                view.findViewById(R.id.tvTwo).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view.findViewById(R.id.tvthree).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view.findViewById(R.id.tvfour).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                if (fragOne == null) {
                    fragOne = new OneFragment();
                    fragmentTransaction.replace(R.id.id_stickynavlayout_viewpager, fragOne);
                } else {
                    fragmentTransaction.show(fragOne);
                }
                break;
            case 1:

                view.findViewById(R.id.tvTwo).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                view.findViewById(R.id.tvOne).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view.findViewById(R.id.tvthree).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view.findViewById(R.id.tvfour).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                if (fragTwo == null) {
                    fragTwo = new TwoFragment();
                    fragmentTransaction.replace(R.id.id_stickynavlayout_viewpager, fragTwo);
                } else {
                    fragmentTransaction.show(fragTwo);
                }
                break;
            case 2:

                view.findViewById(R.id.tvthree).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                view.findViewById(R.id.tvOne).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view.findViewById(R.id.tvTwo).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view.findViewById(R.id.tvfour).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                if (fragThree == null) {
                    fragThree = new ThreeFragment();
                    fragmentTransaction.replace(R.id.id_stickynavlayout_viewpager, fragThree);
                } else {
                    fragmentTransaction.show(fragThree);
                }
                break;
            case 3:

                view.findViewById(R.id.tvfour).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                view.findViewById(R.id.tvOne).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view.findViewById(R.id.tvthree).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view.findViewById(R.id.tvTwo).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                if (fragFour == null) {
                    fragFour = new FourFragment();
                    fragmentTransaction.replace(R.id.id_stickynavlayout_viewpager, fragFour);
                } else {
                    fragmentTransaction.show(fragFour);
                }
                break;
        }
    }

    public void hideFragment() {
        if (fragOne != null) {
            fragmentTransaction.hide(fragOne);
        }
        if (fragTwo != null) {
            fragmentTransaction.hide(fragTwo);
        }
        if (fragThree != null) {
            fragmentTransaction.hide(fragThree);
        }
        if (fragFour != null) {
            fragmentTransaction.hide(fragFour);
        }
    }

    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> list = new ArrayList<>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    public void setOffset(int index) {
        int one = offset * 2 + bitmap;
        int two = one * 2;
        int three = one * 3;
        Animation animation = null;

        switch (index) {
            case 0:
                animation = new TranslateAnimation(offset, 0, 0, 0);

                old = offset;
                break;
            case 1:
                animation = new TranslateAnimation(old, one, 0, 0);
                old = one;
                break;
            case 2:
                animation = new TranslateAnimation(old, two, 0, 0);
                old = two;
                break;
            case 3:
                animation = new TranslateAnimation(old, three, 0, 0);
                old = three;
                break;
        }
        animation.setDuration(300);
        animation.setFillAfter(true);
        mCustom.startAnimation(animation);
    }

    private int offset;
    private int bitmap;

    public void initPageCard() {

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a).getWidth();

        DisplayMetrics matrix = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(matrix);
        int height = matrix.widthPixels;
        offset = (height / 4 - bitmap) / 2;

        Matrix matrix1 = new Matrix();
        matrix1.postTranslate(offset, 0);
        mCustom.setImageMatrix(matrix1);
    }

    /**
     * 根据滑动设置透明度
     */
    private void changAlpha(int pos, float posOffset) {
        int nextIndex = pos + 1;
        if (posOffset > 0) {
            //设置tab的颜色渐变效果
//            mButtonList.get(nextIndex).setAlpha(posOffset);
//            mButtonList.get(pos).setAlpha(1 - posOffset);
            //设置fragment的颜色渐变效果
            listFra.get(nextIndex).getView().setAlpha(posOffset);
            listFra.get(pos).getView().setAlpha(1 - posOffset);
            //设置fragment滑动视图由大到小，由小到大的效果
            listFra.get(nextIndex).getView().setScaleX(0.5F + posOffset / 2);
            listFra.get(nextIndex).getView().setScaleY(0.5F + posOffset / 2);
            listFra.get(pos).getView().setScaleX(1 - (posOffset / 2));
            listFra.get(pos).getView().setScaleY(1 - (posOffset / 2));
        }
    }

    //    **
//            * 一开始运行、滑动和点击tab结束后设置tab的透明度，fragment的透明度和大小
//    */
    private void changAlpha(int postion) {
        for (int i = 0; i < 2; i++) {
            if (i == postion) {
//                mButtonList.get(i).setAlpha(1.0f);
                if (null != listFra.get(i).getView()) {
                    listFra.get(i).getView().setAlpha(1.0f);
                    listFra.get(i).getView().setScaleX(1.0f);
                    listFra.get(i).getView().setScaleY(1.0f);
                }
            } else {
//                mButtonList.get(i).setAlpha(0.0f);
                if (null != listFra.get(i).getView()) {
                    listFra.get(i).getView().setAlpha(0.0f);
                    listFra.get(i).getView().setScaleX(0.0f);
                    listFra.get(i).getView().setScaleY(0.0f);
                }
            }
        }
    }
}
