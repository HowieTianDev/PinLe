package com.howietian.newpinle.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.howietian.newpinle.R;
import com.howietian.newpinle.entities.Friend;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 83624 on 2018/2/22 0022.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private List<Friend> friends = new ArrayList<>();
    private Context context;

    public FriendAdapter(List<Friend> list, Context context) {
        this.context = context;
        this.friends = list;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);
        FriendViewHolder holder = new FriendViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        Friend friend = friends.get(position);
        holder.ivFriend.setImageResource(friend.getImage());
        holder.tvFriend.setText(friend.getPhone());
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    static class FriendViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_friend)
        ImageView ivFriend;
        @Bind(R.id.tv_friend)
        TextView tvFriend;

        public FriendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
