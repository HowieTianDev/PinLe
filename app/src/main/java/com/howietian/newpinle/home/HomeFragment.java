package com.howietian.newpinle.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.howietian.newpinle.R;
import com.howietian.newpinle.adapters.ShopAdapter;
import com.howietian.newpinle.app.MyApp;
import com.howietian.newpinle.base.BaseFragment;
import com.howietian.newpinle.entities.Shop;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.bgaBanner)
    BGABanner bgaBanner;
    private static final String HOME_FRAGMENT = "home_fragment";
    @Bind(R.id.iv_food)
    ImageView ivFood;
    @Bind(R.id.iv_drink)
    ImageView ivDrink;
    @Bind(R.id.iv_wFood)
    ImageView ivWFood;
    @Bind(R.id.iv_tea)
    ImageView ivTea;
    @Bind(R.id.iv_hot_pot)
    ImageView ivHotPot;
    @Bind(R.id.iv_cFood)
    ImageView ivCFood;
    @Bind(R.id.iv_cake)
    ImageView ivCake;
    @Bind(R.id.iv_hot_hall)
    ImageView ivHotHall;
    @Bind(R.id.rvShop)
    RecyclerView rvShop;
    @Bind(R.id.smartLayout)
    SmartRefreshLayout smartRefreshLayout;


    private List<Shop> shopList = new ArrayList<>();
    private ShopAdapter adapter;
    private RecyclerView.LayoutManager manager;
    public static final String FROM_HOME = "from_home";
    String type = "美食外卖";


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String args) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(HOME_FRAGMENT, args);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }


    @Override
    public View createMyView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void init() {
        initDatas();
        initView();
        initListener();

    }


    private void initView() {
        smartRefreshLayout.autoRefresh();
        bgaBanner.setData(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4, R.drawable.banner5);
        adapter = new ShopAdapter(getContext(), shopList);
        manager = new LinearLayoutManager(getContext());
        rvShop.setAdapter(adapter);
        rvShop.setLayoutManager(manager);
        rvShop.setNestedScrollingEnabled(false);
        rvShop.setHasFixedSize(true);
    }

    private void initDatas() {
        queryShop("美食外卖");
    }

    private void initListener() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                queryShop(type);
            }
        });

        adapter.setOnItemClickListener(new ShopAdapter.onItemClickListener() {
            @Override
            public void onClick(int position) {
                if (MyApp.isLogin()) {
                    String msg = new Gson().toJson(shopList.get(position), Shop.class);
                    Intent intent = new Intent(getContext(), ShopDetailActivity.class);
                    intent.putExtra(FROM_HOME, msg);
                    jumpTo(intent, false);
                } else {
                    showToast("请先登录~");
                }

            }
        });
    }

    private void queryShop(String type) {

        BmobQuery<Shop> query = new BmobQuery<>();
        query.addWhereEqualTo("type", type);
        query.findObjects(new FindListener<Shop>() {
            @Override
            public void done(List<Shop> list, BmobException e) {
                if (e == null) {
                    if (shopList != null) {
                        shopList.clear();
                    }
                    shopList.addAll(list);
                    adapter.notifyDataSetChanged();
                    smartRefreshLayout.finishRefresh();
                    if (shopList.size() == 0) {
                        showToast("服务器没有数据");
                    }
                } else {
                    showToast("请求服务器出错" + e.getMessage() + e.getErrorCode());
                }

            }
        });
    }

    @OnClick(R.id.iv_tea)
    public void queryTea() {
        type = "假日茶点";
        queryShop(type);
    }

    @OnClick(R.id.iv_cake)
    public void queryCake() {
        type = "鲜花蛋糕";
        queryShop(type);
    }

    @OnClick(R.id.iv_cFood)
    public void queryCFood() {
        type = "中式美食";
        queryShop(type);
    }

    @OnClick(R.id.iv_drink)
    public void queryDrink() {
        type = "酷爽饮料";
        queryShop(type);
    }

    @OnClick(R.id.iv_food)
    public void queryFood() {
        type = "美食外卖";
        queryShop(type);
    }

    @OnClick(R.id.iv_wFood)
    public void queryWFood() {
        type = "西式快餐";
        queryShop(type);
    }

    @OnClick(R.id.iv_hot_hall)
    public void queryHotHall() {
        type = "最热餐厅";
        queryShop(type);
    }

    @OnClick(R.id.iv_hot_pot)
    public void queryHotPot() {
        type = "热辣火锅";
        queryShop(type);
    }
}
