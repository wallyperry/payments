package ren.perry.payments.utils;

import java.text.DecimalFormat;

/**
 * @author perry
 * @date 2017/11/8
 * WeChat 917351143
 */

public class MathUtils {

    /**
     * 判断double是否是整数
     */
    public static boolean isIntegerForDouble(double d) {
        double eps = 1e-10;  // 精度范围
        return d - Math.floor(d) < eps;
    }

    /**
     * 保留2位精度
     */
    public static String df2Double(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }

    /**
     * 去掉double尾数为0的
     */
    public static String df2Int(double d) {
        DecimalFormat df = new DecimalFormat("###################.###########");
        return df.format(d);
    }
}