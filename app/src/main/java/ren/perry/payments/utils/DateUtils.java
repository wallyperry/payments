package ren.perry.payments.utils;

import android.annotation.SuppressLint;

import java.util.Calendar;

/**
 * @author perry
 * @date 2017/11/28
 * WeChat 917351143
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {

    public static Calendar getFirstDay() {
        Calendar cale;
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return cale;
    }

    public static Calendar getLastDay() {
        Calendar cale;
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return cale;
    }
}
