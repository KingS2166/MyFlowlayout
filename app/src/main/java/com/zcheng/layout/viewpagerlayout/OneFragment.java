package com.zcheng.layout.viewpagerlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zcheng.layout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016/6/29.
 */
public class OneFragment extends MyFragment {

    private boolean isPre = false;
    private View view;
    private VercalViewPager viewpager;
    private TwoFragment fragTwo;
    private FourFragment fragFour;
    private ThreeFragment fragThree;
    private List<Fragment> listFra;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ArrayList list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (view == null) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_one, null);

//            ButterKnife.inject(this, view);
//            fragmentManager = getFragmentManager();
//            fragmentTransaction = fragmentManager.beginTransaction();

//            viewpager = (VercalViewPager) view.findViewById(R.id.frame_one);
//
//            listFra = new ArrayList<>();
//            listFra.add(new TwoFragment());
//            listFra.add(new ThreeFragment());
//            listFra.add(new FourFragment());

            Log.i("aaa", "view...............One");
            isPre = true;
            onLoadFragment();
        }
        return view;
    }

    class mAdapter extends RecyclerView.Adapter<mAdapter.mViewHolder> {
        @Override
        public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_recycler, null);
            mViewHolder m = new mViewHolder(view);
            m.tv = (TextView) view.findViewById(R.id.tv);
            return m;
        }

        @Override
        public void onBindViewHolder(mViewHolder holder, int position) {

            holder.tv.setText(list.get(position) + "");

        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        class mViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public mViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView lv = (RecyclerView) view.findViewById(R.id.tv);
        lv.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList();
        for (int i = 0; i < 40; i++) {
            list.add("hello word");
        }

        lv.setAdapter(new mAdapter());
//        viewpager.setAdapter(new FragmentAdapter(getFragmentManager(), listFra));
//        viewpager.setOverScrollMode(View.OVER_SCROLL_NEVER);
////        viewpager.setPageTransformer(true, new DepthPageTransformer());
//        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                Log.i("bbb", "view...............One>>>>"+position);
//                selectFragment(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    //
//    public void selectFragment(int index) {
//
//        hideFragment();
//        switch (index) {
//            case 0:
//
//                view.findViewById(R.id.tv).setBackgroundColor(getResources().getColor(R.color.colorAccent));
//                if (fragTwo == null) {
//                    fragTwo = new TwoFragment();
//                    fragmentTransaction.replace(R.id.frame_one, fragTwo);
//                } else {
//                    fragmentTransaction.show(fragTwo);
//                }
//                break;
//            case 1:
//                view.findViewById(R.id.tv).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                if (fragThree == null) {
//                    fragThree = new ThreeFragment();
//                    fragmentTransaction.replace(R.id.frame_one, fragThree);
//                } else {
//                    fragmentTransaction.show(fragThree);
//                }
//                break;
//            case 2:
//
//                view.findViewById(R.id.tv).setBackgroundColor(getResources().getColor(R.color.colorAccent));
//                if (fragFour == null) {
//                    fragFour = new FourFragment();
//                    fragmentTransaction.replace(R.id.frame_one, fragFour);
//                } else {
//                    fragmentTransaction.show(fragFour);
//                }
//                break;
//        }
//    }
//
//    public void hideFragment() {
//        if (fragTwo != null) {
//            fragmentTransaction.hide(fragTwo);
//        }
//        if (fragThree != null) {
//            fragmentTransaction.hide(fragThree);
//        }
//        if (fragFour != null) {
//            fragmentTransaction.hide(fragFour);
//        }
//    }
//
//    public class FragmentAdapter extends FragmentPagerAdapter {
//
//        List<Fragment> list = new ArrayList<>();
//
//        public FragmentAdapter(FragmentManager fm, List<Fragment> list) {
//            super(fm);
//            this.list = list;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return list.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//    }
//
    @Override
    public void onLoadFragment() {

        if (!isPre || !isVisible)
            return;
        Log.i("aaa", "onloadfragment...............One");
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
