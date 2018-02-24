package com.howietian.newpinle.me;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.howietian.newpinle.R;
import com.howietian.newpinle.base.BaseActivity;
import com.howietian.newpinle.entities.User;
import com.howietian.newpinle.utils.ImageLoader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends BaseActivity {


    @Bind(R.id.tb_info)
    Toolbar tbInfo;
    @Bind(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @Bind(R.id.et_nick)
    EditText etNick;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.tv_logout)
    TextView tvLogout;

    private AlertDialog photoDialog;
    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;
    private static final int CROP_REQUEST = 2;

    private static final int REQUEST_TAKE_PHOTO_PERMISSION = 3;


    private Uri tempFileCameraUri;// 临时拍照保存路径
    private File tempFileCamera; // 临时拍照文件
    private Uri cropFileUri;//临时的裁剪后的照片Uri


    User user = BmobUser.getCurrentUser(User.class);

    BmobFile bf;

    @Override
    public void setMyContentView() {
        setContentView(R.layout.activity_user_info);
    }

    @Override
    public void init() {
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(tbInfo);
        if(getIntent()!=null){
            Intent intent = getIntent();
            String userMsg = intent.getStringExtra(MeFragment.USER_INFO);
            User user = new Gson().fromJson(userMsg,User.class);
            if(user.getAvatar()!=null){
                ImageLoader.with(this,user.getAvatar().getUrl(),ivAvatar);
            }
            if(user.getPhone()!=null){
                etPhone.setText(user.getPhone());
            }
            if(user.getNickName()!=null){
                etNick.setText(user.getNickName());
            }

        }
    }

    private void initListener() {
        tbInfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
                save();
                break;
        }
        return true;
    }


    private void save() {
        String nickName = etNick.getText().toString();
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(nickName)) {
            showToast("昵称不能为空！");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            showToast("手机号码不能为空！");
            return;
        }

        User user = BmobUser.getCurrentUser(User.class);
        user.setNickName(nickName);
        user.setPhone(phone);
        if (bf != null) {
            user.setAvatar(bf);
        }
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    showToast("保存成功！");
                    finish();
                } else {
                    showToast("保存失败" + e.getErrorCode() + e.getMessage());
                }
            }
        });
    }


    @OnClick(R.id.iv_avatar)
    public void chooseAvatar() {
        showDialog();
    }

    @OnClick(R.id.tv_logout)
    public void toLogOut() {
        BmobUser.logOut();
        showToast("退出成功！");
        finish();
    }

    /**
     * 选择相机、相册的提示对话框
     */

    private void showDialog() {
        photoDialog = new AlertDialog.Builder(this).create();
        photoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        photoDialog.show();

        Window window = photoDialog.getWindow();
        window.setContentView(R.layout.dialog_choose_pic);
        window.setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams lp = photoDialog.getWindow().getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        lp.width = dm.widthPixels;
//        设置宽度
        photoDialog.getWindow().setAttributes(lp);

        Button btnCamera = (Button) photoDialog.findViewById(R.id.btn_camera);
        Button btnGallery = (Button) photoDialog.findViewById(R.id.btn_picture);
        Button btnCancel = (Button) photoDialog.findViewById(R.id.btn_cancel);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                toCamera();
                requestCamera();

            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toGallery();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoDialog.dismiss();
            }
        });

    }

    /**
     * 跳转相机
     */
    private void requestCamera() {

        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_TAKE_PHOTO_PERMISSION);
        } else {
            //有权限，直接拍照
            toCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请成功，可以拍照
                toCamera();
            } else {
                showToast("CAMERA PERMISSION DENIED");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    // Android 7.0对权限的管理更加严格
    //通过 contentProvider 解决
    private void toCamera() {
        photoDialog.dismiss();
        tempFileCamera = new File(getFilePath() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DATA, tempFileCamera.getAbsolutePath());
        tempFileCameraUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        //  tempFileCameraUri = Uri.fromFile(tempFileCamera);
//        把拍好的照片保存到这个路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileCameraUri);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    /**
     * 跳转相册
     */
    private void toGallery() {
        photoDialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
//            从图库中选取图片，会返回图片的Uri
            case GALLERY_REQUEST:
                if (data != null) {
                    if (data.getData() != null) {
                        Uri uri = data.getData();
                        cropImage(uri);
                    }
                }

                break;
//            使用相机拍照，不会返回图片的Uri
            case CAMERA_REQUEST:
//                打开相机页面后，如果按返回键也会回调，所以需要判断是否拍摄了照片

                if (resultCode == RESULT_OK && tempFileCameraUri != null) {
                    File file = new File(tempFileCameraUri.getPath());

                    cropImage(tempFileCameraUri);

                }
                break;
            case CROP_REQUEST:

                if (resultCode == RESULT_OK) {
                    /**
                     * 将图片上传服务器
                     */
                    upLoadAvatar();
                }
                break;
        }
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    private void cropImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
//        图片处于可裁剪状态
        intent.putExtra("crop", "true");
//        aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
//        是否缩放
        intent.putExtra("scale", true);
//        设置图片的大小，提高头像的上传速度
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
//        以Uri的方式传递照片
        File cropFile = new File(getFilePath() + "crop.jpg");
        cropFileUri = Uri.fromFile(cropFile);
//        把裁剪好的图片保存到这个路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropFileUri);

        startActivityForResult(intent, CROP_REQUEST);


    }

    /**
     * 使用系统当前日期加以调整作为照片的名称
     */
    private String getFilePath() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/" + dateFormat.format(date);
        return path;
    }


    private void upLoadAvatar() {
        File file = new File(cropFileUri.getPath());
        Log.e("PHOTO", file.getPath());
        bf = new BmobFile(file);
        bf.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    showToast("上传成功！");
                    /**
                     * 上传新的头像后，把之前的头像从服务器上删除
                     */
                    if (user.getAvatar() != null) {
                        BmobFile preAvatar = new BmobFile();
                        preAvatar.setUrl(user.getAvatar().getUrl());
                        preAvatar.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    showToast("之前头像删除成功！");
                                } else {
                                    showToast("之前的头像删除失败");
                                }
                            }
                        });
                    }

                    Log.e("HHH", bf.getUrl());
//                   可以从本地加载图片

                    ImageLoader.with(UserInfoActivity.this, bf.getUrl(), ivAvatar);
                } else {
                    showToast("上传失败！" + e.getErrorCode() + e.getMessage());
                }
            }
        });


    }

}
