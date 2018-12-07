package com.dubhe.broken.dubheweather.application;

import android.app.Application;
import android.content.SharedPreferences;

import com.dubhe.broken.dubheweather.constant.ServiceInfo;

/**
 * 作者：DubheBroken
 * 时间：2018/12/7 22:59:10
 * 邮箱：z1574507001@gmail.com
 * 说明：
 */
public class AppData extends Application {

    private static String lang;
    private static String group;
    private static String unit;
    private static SharedPreferences settings;
    public static final String PREFS_NAME = "config";//设置文件名称

    @Override
    public void onCreate() {
        super.onCreate();
        AppData.settings = getSharedPreferences(PREFS_NAME, 0);
        AppData.lang = settings.getString(ServiceInfo.LANG, "zh-cn");
        AppData.group = settings.getString(ServiceInfo.HotCity.GROUP, "cn");
        AppData.unit = settings.getString(ServiceInfo.NowWeather.UNIT, "m");
    }



}
