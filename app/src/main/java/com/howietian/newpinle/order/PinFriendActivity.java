package com.howietian.newpinle.order;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.howietian.newpinle.R;
import com.howietian.newpinle.adapters.FriendAdapter;
import com.howietian.newpinle.base.BaseActivity;
import com.howietian.newpinle.entities.Friend;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class PinFriendActivity extends BaseActivity {

    @Bind(R.id.rv_friend)
    RecyclerView rvFriend;
    @Bind(R.id.tb_friend)
    Toolbar tbFriend;

    private List<Friend> friends = new ArrayList<>();
    private FriendAdapter adapter;
    private RecyclerView.LayoutManager manager;

    @Override
    public void setMyContentView() {
        setContentView(R.layout.activity_pin_friend);
    }

    @Override
    public void init() {
        initData();
        initView();
        initListener();
    }

    private void initData() {
        Friend friend = new Friend("15968191535", R.drawable.ic_people_black_24dp);
        for (int i = 0; i < 5; i++) {
            friends.add(friend);
        }
    }

    private void initView() {

        setSupportActionBar(tbFriend);

        adapter = new FriendAdapter(friends, this);
        manager = new LinearLayoutManager(this);
        rvFriend.setLayoutManager(manager);
        rvFriend.setAdapter(adapter);
        rvFriend.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rvFriend.setHasFixedSize(true);
    }

    private void initListener(){
        tbFriend.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
