package com.howietian.newpinle.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.howietian.newpinle.R;
import com.howietian.newpinle.entities.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 83624 on 2018/2/21 0021.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList = new ArrayList<>();
    private Context context;
    private onDeleteClickListener onDeleteClickListener;
    private onAgainClickListener onAgainClickListener;


    public OrderAdapter(List<Order> list, Context context) {
        this.context = context;
        this.orderList = list;
    }

    public interface onDeleteClickListener {
        void onClick(int position);
    }

    public interface onAgainClickListener {
        void onClick(int position);
    }

    public void setOnDeleteClickListener(onDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    public void setOnAgainClickListener(onAgainClickListener listener) {
        this.onAgainClickListener = listener;
    }


    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        OrderViewHolder holder = new OrderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {

        final Order order = orderList.get(position);
        holder.ivMeal.setImageResource(order.getMealImage());
        holder.ivShop.setImageResource(order.getShopImage());
        holder.tvShopName.setText(order.getShopName());
        holder.tvNumber.setText("×" + order.getNumber());
        holder.tvOnePrice.setText("￥" + order.getPrice());
        holder.tvEndPrice.setText("付款：￥" + order.getEndPrice());
        holder.tvMealName.setText(order.getMealName());
        holder.tvBuyTime.setText(order.getBuyTime());

        if (onDeleteClickListener != null) {
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteClickListener.onClick(position);
                }
            });
        }

        if (onAgainClickListener != null) {
            holder.btnAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAgainClickListener.onClick(position);
                }
            });
        }

        if (orderList.size() == 1) {
            holder.btnDelete.setVisibility(View.GONE);
            holder.btnAgain.setText("确认收货");
            holder.tvStatus.setText("等待配送");
        }

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_shop)
        ImageView ivShop;
        @Bind(R.id.tv_shop_name)
        TextView tvShopName;
        @Bind(R.id.iv_meal)
        ImageView ivMeal;
        @Bind(R.id.tv_meal_name)
        TextView tvMealName;
        @Bind(R.id.tv_one_price)
        TextView tvOnePrice;
        @Bind(R.id.tv_number)
        TextView tvNumber;
        @Bind(R.id.tv_end_price)
        TextView tvEndPrice;
        @Bind(R.id.btn_delete)
        Button btnDelete;
        @Bind(R.id.btn_again)
        Button btnAgain;
        @Bind(R.id.tv_buy_time)
        TextView tvBuyTime;
        @Bind(R.id.tv_status)
        TextView tvStatus;

        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
