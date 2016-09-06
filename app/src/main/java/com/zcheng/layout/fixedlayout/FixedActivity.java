package com.zcheng.layout.fixedlayout;

import android.app.Activity;
import android.os.Bundle;

import com.zcheng.layout.R;

/**
 * Created by User on 2016/8/8.
 */
public class FixedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_fixed);

        FixedEdit mfixed= (FixedEdit) findViewById(R.id.mfixed);

        mfixed.getTextString("我的天  看看行不行:");
//        Log.i("aaa", "" + mfixed.getTextString());
    }
}
