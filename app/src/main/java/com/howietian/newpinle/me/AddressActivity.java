package com.howietian.newpinle.me;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.howietian.newpinle.R;
import com.howietian.newpinle.adapters.AddressAdapter;
import com.howietian.newpinle.base.BaseActivity;
import com.howietian.newpinle.entities.Address;
import com.howietian.newpinle.entities.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class AddressActivity extends BaseActivity {


    @Bind(R.id.tb_address)
    Toolbar tbAddress;
    @Bind(R.id.rv_address)
    RecyclerView rvAddress;

    private AddressAdapter addressAdapter;
    private RecyclerView.LayoutManager manager;
    private List<Address> addressList = new ArrayList<>();

    public static final String CHOOSE_TYPE = "choose_type";
    public static final String ADDRESS_MSG = "address_msg";

    @Override
    public void setMyContentView() {
        setContentView(R.layout.activity_address);
    }

    @Override
    public void init() {

        initViews();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();

    }

    private void initData() {
        queryAddress();
    }

    private void initViews() {
        setSupportActionBar(tbAddress);
        addressAdapter = new AddressAdapter(this, addressList);
        rvAddress.setAdapter(addressAdapter);
        manager = new LinearLayoutManager(this);
        rvAddress.setLayoutManager(manager);
        rvAddress.setHasFixedSize(true);
    }

    private void initListener() {
        tbAddress.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addressAdapter.setOnEditClickListener(new AddressAdapter.onEditClickListener() {
            @Override
            public void onClick(int position) {
                showToast("编辑");
                Intent intent = new Intent(AddressActivity.this, EditAddressActivity.class);
                intent.putExtra(CHOOSE_TYPE, "edit");
                Address address = addressList.get(position);
                String addressMsg = new Gson().toJson(address, Address.class);
                intent.putExtra(ADDRESS_MSG, addressMsg);
                jumpTo(intent, false);
            }
        });
        addressAdapter.setOnDeleteClickListener(new AddressAdapter.onDeleteClickListener() {
            @Override
            public void onClick(final int position) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
                builder.setTitle("温馨提示");
                builder.setMessage("确定要删除吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Address address = addressList.get(position);
                        address.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    showToast("删除成功！");
                                } else {
                                    showToast("删除失败" + e.getMessage() + e.getErrorCode());
                                }
                            }
                        });
                        addressList.remove(position);
                        addressAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    private void queryAddress() {
        BmobQuery<Address> query = new BmobQuery<>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser(User.class));
        query.order("-createdAt");
        query.findObjects(new FindListener<Address>() {
            @Override
            public void done(List<Address> list, BmobException e) {
                if (e == null) {
                    if (addressList != null) {
                        addressList.clear();
                    }
                    addressList.addAll(list);
                    addressAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                showToast("添加");
                Intent intent = new Intent(AddressActivity.this, EditAddressActivity.class);
                intent.putExtra(CHOOSE_TYPE, "save");
                jumpTo(intent, false);
                break;
        }
        return true;
    }
}
