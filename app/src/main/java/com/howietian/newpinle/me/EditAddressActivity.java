package com.howietian.newpinle.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.howietian.newpinle.R;
import com.howietian.newpinle.base.BaseActivity;
import com.howietian.newpinle.entities.Address;
import com.howietian.newpinle.entities.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class EditAddressActivity extends BaseActivity {

    @Bind(R.id.tb_edit_address)
    Toolbar tbEditAddress;

    String type = "";
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_address)
    EditText etAddress;

    Address address = null;


    @Override
    public void setMyContentView() {
        setContentView(R.layout.activity_edit_address);
    }

    @Override
    public void init() {
        initData();
        initViews();
        initListener();
    }

    private void initViews() {
        setSupportActionBar(tbEditAddress);

    }

    private void initListener() {
        tbEditAddress.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initData() {
        if (getIntent() != null) {
            type = getIntent().getStringExtra(AddressActivity.CHOOSE_TYPE);
            if (type.equals("save")) {
                tbEditAddress.setTitle("添加地址");
            } else {
                String addressMsg = getIntent().getStringExtra(AddressActivity.ADDRESS_MSG);
                address = new Gson().fromJson(addressMsg, Address.class);
                etName.setText(address.getPeople());
                etName.setSelection(address.getPeople().length());
                etPhone.setText(address.getPhone());
                etAddress.setText(address.getAddress());
                tbEditAddress.setTitle("编辑地址");
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                save(type);
        }
        return true;
    }


    private void save(String type) {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String maddress = etAddress.getText().toString();

        if (TextUtils.isEmpty(name)) {
            showToast("联系人不能为空");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            showToast("联系电话不能为空");
            return;
        }
        if (TextUtils.isEmpty(maddress)) {
            showToast("收货地址不能为空");
            return;
        }

        if (type.equals("save")) {
            Address address = new Address();
            address.setPeople(name);
            address.setPhone(phone);
            address.setAddress(maddress);
            address.setUser(BmobUser.getCurrentUser(User.class));

            address.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        showToast("保存成功！");
                        finish();
                    } else {
                        showToast("保存失败！" + e.getErrorCode() + e.getMessage());
                    }
                }
            });
        } else {
            address.setPeople(name);
            address.setPhone(phone);
            address.setAddress(maddress);
            address.setUser(BmobUser.getCurrentUser(User.class));

            address.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        showToast("保存成功！");
                        finish();
                    } else {
                        showToast("保存失败！" + e.getErrorCode() + e.getMessage());
                    }
                }
            });
        }
    }


}
