package com.howietian.newpinle.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.howietian.newpinle.R;
import com.howietian.newpinle.entities.Meal;
import com.howietian.newpinle.utils.ImageLoader;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 83624 on 2018/2/19 0019.
 */

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> implements StickyRecyclerHeadersAdapter<MealAdapter.HeaderViewHolder> {
    private List<Meal> mealList = new ArrayList<>();
    private Context context;
    public static final int ADD = 0;
    public static final int MINUS = 1;

    private onAddClickListener onAddClickListener;
    private onMinusClickListener onMinusClickListener;

    public interface onAddClickListener {
        void onClick(int position);
    }

    public void setOnAddClickListener(onAddClickListener onAddClickListener) {
        this.onAddClickListener = onAddClickListener;
    }

    public interface onMinusClickListener {
        void onClick(int position);
    }

    public void setOnMinusClickListener(onMinusClickListener onMinusClickListener) {
        this.onMinusClickListener = onMinusClickListener;
    }

    public MealAdapter(List<Meal> meals, Context context) {
        this.mealList = meals;
        this.context = context;
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false);
        MealViewHolder holder = new MealViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MealViewHolder holder, int position) {


    }

    @Override
    public void onBindViewHolder(MealViewHolder holder, final int position, List<Object> payloads) {

        final Meal meal = mealList.get(position);
        if (payloads.isEmpty()) {
            if (meal.getPhotoId() != null) {
                holder.ivMeal.setImageResource(meal.getPhotoId());
            }
            holder.tvMealName.setText(meal.getName());
            holder.tvScore.setText(meal.getScore().toString());
            holder.tvOrderNum.setText(meal.getSaleNum().toString());
            holder.tvBuyNum.setText(meal.getBuyNum().toString());
            if (onAddClickListener != null) {
                holder.ivAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onAddClickListener.onClick(position);
                    }
                });
            }
            if (onMinusClickListener != null) {
                holder.ivMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onMinusClickListener.onClick(position);
                    }
                });
            }
        } else {
            int type = (int) payloads.get(0);
            switch (type) {
                case ADD:
                    holder.tvBuyNum.setText(meal.getBuyNum().toString());
                    break;
                case MINUS:
                    holder.tvBuyNum.setText(meal.getBuyNum().toString());
                    break;
            }
        }
    }

    @Override
    public long getHeaderId(int position) {
        if (position == 0) {
            return -1;
        }

        long headerId = mealList.get(position).getClssify().hashCode();
        return headerId;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
        HeaderViewHolder headerViewHolder = new HeaderViewHolder(view);
        return headerViewHolder;
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder holder, int position) {
        holder.tvHeader.setText(mealList.get(position).getClssify());
    }


    @Override
    public int getItemCount() {
        return mealList.size();
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_header)
        TextView tvHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_meal)
        ImageView ivMeal;
        @Bind(R.id.tv_meal_name)
        TextView tvMealName;
        @Bind(R.id.tv_score)
        TextView tvScore;
        @Bind(R.id.tv_buy_num)
        TextView tvBuyNum;
        @Bind(R.id.tv_order_num)
        TextView tvOrderNum;
        @Bind(R.id.iv_add)
        ImageView ivAdd;
        @Bind(R.id.iv_minus)
        ImageView ivMinus;

        public MealViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
