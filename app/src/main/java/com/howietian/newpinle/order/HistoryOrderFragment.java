package com.howietian.newpinle.order;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.howietian.newpinle.R;
import com.howietian.newpinle.adapters.OrderAdapter;
import com.howietian.newpinle.base.BaseFragment;
import com.howietian.newpinle.entities.Address;
import com.howietian.newpinle.entities.Order;
import com.howietian.newpinle.me.AddressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryOrderFragment extends BaseFragment {
    @Bind(R.id.rv_history)
    RecyclerView rvHistory;

    private static final String HISTORY_ORDER_FRAGMENT = "history_order_fragment";
    private List<Order> orderList = new ArrayList<>();
    private OrderAdapter adapter;
    private RecyclerView.LayoutManager manager;

    public HistoryOrderFragment() {
        // Required empty public constructor
    }


    public static HistoryOrderFragment newInstance(String args) {
        HistoryOrderFragment fragment = new HistoryOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(HISTORY_ORDER_FRAGMENT, args);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View createMyView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_order, container, false);
    }

    @Override
    public void init() {
        initData();
        initView();
        initListener();
    }

    private void initData() {
        Order order = new Order(R.drawable.one, R.drawable.tw, "木马披萨", "7寸榴莲披萨", "15", 1, "2017-5-6", "12");
        Order order1 = new Order(R.drawable.three, R.drawable.three1, "南京灌汤包", "白粥配咸菜", "15", 1, "2017-9-20", "13");
        Order order2 = new Order(R.drawable.four, R.drawable.four1, "小杨生煎", "虾仁生煎", "19", 1, "2017-9-22", "13");
        Order order3 = new Order(R.drawable.fiv, R.drawable.fiv1, "coco奶茶", "抹茶拿铁", "15", 1, "2017-8-22", "14");
        orderList.add(order);
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
    }

    private void initView() {
        adapter = new OrderAdapter(orderList, getContext());
        manager = new LinearLayoutManager(getContext());
        rvHistory.setLayoutManager(manager);
        rvHistory.setAdapter(adapter);
        rvHistory.setHasFixedSize(true);
    }

    private void initListener() {
        adapter.setOnDeleteClickListener(new OrderAdapter.onDeleteClickListener() {
            @Override
            public void onClick(final int position) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("温馨提示");
                builder.setMessage("确定要删除吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        orderList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        adapter.setOnAgainClickListener(new OrderAdapter.onAgainClickListener() {
            @Override
            public void onClick(int position) {
                showToast("再来一单");
            }
        });
    }

}
