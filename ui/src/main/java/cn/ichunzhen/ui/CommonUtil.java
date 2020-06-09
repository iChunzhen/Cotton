package cn.ichunzhen.ui;

import android.content.Context;

/**
 * @Author yuancz
 * @Date 2019/10/18-14:06
 * @Email ichunzhen6@gmail.com
 */
public class CommonUtil {
    public static int dp2px(int dipValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float sp2px(int spValue, Context context) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
