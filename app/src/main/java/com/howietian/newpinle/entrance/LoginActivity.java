package com.howietian.newpinle.entrance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.howietian.newpinle.MainActivity;
import com.howietian.newpinle.R;
import com.howietian.newpinle.base.BaseActivity;
import com.howietian.newpinle.entities.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.til_phone)
    TextInputLayout tilPhone;
    @Bind(R.id.til_pwd)
    TextInputLayout tilPwd;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_pwd)
    EditText etPwd;

    @Override
    public void setMyContentView() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void init() {
        initListener();
        initData();
    }


    private void initData() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            String phone = intent.getStringExtra("username");
            String pwd = intent.getStringExtra("pwd");
            etPhone.setText(phone);
            etPwd.setText(pwd);
            if (phone != null) {
                etPhone.setSelection(phone.length());
            }

        }
    }


    // 只要输入文字发生变化，就将 error 设为 null
    private void initListener() {
        EditText metPhone = tilPhone.getEditText();
        EditText metPwd = tilPwd.getEditText();

        metPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilPhone.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        metPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilPwd.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @OnClick(R.id.tv_register)
    public void toRegister() {
        jumpTo(RegisterActivity.class, false);
    }

    @OnClick(R.id.btn_login)
    public void toLogin() {
        EditText etPhone = tilPhone.getEditText();
        EditText etPwd = tilPwd.getEditText();

        String phone = etPhone.getText().toString();
        String pwd = etPwd.getText().toString();

        if (!isPhoneNum(phone)) {
            tilPhone.setError("手机号码不合法");
            return;
        }
        if (pwd.length() < 6) {
            tilPwd.setError("密码至少为6位");
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在登陆...");
        progressDialog.show();

        User user = new User();
        user.setUsername(phone);
        user.setPassword(pwd);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    showToast("登陆成功！");
                    jumpTo(MainActivity.class, true);
                } else {
                    showToast("登陆失败" + e.getMessage() + e.getErrorCode());
                }
                progressDialog.dismiss();
            }
        });


    }

    //利用正则表达式判断手机号码的合法性,已经改好
    private boolean isPhoneNum(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,1,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.find();
    }


}
