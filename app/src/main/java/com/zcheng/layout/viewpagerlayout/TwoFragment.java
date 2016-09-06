package com.zcheng.layout.viewpagerlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zcheng.layout.R;

import java.util.ArrayList;

/**
 * Created by User on 2016/6/29.
 */
public class TwoFragment extends MyFragment {

    private boolean isPre = false;
    private View view;
    private ListView list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_two, null);
//            ButterKnife.inject(this, view);
            Log.i("aaa", "view...............Two");
            isPre = true;
            onLoadFragment();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = (ListView) view.findViewById(R.id.lv);
        ArrayList<String> lists = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            lists.add("hello word");
        }

        ArrayAdapter<String> mAdaper = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lists);
        list.setAdapter(mAdaper);

    }

    @Override
    public void onLoadFragment() {

        if (!isPre || !isVisible)
            return;
        Log.i("aaa", "onloadfragment...............Two");
    }

}
