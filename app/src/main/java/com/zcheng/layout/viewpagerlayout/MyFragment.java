package com.zcheng.layout.viewpagerlayout;


import android.support.v4.app.Fragment;

/**
 * Created by User on 2016/6/29.
 */
public abstract class MyFragment extends Fragment {

    public boolean isVisible=false;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){

            isVisible=true;
            onVisible();
        }else {

            isVisible=false;
            onInvident();
        }
    }

    private void onVisible() {
        onLoadFragment();
    }

    private void onInvident() {

    }

    public abstract void onLoadFragment();
}
