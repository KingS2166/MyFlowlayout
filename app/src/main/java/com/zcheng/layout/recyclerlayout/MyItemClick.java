package com.zcheng.layout.recyclerlayout;

import android.view.View;

/**
 * Created by User on 2016/8/25.
 */
public class MyItemClick {
    public interface onItemClicked {
        public void onItemClick(int tag, View view);
    }
}
