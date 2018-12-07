package com.dubhe.broken.dubheweather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dubhe.broken.dubheweather.adapter.CityListAdapter;
import com.dubhe.broken.dubheweather.constant.ServiceInfo;
import com.dubhe.broken.dubheweather.entity.HotCity;
import com.dubhe.broken.dubheweather.utils.HttpUtils;
import com.dubhe.broken.dubheweather.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
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
 * 时间：2018/12/6 20:10:10
 * 邮箱：z1574507001@gmail.com
 * 说明：
 */
public class AddCityActivity extends AppCompatActivity {

    private static final String TAG = "com.dubhe.dubheweather.AddCityActivity";
    private Context context = this;
    private TextView btnBackAddcity;
    private TextView textAddcity;
    private AutoCompleteTextView actvAddcity;
    private ListView listViewHotCitys;
    private List<HotCity.HeWeather6Bean.BasicBean> list_hotcitys;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcity_layout);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        btnBackAddcity = findViewById(R.id.btn_back_addcity);
        textAddcity = findViewById(R.id.text_addcity);
        actvAddcity = findViewById(R.id.actv_addcity);
        listViewHotCitys = findViewById(R.id.list_hot_citys);

        btnBackAddcity.setOnClickListener(v -> finish());

        actvAddcity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = String.valueOf(s);
                //监听输入框，已输入就去查询
                if (input.length() > 1) {
                    getMatchCity(input);
                }
            }
        });
        getHotCitys();
    }

    private void getMatchCity(String input) {
        Observable.create((ObservableOnSubscribe<SparseArray>) emitter -> {
            Map<String, String> map = new HashMap<>();
            map.put(ServiceInfo.SERACH_CITY.LOCATION,input);
            HttpUtils.sendOKHttpRequest(ServiceInfo.SERACH_CITY.getURL(map), new Callback() {
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
                        HotCity hotCity = JSON.parseObject(s.get(1).toString(), HotCity.class);
                        if (hotCity != null) {
                            List<HotCity.HeWeather6Bean> list_heWeather6Bean = hotCity.getHeWeather6();
                            if (list_heWeather6Bean.get(0).getStatus().equals(ServiceInfo.Status.OK)) {
                                list_hotcitys = list_heWeather6Bean.get(0).getBasic();
                                List<String> stringArray = new ArrayList<>();
                                for (HotCity.HeWeather6Bean.BasicBean hotcity : list_hotcitys) {
                                    stringArray.add(hotcity.getLocation());
                                }
                                runOnUiThread(() -> {
                                    ArrayAdapter<String> adapter= new ArrayAdapter<>(context, R.layout.city_item_layout, stringArray);
                                    actvAddcity.setAdapter(adapter);
                                    actvAddcity.showDropDown();
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
                ToastUtils.show(context, "连接服务器失败");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void getHotCitys() {
        Observable.create((ObservableOnSubscribe<SparseArray>) emitter -> {
            Map<String, String> map = new HashMap<>();
            HttpUtils.sendOKHttpRequest(ServiceInfo.HOT_CITY.getUri(map), new Callback() {
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
                        HotCity hotCity = JSON.parseObject(s.get(1).toString(), HotCity.class);
                        if (hotCity != null) {
                            List<HotCity.HeWeather6Bean> list_heWeather6Bean = hotCity.getHeWeather6();
                            if (list_heWeather6Bean.get(0).getStatus().equals(ServiceInfo.Status.OK)) {
                                list_hotcitys = list_heWeather6Bean.get(0).getBasic();
                                List<String> stringArray = new ArrayList<>();
                                for (HotCity.HeWeather6Bean.BasicBean hotcity : list_hotcitys) {
                                    stringArray.add(hotcity.getLocation());
                                }
                                runOnUiThread(() -> {
                                    ArrayAdapter<String> hotCitysAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, stringArray);
                                    listViewHotCitys.setAdapter(hotCitysAdapter);
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
                ToastUtils.show(context, "连接服务器失败");
            }

            @Override
            public void onComplete() {

            }
        });
    }


}
