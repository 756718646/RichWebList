package com.yongzheng.com.richwebproject.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public final class Utils {

  public static String toBase64(Bitmap bitmap) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
    byte[] bytes = baos.toByteArray();

    return Base64.encodeToString(bytes, Base64.NO_WRAP);
  }

  public final static float raid = 0.1f;

  public static Bitmap toBitmap(Drawable drawable) {
    if (drawable instanceof BitmapDrawable) {
      return ((BitmapDrawable) drawable).getBitmap();
    }

    int width = drawable.getIntrinsicWidth();
    width = width > 0 ? width : 1;
    int height = drawable.getIntrinsicHeight();
    height = height > 0 ? height : 1;

    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, (int)(canvas.getWidth() * raid), (int)(canvas.getHeight() * raid));
    drawable.draw(canvas);

    return bitmap;
  }

  public static Bitmap decodeResource(Context context, int resId) {
    return BitmapFactory.decodeResource(context.getResources(), resId);
  }

  public static long getCurrentTime() {
    return System.currentTimeMillis();
  }
}
