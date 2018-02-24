package com.howietian.newpinle.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.howietian.newpinle.R;

/**
 * Created by 83624 on 2018/2/11 0011.
 */

public class ImageLoader {
    public static void with(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    public static void with(Context context, Uri uri, ImageView imageView) {
        Glide.with(context).load(uri).into(imageView);
    }


}
