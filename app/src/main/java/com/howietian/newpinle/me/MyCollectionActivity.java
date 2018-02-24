package com.howietian.newpinle.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.howietian.newpinle.R;
import com.howietian.newpinle.adapters.ShopAdapter;
import com.howietian.newpinle.app.MyApp;
import com.howietian.newpinle.base.BaseActivity;
import com.howietian.newpinle.entities.Shop;
import com.howietian.newpinle.entities.User;
import com.howietian.newpinle.home.HomeFragment;
import com.howietian.newpinle.home.ShopDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyCollectionActivity extends BaseActivity {


    @Bind(R.id.tb_collect)
    Toolbar tbCollect;
    @Bind(R.id.rv_collect)
    RecyclerView rvCollect;

    private ShopAdapter adapter;
    private List<Shop> shopList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public void setMyContentView() {
        setContentView(R.layout.activity_my_collection);
    }

    @Override
    public void init() {
        initData();
        initView();
        initListener();
    }

    private void initData() {
        queryData();

    }

    private void initView() {
        setSupportActionBar(tbCollect);

        adapter = new ShopAdapter(this, shopList);
        layoutManager = new LinearLayoutManager(this);
        rvCollect.setLayoutManager(layoutManager);
        rvCollect.setHasFixedSize(true);
        rvCollect.setAdapter(adapter);
    }

    private void initListener() {
        tbCollect.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter.setOnItemClickListener(new ShopAdapter.onItemClickListener() {
            @Override
            public void onClick(int position) {
                if (MyApp.isLogin()) {
                    String msg = new Gson().toJson(shopList.get(position), Shop.class);
                    Intent intent = new Intent(MyCollectionActivity.this, ShopDetailActivity.class);
                    intent.putExtra(HomeFragment.FROM_HOME, msg);
                    jumpTo(intent, false);
                } else {
                    showToast("请先登录~");
                }

            }
        });
    }

    private void queryData() {
        /**
         * 只执行刷新操作,查询当前用户喜欢的推文
         * 采用内部查询的方法，来实现反查询
         */
        BmobQuery<Shop> query = new BmobQuery<>();
        query.include("collect");
        query.order("-createdAt");

        BmobQuery<User> innerQuery = new BmobQuery<>();
        innerQuery.addWhereEqualTo("objectId", BmobUser.getCurrentUser(User.class).getObjectId());
        query.addWhereMatchesQuery("collect", "_User", innerQuery);

        query.findObjects(new FindListener<Shop>() {
            @Override
            public void done(List<Shop> list, BmobException e) {
                if (e == null) {
//                    查出有数据
                    if (list.size() > 0) {
                        shopList.clear();
                        shopList.addAll(list);
                        adapter.notifyDataSetChanged();
//                        查询到无数据
                    } else {
                        showToast("服务器没有数据");
                    }
                } else {
                    showToast("请求服务器异常" + e.getMessage() + "错误代码" + e.getErrorCode());
                    Log.e("HHH", e.getMessage() + "错误代码" + e.getErrorCode());
                }

            }
        });
    }

}



