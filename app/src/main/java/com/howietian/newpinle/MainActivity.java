package com.howietian.newpinle;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.howietian.newpinle.base.BaseActivity;
import com.howietian.newpinle.home.HomeFragment;
import com.howietian.newpinle.me.MeFragment;
import com.howietian.newpinle.order.OrderFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;
    @Bind(R.id.bnvBar)
    BottomNavigationBar bnvBar;

    private HomeFragment homeFragment;
    private OrderFragment orderFragment;
    private MeFragment meFragment;

    @Override
    public void setMyContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void init() {
        initView();
        initListener();
    }

    private void initView() {
        bnvBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bnvBar.setMode(BottomNavigationBar.MODE_FIXED);
        bnvBar.addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, R.string.home).setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_description_black_24dp, R.string.order)).setActiveColor(R.color.colorPrimary)
                .addItem(new BottomNavigationItem(R.drawable.ic_face_black_24dp, R.string.me)).setActiveColor(R.color.colorPrimary)
                .initialise();
        chooseFragments(0);
    }

    private void initListener() {
        // 底部导航的点击跳转
        bnvBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                chooseFragments(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    /**
     * 隐藏fragment
     */

    private void hideFragments(FragmentTransaction ft) {
        if (homeFragment != null) {
            ft.hide(homeFragment);
        }
        if (orderFragment != null) {
            ft.hide(orderFragment);
        }
        if (meFragment != null) {
            ft.hide(meFragment);
        }
    }

    /**
     * 选择fragment
     */
    private void chooseFragments(int position) {
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        hideFragments(ft);
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance("home_fragment");
                    ft.add(R.id.frameLayout, homeFragment, homeFragment.getClass().getName());
                }
                ft.show(homeFragment);
                break;
            case 1:
                if (orderFragment == null) {
                    orderFragment = OrderFragment.newInstance("order_fragment");
                    ft.add(R.id.frameLayout, orderFragment, orderFragment.getClass().getName());
                }
                ft.show(orderFragment);
                break;
            case 2:
                if (meFragment == null) {
                    meFragment = MeFragment.newInstance("me_fragment");
                    ft.add(R.id.frameLayout, meFragment, meFragment.getClass().getName());
                }
                ft.show(meFragment);
                break;

        }
        ft.commit();
    }


    private long lastClickBackTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastClickBackTime > 2000) { // 后退阻断
            showToast("再点一次退出");
            lastClickBackTime = System.currentTimeMillis();
        } else { // 关掉app
            super.onBackPressed();
        }
    }

}
