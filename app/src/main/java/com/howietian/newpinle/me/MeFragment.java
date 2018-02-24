package com.howietian.newpinle.me;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.howietian.newpinle.R;
import com.howietian.newpinle.app.MyApp;
import com.howietian.newpinle.base.BaseFragment;
import com.howietian.newpinle.entities.User;
import com.howietian.newpinle.entrance.LoginActivity;
import com.howietian.newpinle.utils.ImageLoader;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {
    private static final String ME_FRAGMENT = "me_fragment";
    public static final String USER_INFO = "user_info";
    @Bind(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.rl_userInfo)
    RelativeLayout rlUserInfo;
    @Bind(R.id.tv_phone)
    TextView tvPhone;


    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment newInstance(String args) {
        MeFragment fragment = new MeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ME_FRAGMENT, args);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View createMyView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void init() {

    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {

        if (MyApp.isLogin()) {
            User user = BmobUser.getCurrentUser(User.class);
            if (user.getAvatar() != null) {
                ImageLoader.with(getContext(), user.getAvatar().getUrl(), ivAvatar);
            }
            if (user.getNickName() != null) {
                tvUserName.setText(user.getNickName());
            }
            if (user.getPhone() != null) {
                tvPhone.setText(user.getPhone());
            }
        } else {
            ivAvatar.setImageResource(R.drawable.user_image);
            tvPhone.setText("");
            tvUserName.setText("请登陆");
        }


    }


    private void toUserInfo() {

        if (MyApp.isLogin()) {
            String userInfo = new Gson().toJson(BmobUser.getCurrentUser(User.class), User.class);
            Intent intent = new Intent(getContext(), UserInfoActivity.class);
            intent.putExtra(USER_INFO, userInfo);
            startActivity(intent);
        } else {
            jumpTo(LoginActivity.class, false);
        }
    }


    @OnClick({R.id.rl_userInfo, R.id.ll_address, R.id.ll_collection, R.id.ll_setting, R.id.ll_share, R.id.ll_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_userInfo:
                toUserInfo();
                break;
            case R.id.ll_address:
                if (MyApp.isLogin()) {
                    jumpTo(AddressActivity.class, false);
                } else {
                    showToast("请先登陆~");
                }
                break;
            case R.id.ll_collection:
                if (MyApp.isLogin()) {
                    jumpTo(MyCollectionActivity.class, false);
                } else {
                    showToast("请先登陆~");
                }
                break;
            case R.id.ll_setting:
                if (MyApp.isLogin()) {
                    toUserInfo();
                } else {
                    showToast("请先登陆~");
                }
                break;
            case R.id.ll_share:
                if (MyApp.isLogin()) {
                    jumpTo(ShareActivity.class, false);
                } else {
                    showToast("请先登陆~");
                }
                break;
            case R.id.ll_help:
                if (MyApp.isLogin()) {
                    jumpTo(HelpActivity.class, false);
                } else {
                    showToast("请先登陆~");
                }
                break;
        }
    }
}
