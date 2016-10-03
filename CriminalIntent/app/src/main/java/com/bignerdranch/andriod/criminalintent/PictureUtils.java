package com.bignerdranch.andriod.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.MaskFilter;
import android.graphics.Point;

/**
 * Created by YKC on 2016. 9. 29..
 */
public class PictureUtils {
    public static Bitmap getScaledbitmap(String path, int destWidth, int destHeight) {
        // 파일의 이미지 크기를 알아낸다

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        // 얼마나 크기를 조정할지 파악한다.
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / destHeight);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        // Bitmap을 생성한다.
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap getScaledBitmap(String path, Activity activity) { //화면크기확인
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay()
                .getSize(size);
        return getScaledbitmap(path, size.x, size.y);
    }
}
