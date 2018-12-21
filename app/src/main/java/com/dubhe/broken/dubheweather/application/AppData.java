package com.dubhe.broken.dubheweather.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.dubhe.broken.dubheweather.R;
import com.dubhe.broken.dubheweather.constant.ServiceInfo;
import com.dubhe.broken.dubheweather.utils.ResHelper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 作者：DubheBroken
 * 时间：2018/12/7 22:59:10
 * 邮箱：z1574507001@gmail.com
 * 说明：
 */
public class AppData extends Application {

    public static final String TMP_UNIT = "tmp_unit";
    public static final String VISIBILITY = "visibility";
    private static String lang;
    private static String group;
    private static String unit;
    private static SharedPreferences settings;
    public static final String PREFS_NAME = "config";//设置文件名称

    @Override
    public void onCreate() {
        super.onCreate();
        AppData.settings = getSharedPreferences(PREFS_NAME, 0);
        Locale locale = getResources().getConfiguration().locale;
        String lang = "en";
        if (locale.equals(Locale.SIMPLIFIED_CHINESE)) {
            lang = "cn";
        } else if (locale.equals(Locale.TRADITIONAL_CHINESE)) {
            lang = "hk";
        } else if (locale.equals(Locale.ENGLISH)) {
            lang = "en";
        } else if (locale.equals(Locale.GERMAN)) {
            lang = "de";
        } else if (locale.equals(Locale.FRENCH)) {
            lang = "fr";
        } else if (locale.equals(Locale.ITALIAN)) {
            lang = "it";
        } else if (locale.equals(Locale.JAPANESE)) {
            lang = "jp";
        } else if (locale.equals(Locale.KOREAN)) {
            lang = "kr";
        }
        AppData.lang = settings.getString(ServiceInfo.LANG, lang);
        AppData.group = settings.getString(ServiceInfo.HotCity.GROUP, ServiceInfo.HotCity.GROUP_ALL);
        AppData.unit = settings.getString(ServiceInfo.NowWeather.UNIT, "m");
    }

    public static void saveData() {
        SharedPreferences.Editor editor = AppData.settings.edit();
        editor.putString(ServiceInfo.LANG, AppData.lang);
        editor.putString(ServiceInfo.HotCity.GROUP, AppData.group);
        editor.putString(ServiceInfo.NowWeather.UNIT, AppData.unit);
        editor.apply();
    }

    public static String getSettingCode(Context context, int id) {
        String str = context.getResources().getResourceName(id);//获取ID名称
        return str.substring(str.indexOf("/") + 1);//把前半部分包名切掉
    }

    public static String getSettingStr(Context context, String code) {
        return context.getResources().getString(ResHelper.getResId(code, R.string.class));
    }

    public static Map getUnitStr() {
        Map<String, String> map = new HashMap<>();
        switch (AppData.unit) {
            case "m":
                map.put(TMP_UNIT, "℃");
                map.put(VISIBILITY, "km");
                break;
            case "i":
                map.put(TMP_UNIT, "℉");
                map.put(VISIBILITY, "mile");
                break;
        }
        return map;
    }

    public static String getLang() {
        return lang;
    }

    public static void setLang(String lang) {
        AppData.lang = lang;
    }

    public static String getGroup() {
        return group;
    }

    public static void setGroup(String group) {
        AppData.group = group;
    }

    public static String getUnit() {
        return unit;
    }

    public static void setUnit(String unit) {
        AppData.unit = unit;
    }
}
