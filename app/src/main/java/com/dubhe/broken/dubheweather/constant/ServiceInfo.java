package com.dubhe.broken.dubheweather.constant;

import android.util.Log;

import com.dubhe.broken.dubheweather.R;
import com.dubhe.broken.dubheweather.application.AppData;

import java.util.Map;

import static com.dubhe.broken.dubheweather.constant.ServiceInfo.HotCity.NUMBER_D;

/**
 * 作者：DubheBroken
 * 时间：2018/12/6 20:37:50
 * 邮箱：z1574507001@gmail.com
 * 说明：
 */
public class ServiceInfo {

    //图片资源前缀
    public static final String IMG_TAG = "weatherimg_";
    public static final String BACK_TAG = "weatherback_";

    //以下是支持的参数，D表示默认值
    //key 必传 直接用KEY_D就好
    public static final String key = "fa0a979cb34b4482b2dfeac2b00d2a0a";
    public static final String KEY = "key";
    public static final String KEY_D = KEY + "=" + key;

    //语言 可选 默认ZH-CN
    public static final String LANG = "lang";
    public static final String UNIT = "unit";

    public static class Status {
        public static final String OK = "ok";
        //数据正常
        public static final String INVALID_KEY = "invalid key";
        //错误的key，请检查你的key是否输入以及是否输入有误
        public static final String UNKNOWN_LOCATION = "unknown location";
        //未知或错误城市/地区
        public static final String NO_DATA_FOR_THIS_LOCATION = "no data for this location";
        //该城市/地区没有你所请求的数据
        public static final String NO_MORE_REQUESTS = "no more requests";
        //超过访问次数，需要等到当月最后一天24点（免费用户为当天24点）后进行访问次数的重置或升级你的访问量
        public static final String PARAM_INVALID = "param invalid";
        //参数错误，请检查你传递的参数是否正确
        public static final String TOO_FAST = "too fast";
        //超过限定的QPM，请参考QPM说明
        public static final String DEAD = "dead";
        //无响应或超时，接口服务异常请联系我们
        public static final String PERMISSION_DENIED = "permission denied";
        //无访问权限，你没有购买你所访问的这部分服务
        public static final String SIGN_ERROR = "sign error";
        //签名错误，请参考签名算法
        public static final String UNKNOWN_CITY = "unknown city";

        public static String getMessage(String status_code) {
            switch (status_code) {
                case OK:
                    return "数据正常";
                case INVALID_KEY:
                    return "错误的key，请检查你的key是否输入以及是否输入有误";
                case UNKNOWN_LOCATION:
                    return "未知或错误城市/地区";
                case UNKNOWN_CITY:
                    return "未知或错误城市";
                case NO_DATA_FOR_THIS_LOCATION:
                    return "该城市/地区没有你所请求的数据";
                case NO_MORE_REQUESTS:
                    return "超过访问次数，需要等到当月最后一天24点（免费用户为当天24点）后进行访问次数的重置或升级你的访问量";
                case PARAM_INVALID:
                    return "参数错误，请检查你传递的参数是否正确";
                case TOO_FAST:
                    return "超过限定的QPM，请参考QPM说明";
                case DEAD:
                    return "无响应或超时，接口服务异常请联系我们";
                case PERMISSION_DENIED:
                    return "无访问权限，你没有购买你所访问的这部分服务";
                case SIGN_ERROR:
                    return "签名错误，请参考签名算法";
                default:
                    return "未知错误" + status_code;
            }
        }
    }

    public static class HotCity {

        //URL
        private static final String HOT_CITY_URL = "https://search.heweather.com/top?";

        //国家 必传 默认CN
        public static final String GROUP = "group";
        public static final String GROUP_CN = "cn";
        public static final String GROUP_OVER = "overseas";
        public static final String GROUP_ALL = "world";

        //数量 可选 1-20 默认10
        public static final String NUMBER = "number";
        public static final String NUMBER_D = NUMBER + "=" + "20";

        /**
         * @return 返回请求URL
         */
        public static String getUri(Map map) {
            String str = HOT_CITY_URL + KEY_D;

            if (map.get(GROUP) != null && map.get(GROUP).toString().length() > 0) {
                str += "&" + GROUP + "=" + map.get(GROUP).toString();
            } else {
                str += "&" + GROUP + "=" + GROUP_CN;
            }

            if (map.get(NUMBER) != null && map.get(NUMBER).toString().length() > 0) {
                str += "&" + NUMBER + "=" + map.get(NUMBER).toString();
            } else {
                str += "&" + NUMBER_D;
            }

            if (map.get(LANG) != null && map.get(LANG).toString().length() > 0) {
                str += "&" + LANG + "=" + map.get(LANG).toString();
            } else {
                str += "&" + LANG + "=" + AppData.getLang();
            }
            Log.i("热门城市URL：", str);
            return str;
        }


    }

    public static class SearchCity {
        //URL
        public static final String URL = "https://search.heweather.com/find?";

        //位置
        //输入需要查询的城市名称，支持模糊搜索
//        location=北      中文（至少一个汉字）
//        location=北京    中文（至少一个汉字）
//        location=beij    英文（至少2个字母）
//        location=60.194.130.1    IP地址
//        location=116.4,39.1      坐标（经度在前纬度在后，英文,分割）
        public static final String LOCATION = "location";

        //查询方式
        public static final String MODE = "mode";
        public static final String MODE_M = MODE + "=match";//模糊查询
        public static final String MODE_E = MODE + "=equal";//精确查询

        //分组
        //支持国家名称缩写
        // 最多20个，逗号分隔
        public static final String GROUP = "group";
        public static final String GROUP_W = GROUP + "=world";//全球
        public static final String GROUP_S = GROUP + "=scenic";//国内4A/5A风景区
        public static final String GROUP_O = GROUP + "=overseas";//海外城市
        public static final String GROUP_ZH = GROUP + "=cn";//国内

        //数量 1-20 使用IP和坐标时只返回一个
        //默认10个，按相关度排列
        public static final String NUMBER = "number";

        public static String getURL(Map map) {
            String str = URL + KEY_D;

            if (map.get(LOCATION) != null && map.get(LOCATION).toString().length() > 0) {
                str += "&" + LOCATION + "=" + map.get(LOCATION).toString();
            } else {
                str += "&" + "";
            }

            if (map.get(GROUP) != null && map.get(GROUP).toString().length() > 0) {
                str += "&" + GROUP + "=" + map.get(GROUP).toString();
            } else {
                str += "&" + GROUP + "=" + AppData.getGroup();
            }

            if (map.get(MODE) != null && map.get(MODE).toString().length() > 0) {
                str += "&" + MODE + "=" + map.get(MODE).toString();
            } else {
                str += "&" + MODE_M;
            }

            if (map.get(NUMBER) != null && map.get(NUMBER).toString().length() > 0) {
                str += "&" + NUMBER + "=" + map.get(NUMBER).toString();
            } else {
                str += "&" + NUMBER_D;
            }

            if (map.get(LANG) != null && map.get(LANG).toString().length() > 0) {
                str += "&" + LANG + "=" + map.get(LANG).toString();
            } else {
                str += "&" + LANG + "=" + AppData.getLang();
            }
            Log.i("搜索城市URL：", str);
            return str;
        }

    }

    public static class NowWeather {
        //请求地址
        public static final String URL = "https://free-api.heweather.com/s6/weather/now?";

        //位置
        //1.城市ID
        //2.经度,纬度
        //3.城市名称拼音or汉字
        //4.城市名称,上级城市      消歧义
        //5.IP地址
        //6.根据请求来源自动判断     默认
        public static final String LOCATION = "location";
        public static final String LOCATION_D = LOCATION + "=auto_ip";//自动判断

        //单位 可选 公制（m）或英制（i） 默认为公制
        public static final String UNIT = "unit";

        public static String getUrl(Map map) {
            String str = URL + KEY_D;

            if (map.get(LOCATION) != null && map.get(LOCATION).toString().length() > 0) {
                str += "&" + LOCATION + "=" + map.get(LOCATION).toString();
            } else {
                str += "&" + LOCATION_D;
            }

            if (map.get(UNIT) != null && map.get(UNIT).toString().length() > 0) {
                str += "&" + UNIT + "=" + map.get(UNIT).toString();
            }

            if (map.get(LANG) != null && map.get(LANG).toString().length() > 0) {
                str += "&" + LANG + "=" + map.get(LANG).toString();
            } else {
                str += "&" + LANG + "=" + AppData.getLang();
            }
            Log.i("即时天气URL：", str);
            return str;
        }

    }

    public static class Language {
        public static final String CN = "简体中文";
        public static final String HK = "繁体中文";
        public static final String EN = "English";
        public static final String DE = "Deutsch";
        public static final String FR = "Le français";
        public static final String IT = "lingua italiana";
        public static final String JP = "日本語";
        public static final String KR = "한국어";
    }

    public static class LifeStyle {
        //请求地址
        public static final String URL = "https://free-api.heweather.com/s6/weather/lifestyle?";

        //位置
        //1.城市ID
        //2.经度,纬度
        //3.城市名称拼音or汉字
        //4.城市名称,上级城市      消歧义
        //5.IP地址
        //6.根据请求来源自动判断     默认
        public static final String LOCATION = "location";
        public static final String LOCATION_D = LOCATION + "=auto_ip";//自动判断

        //单位 可选 公制（m）或英制（i） 默认为公制
        public static final String UNIT = "unit";

        public static String getUrl(Map map) {
            String str = URL + KEY_D;

            if (map.get(LOCATION) != null && map.get(LOCATION).toString().length() > 0) {
                str += "&" + LOCATION + "=" + map.get(LOCATION).toString();
            } else {
                str += "&" + LOCATION_D;
            }

            if (map.get(UNIT) != null && map.get(UNIT).toString().length() > 0) {
                str += "&" + UNIT + "=" + map.get(UNIT).toString();
            }

            if (map.get(LANG) != null && map.get(LANG).toString().length() > 0) {
                str += "&" + LANG + "=" + map.get(LANG).toString();
            } else {
                str += "&" + LANG + "=" + AppData.getLang();
            }
            Log.i("生活指数URL：", str);
            return str;
        }
    }
}
