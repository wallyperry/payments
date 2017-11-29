package ren.perry.payments.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import ren.perry.payments.MyApp;


/**
 * Email: pl.w@outlook.com
 *
 * @author perry
 * @date 2017/8/18
 */

public class AppUtils {

    public static String getVersionName() {
        try {
            PackageInfo info = MyApp.getContext().getPackageManager().getPackageInfo(MyApp.getContext().getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
    }

    public static int getVersionCode() {
        try {
            PackageInfo info = MyApp.getContext().getPackageManager().getPackageInfo(MyApp.getContext().getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
