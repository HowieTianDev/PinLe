package com.howietian.newpinle.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.howietian.newpinle.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {


    BaseActivity baseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseActivity = (BaseActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = createMyView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    public abstract View createMyView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void init();

    /**
     * 一些常用的方法
     */

    public void showToast(String s) {
        baseActivity.showToast(s);
    }

    public void jumpTo(Class<?> clazz, boolean isFinish) {
        baseActivity.jumpTo(clazz, isFinish);
    }

    public void jumpTo(Intent intent, boolean isFinish) {
        baseActivity.jumpTo(intent, isFinish);
    }


    /**
     * 检查EditText是否为空
     */
    public boolean isEmpty(EditText... ets) {
        for (EditText et : ets) {
            String s = et.getText().toString();
            if (TextUtils.isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

}
