package com.howietian.newpinle.home;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.howietian.newpinle.R;
import com.howietian.newpinle.adapters.MealAdapter;
import com.howietian.newpinle.base.BaseActivity;
import com.howietian.newpinle.entities.Meal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class ShopCarActivity extends BaseActivity {

    @Bind(R.id.tb_shopCar)
    Toolbar tbShopCar;
    @Bind(R.id.rv_shop_car)
    RecyclerView rvShopCar;


    private MealAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private List<Meal> mealList = new ArrayList<>();

    @Override
    public void setMyContentView() {
        setContentView(R.layout.activity_shop_car);
    }

    @Override
    public void init() {
        initData();
        initView();
        initListener();
    }

    private void initData() {
        Meal meal1_1 = new Meal(R.drawable.shop_a12, "40优惠套餐", 4.6, 1254, 1, null);
        Meal meal1_2 = new Meal(R.drawable.shop_a13, "麻辣烫米饭双人套餐", 4.7, 1256, 1, null);
        mealList.add(meal1_1);
        mealList.add(meal1_2);
    }

    private void initListener() {
        tbShopCar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        adapter.setOnAddClickListener(new MealAdapter.onAddClickListener() {
            @Override
            public void onClick(int position) {
                Meal meal = mealList.get(position);
                int buyNum = meal.getBuyNum();
                buyNum++;
                meal.setBuyNum(buyNum);
                mealList.set(position, meal);
                adapter.notifyItemChanged(position, MealAdapter.ADD);
            }
        });

        adapter.setOnMinusClickListener(new MealAdapter.onMinusClickListener() {
            @Override
            public void onClick(int position) {
                Meal meal = mealList.get(position);
                int buyNum = meal.getBuyNum();
                buyNum--;
                if (buyNum < 0) {
                    buyNum = 0;
                }
                meal.setBuyNum(buyNum);
                mealList.set(position, meal);
                adapter.notifyItemChanged(position, MealAdapter.ADD);
            }
        });
    }

    private void initView() {
        setSupportActionBar(tbShopCar);
        adapter = new MealAdapter(mealList, this);
        manager = new LinearLayoutManager(this);
        rvShopCar.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvShopCar.setLayoutManager(manager);
        rvShopCar.setHasFixedSize(true);
        rvShopCar.setAdapter(adapter);

        if (getIntent() != null) {
            String name = getIntent().getStringExtra("toshopcar");
            tbShopCar.setTitle(name);
        }
    }


}
