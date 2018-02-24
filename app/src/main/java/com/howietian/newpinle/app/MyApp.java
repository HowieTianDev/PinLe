package com.howietian.newpinle.app;

import android.app.Application;

import com.howietian.newpinle.entities.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by 83624 on 2018/2/11 0011.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, Constant.BMOB_APP_ID);
    }

    public static boolean isLogin() {
        User user = BmobUser.getCurrentUser(User.class);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }
}
