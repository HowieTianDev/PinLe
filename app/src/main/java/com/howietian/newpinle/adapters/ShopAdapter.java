package com.howietian.newpinle.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.howietian.newpinle.R;
import com.howietian.newpinle.entities.Shop;
import com.howietian.newpinle.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 83624 on 2018/2/13 0013.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {
    private Context context;
    private List<Shop> shopList = new ArrayList<>();
    private onItemClickListener onItemClickListener;

    public interface onItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public ShopAdapter(Context context, List<Shop> shopList) {
        this.context = context;
        this.shopList = shopList;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ShopViewHolder holder = null;
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop, parent, false);
        holder = new ShopViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, final int position) {
        final Shop shop = shopList.get(position);
        holder.tvShopName.setText(shop.getName());
        holder.tvScore.setText(shop.getScore().toString());
        holder.tvOrderNum.setText(shop.getOrderNum().toString());
        holder.tvPrice.setText(shop.getLowPrice().toString());
        if (shop.getShopImage() != null) {
            ImageLoader.with(context, shop.getShopImage().getUrl(), holder.ivShop);
        }

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    static class ShopViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_shop)
        ImageView ivShop;
        @Bind(R.id.tv_shop_name)
        TextView tvShopName;
        @Bind(R.id.tv_order_num)
        TextView tvOrderNum;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_score)
        TextView tvScore;

        public ShopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
