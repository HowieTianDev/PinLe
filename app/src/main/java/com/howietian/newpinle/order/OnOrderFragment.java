package com.howietian.newpinle.order;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.howietian.newpinle.R;
import com.howietian.newpinle.adapters.OrderAdapter;
import com.howietian.newpinle.base.BaseFragment;
import com.howietian.newpinle.entities.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnOrderFragment extends BaseFragment {


    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.tv_wait)
    TextView tvWait;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.rv_on_order)
    RecyclerView rvOnOrder;


    private List<Order> orderList = new ArrayList<>();
    private OrderAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private List<String> list = new ArrayList<>();
    private ArrayAdapter arrayAdapter = null;

    public OnOrderFragment() {
        // Required empty public constructor
    }

    private static final String ON_ORDER_FRAGMENT = "on_order_fragment";

    public static OnOrderFragment newInstance(String args) {
        OnOrderFragment fragment = new OnOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ON_ORDER_FRAGMENT, args);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View createMyView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_on_order, container, false);
    }

    @Override
    public void init() {
        initData();
        initView();
        initListener();

    }

    private void initData() {

        Order order3 = new Order(R.drawable.fiv, R.drawable.fiv1, "coco奶茶", "抹茶拿铁", "15", 1, "2018-2-22", "14");
        orderList.add(order3);

        list.add("拼单");
        list.add("不拼单");
    }

    private void initListener() {
        adapter.setOnAgainClickListener(new OrderAdapter.onAgainClickListener() {
            @Override
            public void onClick(int position) {
                orderList.clear();
                adapter.notifyDataSetChanged();
                showToast("确认收货成功！");
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvResult.setText(list.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initView() {
        adapter = new OrderAdapter(orderList, getContext());
        manager = new LinearLayoutManager(getContext());
        rvOnOrder.setLayoutManager(manager);
        rvOnOrder.setAdapter(adapter);
        rvOnOrder.setHasFixedSize(true);

        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
        spinner.setAdapter(arrayAdapter);


    }

    @OnClick(R.id.tv_search)
    public void toFriend() {
        jumpTo(PinFriendActivity.class, false);
    }


}
