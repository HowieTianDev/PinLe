package com.howietian.newpinle.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.howietian.newpinle.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 83624 on 2018/2/19 0019.
 */

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.MyViewHolder> {

    private List<String> titles = new ArrayList<>();
    private List<Boolean> flags = new ArrayList<>();
    private Context context;
    private onItemClickListener listener;

    public interface onItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }


    public LeftAdapter(Context context, List<String> titles, List<Boolean> flags) {
        this.context = context;
        this.titles = titles;
        this.flags = flags;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_left, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String title = titles.get(position);
        holder.tvTitle.setText(title);
        if (flags.get(position)) {
            holder.ivIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.ivIndicator.setVisibility(View.INVISIBLE);
        }
// 设置点击事件
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.iv_indicator)
        ImageView ivIndicator;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
