package com.zcheng.layout;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zcheng.layout.flowlayout.FlowLayout;
import com.zcheng.layout.flowlayout.GridientDrawableUtil;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private FlowLayout flow;
    private Random random;

    String data[] = {"短的", "不短的", "不是很短的", "我说这个是短的你信吗",
            "懒得复制了我叫长的", "前面的朋友好我也叫长的", "这么巧", "oh shit 不是吧"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_intercept);


        ListView lv= (ListView) findViewById(R.id.lv);
        ArrayList<String> list=new ArrayList<>();

        for(int i=0;i<20;i++){
            list.add("hello word");
        }

        ArrayAdapter mAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(mAdapter);
//        random=new Random();
//
//        flow = (FlowLayout) findViewById(R.id.flow);
//        init();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void init() {

        for (int i = 0; i < data.length; i++) {
            TextView tv = new TextView(this);
            //因为那个那个 所以必须先Margin
            ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mp.topMargin = 5;
            mp.leftMargin = 5;
            mp.rightMargin = 5;
            mp.bottomMargin = 5;
            tv.setLayoutParams(mp);

            tv.setText(data[i]);
            tv.setBackground(GridientDrawableUtil.setText(Color.rgb(random(), random(), random()), 5));
            tv.setPadding(5,5,5,5);
            flow.addView(tv);
        }
    }

    public int random(){
        return random.nextInt(210);
    }
}
