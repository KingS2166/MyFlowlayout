package com.zcheng.layout.recyclerlayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zcheng.layout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016/8/13.
 */
public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> list;
    private myRecylerAdapter myRecylerAdapter;
    private RecyclerView.Adapter mAdapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler);
        mRecyclerView = (RecyclerView) findViewById(R.id.myRecyler);

        initData();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));

//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        myRecylerAdapter = new myRecylerAdapter();
        HeaderAndFooterWrapper head=new HeaderAndFooterWrapper(myRecylerAdapter);
        TextView tv1 = new TextView(this);
        tv1.setText("asfsadfsafsadf");
        head.addFootView(tv1);

        View view=LayoutInflater.from(this).inflate(R.layout.activity_cqs,null);
//        TextView tv2 = new TextView(this);
//        tv2.setText("asfsadfsafsadf");
        head.addHeaderView(view);

        head.setOnItemListener(new MyItemClick.onItemClicked() {
            @Override
            public void onItemClick(int tag, View view) {

                setToast(tag+"");
            }
        });
        mRecyclerView.setAdapter(head);
    }

    public void setToast(String tag) {
        Toast.makeText(RecyclerActivity.this, "" + tag, Toast.LENGTH_SHORT).show();
    }

    private void initData() {

        list = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            list.add((char) i + "");
        }
    }

    class myRecylerAdapter extends RecyclerView.Adapter<myRecylerAdapter.mViewHoder>{

        @Override
        public mViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(RecyclerActivity.this).inflate(R.layout.item_recycler, null);
            mViewHoder mViewHoder = new mViewHoder(view);
            return mViewHoder;

        }

        @Override
        public void onBindViewHolder(mViewHoder holder, int position) {

            holder.tv.setText(list.get(position).toString());

        }

        class mViewHoder extends RecyclerView.ViewHolder {

            TextView tv;

            public mViewHoder(View itemView) {
                super(itemView);

                tv = (TextView) itemView.findViewById(R.id.tv);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
        private static final int BASE_ITEM_TYPE_HEADER = 100000;
        private static final int BASE_ITEM_TYPE_FOOTER = 200000;

        private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
        private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

        private RecyclerView.Adapter mInnerAdapter;

        private MyItemClick.onItemClicked onItemClick;

        public void setOnItemListener(MyItemClick.onItemClicked myOnItem) {
            onItemClick = myOnItem;
        }

        public HeaderAndFooterWrapper(RecyclerView.Adapter adapter) {
            mInnerAdapter = adapter;
        }

        private boolean isHeaderViewPos(int position) {
            return position < getHeadersCount();
        }

        private boolean isFooterViewPos(int position) {
            return position >= getHeadersCount() + getRealItemCount();
        }


        public void addHeaderView(View view) {
            mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
        }

        public void addFootView(View view) {
            mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
        }

        public int getHeadersCount() {
            return mHeaderViews.size();
        }

        public int getFootersCount() {
            return mFootViews.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(RecyclerActivity.this).inflate(R.layout.item_recycler, null);
            ViewHolder viewHolder=new ViewHolder(view);

            if (mHeaderViews.get(viewType) != null) {

                viewHolder = viewHolder.createViewHolder(mHeaderViews.get(viewType));
                return viewHolder;

            } else if (mFootViews.get(viewType) != null) {
                viewHolder = viewHolder.createViewHolder(mFootViews.get(viewType));
                return viewHolder;
            }
            return mInnerAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public int getItemViewType(int position) {
            if (isHeaderViewPos(position)) {
                return mHeaderViews.keyAt(position);
            } else if (isFooterViewPos(position)) {
                return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
            }
            return mInnerAdapter.getItemViewType(position - getHeadersCount());
        }

        private int getRealItemCount() {
            return mInnerAdapter.getItemCount();
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);

            if (isHeaderViewPos(position)) {
                return;
            }
            if (isFooterViewPos(position)) {
                return;
            }
            mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
        }

        @Override
        public int getItemCount() {
            return getHeadersCount() + getFootersCount() + getRealItemCount();
        }

        @Override
        public void onClick(View v) {
            onItemClick.onItemClick((Integer) v.getTag(), v);

//            notifyItemChanged((Integer) v.getTag());
            Log.i("aaa", "" + (Integer) v.getTag());
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private SparseArray<View> mViews;
            private View mConvertView;
            private Context mContext;

            public ViewHolder(View itemView) {
                super(itemView);
//                mContext = context;
                mConvertView = itemView;
                mViews = new SparseArray<View>();
            }

            public ViewHolder createViewHolder(View itemView) {
                ViewHolder holder = new ViewHolder(itemView);
                return holder;
            }
        }

    }
}
