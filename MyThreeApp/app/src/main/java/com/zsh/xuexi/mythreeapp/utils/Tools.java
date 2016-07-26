package com.zsh.xuexi.mythreeapp.utils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by zsh on 2016/7/26.
 */
public class Tools {
    /**
     * 强制满屏
     * @param a 表示Activity
     */
    public static void fullActivity(Activity a){
        a.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
