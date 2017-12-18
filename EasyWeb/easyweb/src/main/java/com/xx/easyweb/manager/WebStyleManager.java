package com.xx.easyweb.manager;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by xievxin on 2017/11/14.
 */

public class WebStyleManager {

    private static final String TAG = "WebStyleManager";

    public static void setAppStyle(Object o) {
        if(o==null)
            return;
        int sdkInt = Build.VERSION.SDK_INT;
        if(sdkInt<Build.VERSION_CODES.KITKAT)
            return;
        Window window = null;
        if(o instanceof Dialog) {
            window = ((Dialog) o).getWindow();
        }else if(o instanceof Activity) {
            window = ((Activity) o).getWindow();
        }
        if(window==null)
            return;
        window.clearFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if(sdkInt >= Build.VERSION_CODES.M) {
            // 6.0支持改变系统图标（白→黑），没有statusBar阴影
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);
            setMIUIStatusBarDarkMode(true, window);
            setMEIZUStatusBarDarkIcon(true, window);
        }else if(sdkInt >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0以上就设个背景色，有statusBar阴影
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.parseColor("#8C8C8C"));	//5.0状态栏图标白色，看不见
//			window.setStatusBarColor(Color.TRANSPARENT);
        }
//		window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    /**
     * 设置小米沉浸式菜单图标
     * @param darkmode
     * @param window
     */
    private static void setMIUIStatusBarDarkMode(boolean darkmode, Window window) {
        Class<? extends Window> clazz = window.getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(window, darkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            Log.e(TAG, "setMIUIStatusBarDarkIcon: failed");
        }
    }

    /**
     * 设置魅族沉浸式菜单图标
     * @param dark
     * @param window
     */
    private static void setMEIZUStatusBarDarkIcon(boolean dark, Window window) {
        try {
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            Log.e(TAG, "setMEIZUStatusBarDarkIcon: failed");
        }
    }
}
