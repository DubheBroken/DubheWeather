package com.dubhe.broken.dubheweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dubhe.broken.dubheweather.R;
import com.dubhe.broken.dubheweather.application.AppData;
import com.dubhe.broken.dubheweather.constant.ServiceInfo;
import com.dubhe.broken.dubheweather.entity.HotCity;
import com.dubhe.broken.dubheweather.entity.Weather;
import com.dubhe.broken.dubheweather.utils.DrawableTintUtil;
import com.dubhe.broken.dubheweather.utils.HttpUtils;
import com.dubhe.broken.dubheweather.utils.ResHelper;
import com.dubhe.broken.dubheweather.utils.ToastUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
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
        line2 = findViewById(R.id.line2);
        textHumTag = findViewById(R.id.text_hum_tag);
        textHum = findViewById(R.id.text_hum);
        line3 = findViewById(R.id.line3);
        textPcpnTag = findViewById(R.id.text_pcpn_tag);
        textPcpn = findViewById(R.id.text_pcpn);
        line4 = findViewById(R.id.line4);
        textVisTag = findViewById(R.id.text_vis_tag);
        textVis = findViewById(R.id.text_vis);
        constraintMoreinfoWeather = findViewById(R.id.constraint_moreinfo_weather);
        constraintTmpWeather = findViewById(R.id.constraint_tmp_weather);
        constraintWeather = findViewById(R.id.constraint_weather);
        textTitleimg = findViewById(R.id.text_titleimg);
        textCondimg = findViewById(R.id.text_condimg);
        textCitytmp = findViewById(R.id.text_citytmp);

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

    }

    private void getWeatherInfo() {
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
                        Weather weather = JSON.parseObject(s.get(1).toString(), Weather.class);
                        if (weather != null) {
                            List<Weather.HeWeather6Bean> list_heWeather6Bean = weather.getHeWeather6();
                            if (list_heWeather6Bean.get(0).getStatus().equals(ServiceInfo.Status.OK)) {
                                Weather.HeWeather6Bean.NowBean nowBean = list_heWeather6Bean.get(0).getNow();
                                runOnUiThread(() -> {
                                    textCityname.setText(list_heWeather6Bean.get(0).getBasic().getLocation());
                                    textTmpWeather.setText(nowBean.getTmp() + AppData.getUnitStr().get(AppData.TMP_UNIT));//当前气温
                                    textCitytmp.setText(nowBean.getTmp() + AppData.getUnitStr().get(AppData.TMP_UNIT));//当前气温
                                    textFl.setText(nowBean.getFl() + AppData.getUnitStr().get(AppData.TMP_UNIT));//体感温度
                                    textCondtxtWeather.setText(nowBean.getCond_txt());
                                    textWinddir.setText(nowBean.getWind_dir());
                                    textWindsc.setText(nowBean.getWind_sc());
                                    textHum.setText(nowBean.getHum());
                                    textPcpn.setText(nowBean.getPcpn());
                                    textVis.setText(nowBean.getVis() + AppData.getUnitStr().get(AppData.VISIBILITY));//能见度

                                    textTitleimg.setBackground(
                                            DrawableTintUtil.tintDrawable(
                                                    getResources().getDrawable(
                                                            ResHelper.getResId(ServiceInfo.IMG_TAG + nowBean.getCond_code(), R.drawable.class))
                                                    , getResources().getColor(R.color.white)));

                                    textCondimg.setBackground(
                                            DrawableTintUtil.tintDrawable(
                                                    getResources().getDrawable(
                                                            ResHelper.getResId(ServiceInfo.IMG_TAG + nowBean.getCond_code(), R.drawable.class))
                                                    , getResources().getColor(R.color.white)));
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
