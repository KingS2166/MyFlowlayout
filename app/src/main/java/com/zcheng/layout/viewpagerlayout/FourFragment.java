package com.zcheng.layout.viewpagerlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zcheng.layout.R;

/**
 * Created by User on 2016/6/29.
 */
public class FourFragment extends MyFragment {

    private boolean isPre=false;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(view==null) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_one, null);
//            ButterKnife.inject(this, view);
            Log.i("aaa","view...............One");
            isPre=true;
            onLoadFragment();
        }
        return view;
    }

    @Override
    public void onLoadFragment() {

        if(!isPre||!isVisible)
            return;
        Log.i("aaa","onloadfragment...............One");
    }
}
