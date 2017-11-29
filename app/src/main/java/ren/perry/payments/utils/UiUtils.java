package ren.perry.payments.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import ren.perry.payments.MyApp;


/**
 * Email: pl.w@outlook.com
 * Created by perry on 2017/9/8.
 */

@SuppressWarnings({"deprecation", "unused"})
public class UiUtils {


    public static int getColor(int id) {
        return MyApp.getContext().getResources().getColor(id);
    }

    @NonNull
    public static String getString(int id) {
        return MyApp.getContext().getResources().getString(id);
    }

    @NonNull
    public static String[] getStringArray(int id) {
        return MyApp.getContext().getResources().getStringArray(id);
    }

    @NonNull
    public static int[] getIntArray(int id) {
        return MyApp.getContext().getResources().getIntArray(id);
    }

    public static View getView(int id) {
        return LayoutInflater.from(MyApp.getContext()).inflate(id, null);
    }

    public static Drawable getDrawable(int id) {
        return MyApp.getContext().getResources().getDrawable(id);
    }

    public static Animation getAnimation(int id) {
        return AnimationUtils.loadAnimation(MyApp.getContext(), id);
    }

}
