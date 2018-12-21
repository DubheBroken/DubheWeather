package com.dubhe.broken.dubheweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dubhe.broken.dubheweather.R;
import com.dubhe.broken.dubheweather.application.AppData;
import com.dubhe.broken.dubheweather.constant.ServiceInfo;
import com.dubhe.broken.dubheweather.entity.FutureWeather;
import com.dubhe.broken.dubheweather.entity.HotCity;
import com.dubhe.broken.dubheweather.entity.LifeStyle;
import com.dubhe.broken.dubheweather.entity.Weather;
import com.dubhe.broken.dubheweather.utils.DrawableTintUtil;
import com.dubhe.broken.dubheweather.utils.HttpUtils;
import com.dubhe.broken.dubheweather.utils.ResHelper;
import com.dubhe.broken.dubheweather.utils.ToastUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 作者：DubheBroken
 * 时间：2018/12/7 14:13:10
 * 邮箱：z1574507001@gmail.com
 * 说明：
 */

public class WeatherActivity extends AppCompatActivity {

    private static final int TODAY_MODE = 0;
    private static final int ONEDAY_LATER_MODE = 1;
    private static final int TOWDAY_LATER_MODE = 2;
    private Context context = this;
    private TextView btnChoosecity;
    private TextView textCityname;
    private TextView btnSetting;
    private ConstraintLayout constraintWeatherToolbar;
    private TextView textTmpWeather;
    private TextView textCondtxtWeather;
    private TextView textWinddir;
    private TextView textWindsc;
    private View line1;
    private TextView textFlTag;
    private TextView textFl;
    private View line2;
    private TextView textHumTag;
    private TextView textHum;
    private View line3;
    private TextView textPcpnTag;
    private TextView textPcpn;
    private View line4;
    private TextView textVisTag;
    private TextView textVis;
    private ConstraintLayout constraintMoreinfoWeather;
    private ConstraintLayout constraintTmpWeather;
    private ConstraintLayout constraintWeather;
    private String TAG = "con.dubhe.broken.dubheweather.activity.WeatherActivity";
    private String cid;
    private TextView textTitleimg;
    private TextView textCondimg;
    private TextView textCitytmp;
    private ConstraintLayout constraintWinddir;
    private ConstraintLayout constraintFl;
    private ConstraintLayout constraintHum;
    private ConstraintLayout constraintPcpn;
    private ConstraintLayout constraintVis;
    private ConstraintLayout constraintLifestyle;
    private TextView textLifestyleTag;
    private CardView cardComf;
    private TextView textComfTag;
    private TextView textComf;
    private CardView cardCw;
    private TextView textCwTag;
    private View line5;
    private TextView textCw;
    private CardView cardDrsg;
    private TextView textDrsgTag;
    private View line6;
    private TextView textDrsg;
    private CardView cardFlu;
    private TextView textFluTag;
    private View line7;
    private TextView textFlu;
    private CardView cardSport;
    private TextView textSportTag;
    private View line8;
    private TextView textSport;
    private CardView cardTrav;
    private TextView textTravTag;
    private View line9;
    private TextView textTrav;
    private CardView cardUv;
    private TextView textUvTag;
    private View line10;
    private TextView textUv;
    private CardView cardAir;
    private TextView textAirTag;
    private View line11;
    private TextView textAir;
    private boolean isExit = false;
    private SparseArray sparseArray_today;
    private SparseArray sparseArray_future;
    private TextView textTolastday;
    private TextView textNowDay;
    private TextView textTonextday;
    private int now_mode = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public void onBackPressed() {
        exitByDoubleClick();
    }

    /**
     * 双击返回键退出
     */
    private void exitByDoubleClick() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true;
            Toast.makeText(context, getResources().getString(R.string.exit_warning), Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;//取消退出
                }
            }, 2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            System.exit(0);
        }
    }

    private void initView() {
        btnChoosecity = findViewById(R.id.btn_choosecity);
        textCityname = findViewById(R.id.text_cityname);
        btnSetting = findViewById(R.id.btn_setting);
        constraintWeatherToolbar = findViewById(R.id.constraint_weather_toolbar);
        textTmpWeather = findViewById(R.id.text_tmp_weather);
        textCondtxtWeather = findViewById(R.id.text_condtxt_weather);
        textWinddir = findViewById(R.id.text_winddir);
        textWindsc = findViewById(R.id.text_windsc);
        line1 = findViewById(R.id.line1);
        textFlTag = findViewById(R.id.text_fl_tag);
        textFl = findViewById(R.id.text_fl);
        textHumTag = findViewById(R.id.text_hum_tag);
        textHum = findViewById(R.id.text_hum);
        line3 = findViewById(R.id.line3);
        textPcpnTag = findViewById(R.id.text_pcpn_tag);
        textPcpn = findViewById(R.id.text_pcpn);
        textVisTag = findViewById(R.id.text_vis_tag);
        textVis = findViewById(R.id.text_vis);
        constraintTmpWeather = findViewById(R.id.constraint_tmp_weather);
        constraintWeather = findViewById(R.id.constraint_weather);
        textTitleimg = findViewById(R.id.text_titleimg);
        textCondimg = findViewById(R.id.text_condimg);
        textCitytmp = findViewById(R.id.text_citytmp);
        constraintWinddir = findViewById(R.id.constraint_winddir);
        constraintFl = findViewById(R.id.constraint_fl);
        constraintHum = findViewById(R.id.constraint_hum);
        constraintPcpn = findViewById(R.id.constraint_pcpn);
        constraintVis = findViewById(R.id.constraint_vis);
        constraintLifestyle = findViewById(R.id.constraint_lifestyle);
        textLifestyleTag = findViewById(R.id.text_lifestyle_tag);
        cardComf = findViewById(R.id.card_comf);
        textComfTag = findViewById(R.id.text_comf_tag);
        line4 = findViewById(R.id.line4);
        textComf = findViewById(R.id.text_comf);
        cardCw = findViewById(R.id.card_cw);
        textCwTag = findViewById(R.id.text_cw_tag);
        line5 = findViewById(R.id.line5);
        textCw = findViewById(R.id.text_cw);
        cardDrsg = findViewById(R.id.card_drsg);
        textDrsgTag = findViewById(R.id.text_drsg_tag);
        line6 = findViewById(R.id.line6);
        textDrsg = findViewById(R.id.text_drsg);
        cardFlu = findViewById(R.id.card_flu);
        textFluTag = findViewById(R.id.text_flu_tag);
        line7 = findViewById(R.id.line7);
        textFlu = findViewById(R.id.text_flu);
        cardSport = findViewById(R.id.card_sport);
        textSportTag = findViewById(R.id.text_sport_tag);
        line8 = findViewById(R.id.line8);
        textSport = findViewById(R.id.text_sport);
        cardTrav = findViewById(R.id.card_trav);
        textTravTag = findViewById(R.id.text_trav_tag);
        line9 = findViewById(R.id.line9);
        textTrav = findViewById(R.id.text_trav);
        cardUv = findViewById(R.id.card_uv);
        textUvTag = findViewById(R.id.text_uv_tag);
        line10 = findViewById(R.id.line10);
        textUv = findViewById(R.id.text_uv);
        cardAir = findViewById(R.id.card_air);
        textAirTag = findViewById(R.id.text_air_tag);
        line11 = findViewById(R.id.line11);
        textAir = findViewById(R.id.text_air);

        textFlTag.setText(getResources().getString(R.string.fl));
        textHumTag.setText(getResources().getString(R.string.hum));
        textPcpnTag.setText(getResources().getString(R.string.pcpn));
        textVisTag.setText(getResources().getString(R.string.vis));
        textLifestyleTag.setText(getResources().getString(R.string.lifestyle));
        textComfTag.setText(getResources().getString(R.string.comf));
        textCwTag.setText(getResources().getString(R.string.cw));
        textDrsgTag.setText(getResources().getString(R.string.drsg));
        textFluTag.setText(getResources().getString(R.string.flu));
        textSportTag.setText(getResources().getString(R.string.sport));
        textTravTag.setText(getResources().getString(R.string.trav));
        textUvTag.setText(getResources().getString(R.string.uv));
        textAirTag.setText(getResources().getString(R.string.air));

        btnChoosecity.setOnClickListener(v -> {
            startActivity(new Intent(context, AddCityActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.animo_no);
        });

        btnSetting.setOnClickListener(v -> {
            startActivity(new Intent(context, SettingActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.animo_no);
        });


        if (getIntent() != null) {
            Intent intent = getIntent();
            HotCity.HeWeather6Bean.BasicBean basicBean = (HotCity.HeWeather6Bean.BasicBean)
                    intent.getSerializableExtra("city");
            if (basicBean != null) {
                cid = basicBean.getCid();
            }
        }
        getWeatherInfo();
        getLifeStyleInfo();
        textTolastday = findViewById(R.id.text_tolastday);
        textNowDay = findViewById(R.id.text_now_day);
        textTonextday = findViewById(R.id.text_tonextday);
        textTolastday.setVisibility(View.INVISIBLE);

        textTonextday.setOnClickListener(v -> initWeather(now_mode + 1, sparseArray_future));


        textTolastday.setOnClickListener(v ->
        {
            if (now_mode == 1) {
                initWeather(0, sparseArray_today);
            } else {
                initWeather(now_mode - 1, sparseArray_future);
            }
        });
    }

    /**
     * 转换时间字符串格式
     *
     * @param datestr 服务器传来的时间字符串
     * @return 转换后的时间字符串
     */
    private String formatDateStr(String datestr) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        if (datestr != null) {
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(datestr);
            } catch (ParseException e) {
                Log.e("时间转换", "出错", e);
            }
        }
        return simpleDateFormat.format(date);
    }

    /**
     * 初始化天气数据
     *
     * @param s 服务器传来的天气数据
     */
    private void initWeather(int mode, SparseArray s) {
        now_mode = mode;
        switch (mode) {
            case TODAY_MODE:
                //显示今日天气
                textTolastday.setVisibility(View.INVISIBLE);
                textTonextday.setVisibility(View.VISIBLE);
                textNowDay.setText(formatDateStr(null));
                Date newDate2 = new Date(new Date().getTime() + (long) 24 * 60 * 60 * 1000);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
                String dateOk = simpleDateFormat.format(newDate2);
                textTonextday.setText(dateOk);
                Weather weather = JSON.parseObject(s.get(1).toString(), Weather.class);
                if (weather != null) {
                    List<Weather.HeWeather6Bean> list_heWeather6Bean = weather.getHeWeather6();
                    if (list_heWeather6Bean.get(0).getStatus().equals(ServiceInfo.Status.OK)) {
                        Weather.HeWeather6Bean.NowBean nowBean = list_heWeather6Bean.get(0).getNow();
                        textCityname.setText(list_heWeather6Bean.get(0).getBasic().getLocation());
                        textTmpWeather.setText(nowBean.getTmp() + AppData.getUnitStr().get(AppData.TMP_UNIT));//当前气温
                        textCitytmp.setText(nowBean.getTmp() + AppData.getUnitStr().get(AppData.TMP_UNIT));//当前气温
                        constraintFl.setVisibility(View.VISIBLE);//体感温度隐藏，未来体感温度无法预测
                        textFl.setText(nowBean.getFl() + AppData.getUnitStr().get(AppData.TMP_UNIT));//体感温度
                        textCondtxtWeather.setText(nowBean.getCond_txt());
                        textWinddir.setText(nowBean.getWind_dir());
                        textWindsc.setText(nowBean.getWind_sc());
                        textHum.setText(nowBean.getHum());
                        textPcpn.setText(nowBean.getPcpn());
                        textVis.setText(nowBean.getVis() + AppData.getUnitStr().get(AppData.VISIBILITY));//能见度
                        initIcon(nowBean.getCond_code());
                    } else {
                        Log.e(TAG, ServiceInfo.Status.getMessage(list_heWeather6Bean.get(0).getStatus()));
                    }
                }
                break;
            case ONEDAY_LATER_MODE:
            case TOWDAY_LATER_MODE:
                //显示未来天气
                textTolastday.setVisibility(View.VISIBLE);
                if (mode == TOWDAY_LATER_MODE) {
                    textTonextday.setVisibility(View.INVISIBLE);
                } else {
                    textTonextday.setVisibility(View.VISIBLE);
                }
                FutureWeather futureWeather = JSON.parseObject(s.get(1).toString(), FutureWeather.class);
                if (futureWeather != null) {
                    List<FutureWeather.HeWeather6Bean> list_fuheWeather6Bean = futureWeather.getHeWeather6();
                    if (list_fuheWeather6Bean.get(0).getStatus().equals(ServiceInfo.Status.OK)) {
                        List<FutureWeather.HeWeather6Bean.DailyForecastBean> dailyForecastBeanList = list_fuheWeather6Bean.get(0).getDaily_forecast();
                        textCityname.setText(list_fuheWeather6Bean.get(0).getBasic().getLocation());
                        textTmpWeather.setText(dailyForecastBeanList.get(mode).getTmp_min() + AppData.getUnitStr().get(AppData.TMP_UNIT)
                                + "~" + dailyForecastBeanList.get(mode).getTmp_max() + AppData.getUnitStr().get(AppData.TMP_UNIT));//气温
                        textCitytmp.setText(dailyForecastBeanList.get(mode).getTmp_min() + AppData.getUnitStr().get(AppData.TMP_UNIT)
                                + "~" + dailyForecastBeanList.get(mode).getTmp_max() + AppData.getUnitStr().get(AppData.TMP_UNIT));//气温
                        constraintFl.setVisibility(View.GONE);//体感温度隐藏，未来体感温度无法预测
                        textCondtxtWeather.setText(dailyForecastBeanList.get(mode).getCond_txt_d());
                        textWinddir.setText(dailyForecastBeanList.get(mode).getWind_dir());
                        textWindsc.setText(dailyForecastBeanList.get(mode).getWind_sc());
                        textHum.setText(dailyForecastBeanList.get(mode).getHum());
                        textPcpn.setText(dailyForecastBeanList.get(mode).getPcpn());
                        textVis.setText(dailyForecastBeanList.get(mode).getVis() + AppData.getUnitStr().get(AppData.VISIBILITY));//能见度
                        textNowDay.setText(formatDateStr(dailyForecastBeanList.get(mode).getDate()));
                        Date newDate3 = null;
                        Date newDate4 = null;
                        try {
                            newDate3 = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dailyForecastBeanList.get(mode).getDate()).getTime() + (long) 24 * 60 * 60 * 1000);
                            newDate4 = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dailyForecastBeanList.get(mode).getDate()).getTime() - (long) 24 * 60 * 60 * 1000);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                        String nextDay = sdf.format(newDate3);
                        String lastDay = sdf.format(newDate4);
                        textTonextday.setText(nextDay);
                        textTolastday.setText(lastDay);
                        initIcon(dailyForecastBeanList.get(mode).getCond_code_d());
                    } else {
                        Log.e(TAG, ServiceInfo.Status.getMessage(list_fuheWeather6Bean.get(mode).getStatus()));
                    }
                }
                break;
        }

    }

    private void initIcon(String code) {
        String code_str;
        switch (code) {
            case "200":
            case "201":
                code_str = "200";
                break;
            case "202":
            case "203":
            case "204":
                code_str = "202";
                break;
            case "205":
            case "206":
            case "207":
            case "208":
            case "209":
            case "210":
                code_str = "205";
                break;
            case "211":
            case "212":
            case "213":
                code_str = "211";
                break;
            case "300":
            case "305":
            case "309":
            case "314":
                code_str = "305";
                break;
            case "301":
            case "307":
            case "315":
            case "399":
                code_str = "307";
                break;
            case "308":
            case "310":
            case "311":
            case "312":
            case "316":
            case "317":
            case "318":
                code_str = "310";
                break;
            case "400":
            case "404":
            case "406":
            case "407":
            case "408":
                code_str = "400";
                break;
            case "401":
            case "409":
                code_str = "401";
                break;
            case "402":
            case "410":
                code_str = "402";
                break;
            case "405":
            case "499":
                code_str = "499";
                break;
            case "509":
            case "514":
                code_str = "509";
                break;
            case "510":
            case "515":
                code_str = "510";
                break;
            default:
                code_str = code;
                break;
        }
        switch (code_str) {
            case "901":
                constraintTmpWeather.setBackgroundColor(
                        getResources().getColor(R.color.blue));
                break;
            case "900":
                constraintTmpWeather.setBackgroundColor(
                        getResources().getColor(R.color.orange));
                break;
            case "999":
                constraintTmpWeather.setBackgroundColor(
                        getResources().getColor(R.color.black));
                break;
            default:
                constraintTmpWeather.setBackground(
                        getResources().getDrawable(
                                ResHelper.getResId(ServiceInfo.BACK_TAG + code_str, R.drawable.class)));
                break;
        }


        textTitleimg.setBackground(
                DrawableTintUtil.tintDrawable(
                        getResources().getDrawable(
                                ResHelper.getResId(ServiceInfo.IMG_TAG + code, R.drawable.class))
                        , getResources().getColor(R.color.white)));

        textCondimg.setBackground(
                DrawableTintUtil.tintDrawable(
                        getResources().getDrawable(
                                ResHelper.getResId(ServiceInfo.IMG_TAG + code, R.drawable.class))
                        , getResources().getColor(R.color.white)));
    }

    private void getWeatherInfo() {
        //获取今日天气
        Observable.create((ObservableOnSubscribe<SparseArray>) emitter -> {
            Map<String, String> map = new HashMap<>();
            if (cid != null && !"".equals(cid)) {
                map.put(ServiceInfo.NowWeather.LOCATION, cid);
            }
            HttpUtils.sendOKHttpRequest(ServiceInfo.NowWeather.getUrl(map), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    emitter.onError(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    SparseArray<String> sparseArray = new SparseArray();
                    sparseArray.put(0, Integer.toString(response.code()));
                    sparseArray.put(1, response.body() != null ? response.body().string() : "");
                    emitter.onNext(sparseArray);
                }
            });
        }).subscribe(new Observer<SparseArray>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SparseArray s) {
                switch ((String) s.get(0)) {
                    case "200":
                        sparseArray_today = s;
                        runOnUiThread(() -> initWeather(TODAY_MODE, sparseArray_today));
                        break;
                    default:
                        ToastUtils.show(context, "连接服务器失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        //获取未来3日天气
        Observable.create((ObservableOnSubscribe<SparseArray>) emitter -> {
            Map<String, String> map = new HashMap<>();
            if (cid != null && !"".equals(cid)) {
                map.put(ServiceInfo.FutureWeather.LOCATION, cid);
            }
            HttpUtils.sendOKHttpRequest(ServiceInfo.FutureWeather.getUrl(map), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    emitter.onError(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    SparseArray<String> sparseArray = new SparseArray();
                    sparseArray.put(0, Integer.toString(response.code()));
                    sparseArray.put(1, response.body() != null ? response.body().string() : "");
                    emitter.onNext(sparseArray);
                }
            });
        }).subscribe(new Observer<SparseArray>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SparseArray s) {
                switch ((String) s.get(0)) {
                    case "200":
                        sparseArray_future = s;
                        break;
                    default:
                        ToastUtils.show(context, "连接服务器失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void getLifeStyleInfo() {
        Observable.create((ObservableOnSubscribe<SparseArray>) emitter -> {
            Map<String, String> map = new HashMap<>();
            if (cid != null && !"".equals(cid)) {
                map.put(ServiceInfo.LifeStyle.LOCATION, cid);
            }
            HttpUtils.sendOKHttpRequest(ServiceInfo.LifeStyle.getUrl(map), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    emitter.onError(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    SparseArray<String> sparseArray = new SparseArray();
                    sparseArray.put(0, Integer.toString(response.code()));
                    sparseArray.put(1, response.body() != null ? response.body().string() : "");
                    emitter.onNext(sparseArray);
                }
            });
        }).subscribe(new Observer<SparseArray>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SparseArray s) {
                switch ((String) s.get(0)) {
                    case "200":
                        LifeStyle lifeStyle = JSON.parseObject(s.get(1).toString(), LifeStyle.class);
                        if (lifeStyle != null) {
                            List<LifeStyle.HeWeather6Bean> list_heWeather6Bean = lifeStyle.getHeWeather6();
                            if (list_heWeather6Bean.get(0).getStatus().equals(ServiceInfo.Status.OK)) {
                                List<LifeStyle.HeWeather6Bean.LifestyleBean> lifestyleBeanList = list_heWeather6Bean.get(0).getLifestyle();
                                runOnUiThread(() -> {
                                    for (LifeStyle.HeWeather6Bean.LifestyleBean item : lifestyleBeanList) {
                                        switch (item.getType()) {
                                            case "comf":
                                                textComfTag.setText(new StringBuilder().append(
                                                        getResources().getString(R.string.comf)).append(" ").append(item.getBrf()).toString());
                                                textComf.setText(item.getTxt());
                                                break;
                                            case "drsg":
                                                textDrsgTag.setText(new StringBuilder().append(
                                                        getResources().getString(R.string.drsg)).append(" ").append(item.getBrf()).toString());
                                                textDrsg.setText(item.getTxt());
                                                break;
                                            case "cw":
                                                textCwTag.setText(new StringBuilder().append(
                                                        getResources().getString(R.string.cw)).append(" ").append(item.getBrf()).toString());
                                                textCw.setText(item.getTxt());
                                                break;
                                            case "flu":
                                                textFluTag.setText(new StringBuilder().append(
                                                        getResources().getString(R.string.flu)).append(" ").append(item.getBrf()).toString());
                                                textFlu.setText(item.getTxt());
                                                break;
                                            case "sport":
                                                textSportTag.setText(new StringBuilder().append(
                                                        getResources().getString(R.string.sport)).append(" ").append(item.getBrf()).toString());
                                                textSport.setText(item.getTxt());
                                                break;
                                            case "trav":
                                                textTravTag.setText(new StringBuilder().append(
                                                        getResources().getString(R.string.trav)).append(" ").append(item.getBrf()).toString());
                                                textTrav.setText(item.getTxt());
                                                break;
                                            case "uv":
                                                textUvTag.setText(new StringBuilder().append(
                                                        getResources().getString(R.string.uv)).append(" ").append(item.getBrf()).toString());
                                                textUv.setText(item.getTxt());
                                                break;
                                            case "air":
                                                textAirTag.setText(new StringBuilder().append(
                                                        getResources().getString(R.string.air)).append(" ").append(item.getBrf()).toString());
                                                textAir.setText(item.getTxt());
                                                break;
                                        }
                                    }
                                });
                            } else {
                                Log.e(TAG, ServiceInfo.Status.getMessage(list_heWeather6Bean.get(0).getStatus()));
                            }
                        }
                        break;
                    default:
                        ToastUtils.show(context, "连接服务器失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
