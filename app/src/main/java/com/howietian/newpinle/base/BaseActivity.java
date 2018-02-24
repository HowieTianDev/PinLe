package com.howietian.newpinle.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    /**
     * 抽象方法，每个布局设置布局文件，需要子类去具体实现，下面加上ButterKnife的初始化
     */
    public abstract void setMyContentView();

    /**
     * 抽象方法，做一些初始化工作
     */
    public abstract void init();

    private void initLayout() {
        setMyContentView();
        ButterKnife.bind(this);
        init();
    }

    /**
     * 常用的几个方法
     */
    public void showToast(String s) {
        if (!TextUtils.isEmpty(s)) {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
    }

    public void jumpTo(Class<?> clazz, boolean isFinish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }

    public void jumpTo(Intent intent,boolean isFinish){
        startActivity(intent);
        if(isFinish){
            finish();
        }
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
