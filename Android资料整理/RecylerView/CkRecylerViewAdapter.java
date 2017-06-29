package com.ckjr.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ckjr.framework.R;

/**
 * Created by xievxin on 2016/11/18.
 * 2016/11/18:  支持"点击加载下一页"
 */

public abstract class CkRecylerViewAdapter extends RecyclerView.Adapter {

    private final int TYPE_ITEM = 0;
    private final int TYPE_FOOTER = 1;
    private Context context;

    /**
     * 加载下一页 ViewGroup
     */
    private ViewGroup loaderVg;

    private LoaderHolder loaderHolder;

    public CkRecylerViewAdapter(Context context) {
        this.context = context;
        loaderVg = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.recylerview_footer, null);
        loaderVg.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        loaderHolder = new LoaderHolder(loaderVg);
    }

    public void resetLoaderBtn() {
        loaderHolder.resetLoaderBtn();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return loaderHolder;
        }
        return onCreateHolder(viewGroup, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            return;
        }
        onBindHolder(viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return getCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1)
            return TYPE_FOOTER;
        else
            return TYPE_ITEM;
    }

    /**
     * 设置是否可以点击加载下一页
     *
     * @param loadable
     */
    public void setLoadable(boolean loadable) {
        loaderVg.setClickable(loadable);
    }


    class LoaderHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Button loaderBtn;

        public LoaderHolder(View itemView) {
            super(itemView);
            loaderBtn = (Button) itemView.findViewById(R.id.loaderBtn);
            loaderBtn.setOnClickListener(this);
        }

        public void resetLoaderBtn() {
            loaderBtn.setText("点击加载下一页");
            loaderBtn.setClickable(true);
        }

        @Override
        public void onClick(View v) {
            loaderBtn.setText("加载中...");
            loaderBtn.setClickable(false);
            onLoadNextPage();
        }
    }



    public abstract RecyclerView.ViewHolder onCreateHolder(ViewGroup viewGroup, int viewType);

    public abstract void onBindHolder(RecyclerView.ViewHolder viewHolder, int position);

    public abstract int getCount();

    /**
     * 加载下一页
     */
    public abstract void onLoadNextPage();

}
