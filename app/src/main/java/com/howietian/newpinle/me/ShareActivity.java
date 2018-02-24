package com.howietian.newpinle.me;

import android.support.v7.widget.Toolbar;
import android.view.View;


import com.howietian.newpinle.R;
import com.howietian.newpinle.base.BaseActivity;

import butterknife.Bind;

public class ShareActivity extends BaseActivity {

    @Bind(R.id.tb_help)
    Toolbar tbHelp;

    @Override
    public void setMyContentView() {
        setContentView(R.layout.activity_share);
    }

    @Override
    public void init() {
        initListener();
    }


    private void initListener() {
        setSupportActionBar(tbHelp);
        tbHelp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
