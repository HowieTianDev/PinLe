package com.howietian.newpinle.order;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.howietian.newpinle.R;
import com.howietian.newpinle.adapters.PageAdapter;
import com.howietian.newpinle.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends BaseFragment {
    private static final String ORDER_FRAGMENT = "order_fragment";
    @Bind(R.id.tbLayout)
    TabLayout tbLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private OnOrderFragment onOrderFragment;
    private HistoryOrderFragment historyOrderFragment;
    private PageAdapter pageAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();


    public OrderFragment() {
        // Required empty public constructor
    }


    public static OrderFragment newInstance(String args) {
        OrderFragment fragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_FRAGMENT, args);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View createMyView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void init() {
        initViews();
    }

    private void initViews() {
        onOrderFragment = OnOrderFragment.newInstance("on_order_fragment");
        historyOrderFragment = HistoryOrderFragment.newInstance("history_order_fragment");
        fragments.add(onOrderFragment);
        fragments.add(historyOrderFragment);
        titles.add("正在进行的订单");
        titles.add("历史订单");

        pageAdapter = new PageAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(pageAdapter);
        tbLayout.setupWithViewPager(viewPager);
        tbLayout.setTabMode(TabLayout.MODE_FIXED);

    }


}
