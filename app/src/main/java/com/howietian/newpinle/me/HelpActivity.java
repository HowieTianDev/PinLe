package com.howietian.newpinle.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.howietian.newpinle.R;
import com.howietian.newpinle.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpActivity extends BaseActivity {


    @Bind(R.id.tb_help)
    Toolbar tbHelp;
    @Bind(R.id.et_email)
    EditText etEmail;
    @Bind(R.id.et_advice)
    EditText etAdvice;

    @Override
    public void setMyContentView() {
        setContentView(R.layout.activity_help);
    }

    @Override
    public void init() {
        setSupportActionBar(tbHelp);
        tbHelp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        String email = etEmail.getText().toString();
        String advice = etAdvice.getText().toString();
        if (TextUtils.isEmpty(email)) {
            showToast("请填写您的邮箱");
            return;
        }
        if (TextUtils.isEmpty(advice)) {
            showToast("请填写意见哦");
            return;
        }
        showToast("已提交，请等待回复");
        finish();
    }
}
