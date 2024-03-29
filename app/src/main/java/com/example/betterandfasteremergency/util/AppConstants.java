package com.example.betterandfasteremergency.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.example.betterandfasteremergency.R;

public class AppConstants {
    public static final int GPS_REQUEST = 1001;
    public static final int LOCATION_REQUEST = 1000;

    @SuppressLint("ResourceAsColor")
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            Drawable background = activity.getResources().getDrawable(R.drawable.ic_app_bg1);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);

        }
    }
}