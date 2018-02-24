package com.howietian.newpinle.entrance;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.howietian.newpinle.MainActivity;
import com.howietian.newpinle.R;
import com.howietian.newpinle.app.MyApp;
import com.howietian.newpinle.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler();

    @Override
    public void setMyContentView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void init() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(MyApp.isLogin()){
                    jumpTo(MainActivity.class,true);
                }else{
                    jumpTo(LoginActivity.class,true);
                }
            }
        },2000);

    }
}
