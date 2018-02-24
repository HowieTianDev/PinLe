package com.howietian.newpinle.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.howietian.newpinle.R;
import com.howietian.newpinle.adapters.LeftAdapter;
import com.howietian.newpinle.adapters.MealAdapter;
import com.howietian.newpinle.base.BaseActivity;
import com.howietian.newpinle.entities.Meal;
import com.howietian.newpinle.entities.Shop;
import com.howietian.newpinle.entities.User;
import com.howietian.newpinle.utils.ImageLoader;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ShopDetailActivity extends BaseActivity {
    @Bind(R.id.main_toolbar)
    Toolbar toolbar;
    @Bind(R.id.main_collapsing)
    CollapsingToolbarLayout collapsing;
    @Bind(R.id.rv_title)
    RecyclerView rvTitle;
    @Bind(R.id.rv_meal)
    RecyclerView rvMeal;
    @Bind(R.id.backdrop)
    ImageView backdrop;

    private MealAdapter adapter;
    private List<Meal> mealList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<Boolean> flags = new ArrayList<>();
    private RecyclerView.LayoutManager manager;
    private StickyRecyclerHeadersDecoration stickyRecyclerHeadersDecoration;
    private LeftAdapter leftadapter;
    private ArrayList<String> collectIdList = new ArrayList<>();
    Shop shop = null;

    // 是否收藏的标志
    boolean isCollect = false;

    @Override
    public void setMyContentView() {
        setContentView(R.layout.activity_shop_detail);
    }

    @Override
    public void init() {
        initData();
        initView();
        initListener();
    }


    private void setMeal(int position) {
        mealList.clear();
        switch (position) {
            case 0:
                Meal meal0_1 = new Meal(R.drawable.shop_b11, "骨肉相连", 4.6, 258, 0, null);
                Meal meal0_2 = new Meal(R.drawable.shop_b12, "金针菇", 4.7, 256, 0, null);
                Meal meal0_3 = new Meal(R.drawable.shop_a11, "打包神器+骨头汤底", 4.9, 1234, 0, null);
                mealList.add(meal0_3);
                mealList.add(meal0_1);
                mealList.add(meal0_2);
                break;
            case 1:
                Meal meal1_1 = new Meal(R.drawable.shop_a12, "40优惠套餐", 4.6, 1254, 0, null);
                Meal meal1_2 = new Meal(R.drawable.shop_a13, "麻辣烫米饭双人套餐", 4.7, 1256, 0, null);
                mealList.add(meal1_1);
                mealList.add(meal1_2);
                break;
            case 2:
                Meal meal2_1 = new Meal(R.drawable.shop_c11, "培根", 4.6, 1254, 0, null);
                Meal meal2_2 = new Meal(R.drawable.shop_c12, "千张丝", 4.7, 1256, 0, null);
                mealList.add(meal2_1);
                mealList.add(meal2_2);
                break;
            case 3:
                Meal meal3_1 = new Meal(R.drawable.shop_d11, "草莓优酪多", 4.3, 21, 0, null);
                Meal meal3_2 = new Meal(R.drawable.shop_d12, "红豆奶茶", 4.6, 29, 0, null);
                mealList.add(meal3_1);
                mealList.add(meal3_2);
                break;
            case 4:
                Meal meal4_1 = new Meal(R.drawable.shop_e11, "33朵红玫瑰", 4.0, 23, 0, null);
                Meal meal4_2 = new Meal(R.drawable.shop_e12, "33朵蓝色妖姬", 4.1, 25, 0, null);
                mealList.add(meal4_1);
                mealList.add(meal4_2);
                break;
            case 5:
                Meal meal5_1 = new Meal(R.drawable.logo, "请备注", 4.0, 2255, 0, null);
                mealList.add(meal5_1);
                break;
        }

        adapter.notifyDataSetChanged();
    }


    private void initData() {
        titles.add("招牌菜");
        titles.add("套餐");
        titles.add("加菜区");
        titles.add("饮品");
        titles.add("赠品");
        titles.add("备注");

        flags.add(true);
        flags.add(false);
        flags.add(false);
        flags.add(false);
        flags.add(false);
        flags.add(false);

    }

    private void initView() {

        setSupportActionBar(toolbar);
        collapsing.setCollapsedTitleTextColor(getResources().getColor(R.color.dark));
        collapsing.setExpandedTitleColor(getResources().getColor(R.color.white));

        if (getIntent() != null) {
            Intent intent = getIntent();
            String msg = intent.getStringExtra(HomeFragment.FROM_HOME);
            shop = new Gson().fromJson(msg, Shop.class);
            collapsing.setTitle(shop.getName());
            ImageLoader.with(this, shop.getShopImage().getUrl(), backdrop);
        }

        manager = new LinearLayoutManager(this);
        adapter = new MealAdapter(mealList, this);
        rvMeal.setLayoutManager(manager);
        rvMeal.setAdapter(adapter);

        stickyRecyclerHeadersDecoration = new StickyRecyclerHeadersDecoration(adapter);
        // 粘性头部不知道为啥就是显示不出来，放弃了。。。。
        // rvMeal.addItemDecoration(stickyRecyclerHeadersDecoration);
        rvMeal.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(this);
        leftadapter = new LeftAdapter(this, titles, flags);
        rvTitle.setAdapter(leftadapter);
        rvTitle.setLayoutManager(manager1);
        rvTitle.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvMeal.setNestedScrollingEnabled(false);

        setMeal(0);
    }

    private void initListener() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        leftadapter.setOnItemClickListener(new LeftAdapter.onItemClickListener() {
            @Override
            public void onClick(int position) {
                for (int i = 0; i < flags.size(); i++) {
                    flags.set(i, false);
                }
                flags.set(position, true);
                leftadapter.notifyDataSetChanged();
                setMeal(position);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop_detail, menu);
        if (shop.getCollectIdList() != null) {
            if (shop.getCollectIdList().contains(BmobUser.getCurrentUser(User.class).getObjectId())) {
                MenuItem menuItem = menu.findItem(R.id.menu_collect);
                menuItem.setTitle("已收藏");
                menuItem.setIcon(R.drawable.ic_favorite_black_24dp);
                isCollect = true;
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_pinle:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("您想要拼单吗？");
                builder.setIcon(R.drawable.logo);
                builder.setMessage("当前拼单人数2\n拼单钱您需支付：25.0\n拼单后您需支付：23.8");
                builder.setPositiveButton("拼单", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showToast("拼单成功！");
                    }
                });
                builder.setNegativeButton("不了谢谢", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.menu_collect:
                if (!isCollect) {
                    item.setTitle("已收藏");
                    item.setIcon(R.drawable.ic_favorite_black_24dp);
                    isCollect = true;
                    toCollect();

                } else {
                    item.setTitle("收藏");
                    item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                    isCollect = false;
                    cancelCollect();
                }

                break;
        }
        return true;
    }

    private void toCollect() {
        BmobRelation relation = new BmobRelation();
        relation.add(BmobUser.getCurrentUser(User.class));
        shop.setCollect(relation);
        if (shop.getCollectIdList() != null) {
            collectIdList = shop.getCollectIdList();
        }
        collectIdList.add(BmobUser.getCurrentUser(User.class).getObjectId());
        shop.setCollectIdList(collectIdList);

        shop.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    showToast("收藏成功");
                } else {
                    showToast("收藏失败" + e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    private void cancelCollect() {
        BmobRelation relation = new BmobRelation();
        relation.remove(BmobUser.getCurrentUser(User.class));
        shop.setCollect(relation);
        if (shop.getCollectIdList() != null) {
            collectIdList = shop.getCollectIdList();
        }
        collectIdList.remove(BmobUser.getCurrentUser(User.class).getObjectId());
        shop.setCollectIdList(collectIdList);

        shop.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    showToast("取消收藏成功");
                } else {
                    showToast("取消收藏失败" + e.getMessage() + e.getErrorCode());
                }
            }
        });
    }

    @OnClick(R.id.ll_shop_car)
    public void toShopCar() {
        Intent intent = new Intent(this, ShopCarActivity.class);
        intent.putExtra("toshopcar", shop.getName());
        jumpTo(intent, false);
    }

    @OnClick(R.id.tv_pay)
    public void toPay() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认支付吗？");
        builder.setIcon(R.drawable.logo);
        builder.setMessage("总计：36.5");
        builder.setPositiveButton("支付", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showToast("支付成功！");
            }
        });
        builder.setNegativeButton("再看看", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 通过反射，设置menu显示icon
     *
     * @param view
     * @param menu
     * @return
     */
    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }


}
