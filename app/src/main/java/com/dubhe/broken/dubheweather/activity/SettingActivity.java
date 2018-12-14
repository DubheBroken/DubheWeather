package com.dubhe.broken.dubheweather.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dubhe.broken.dubheweather.R;
import com.dubhe.broken.dubheweather.application.AppData;
import com.dubhe.broken.dubheweather.constant.ServiceInfo;

import java.util.Locale;

/**
 * 作者：DubheBroken
 * 时间：2018/12/7 21:46:11
 * 邮箱：z1574507001@gmail.com
 * 说明：
 */
public class SettingActivity extends AppCompatActivity {

    private static final int MODE_GROUP = 0;
    private static final int MODE_LANG = 1;
    private static final int MODE_UNIT = 2;
    private Context context = this;
    private TextView btnBackSetting;
    private TextView textSetting;
    private ConstraintLayout constraintSettingToolbar;
    private TextView textSettingGroupIcon;
    private TextView textSettingGroupTag;
    private TextView textSettingGroup;
    private TextView textSettingGroupArrow;
    private ConstraintLayout constraintSettingGroup;
    private View line1s;
    private TextView textSettingLangIcon;
    private TextView textSettingLangTag;
    private TextView textSettingLang;
    private TextView textSettingLangArrow;
    private ConstraintLayout constraintSettingLang;
    private View line2s;
    private TextView textSettingUnitIcon;
    private TextView textSettingUnitTag;
    private TextView textSettingUnit;
    private TextView textSettingUnitArrow;
    private ConstraintLayout constraintSettingUnit;
    private ConstraintLayout constraintSetting;
    private AlertDialog dialog;
    private AlertDialog dialogOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        btnBackSetting = findViewById(R.id.btn_back_setting);
        textSetting = findViewById(R.id.text_setting);
        constraintSettingToolbar = findViewById(R.id.constraint_setting_toolbar);
        textSettingGroupIcon = findViewById(R.id.text_setting_group_icon);
        textSettingGroupTag = findViewById(R.id.text_setting_group_tag);
        textSettingGroup = findViewById(R.id.text_setting_group);
        textSettingGroupArrow = findViewById(R.id.text_setting_group_arrow);
        constraintSettingGroup = findViewById(R.id.constraint_setting_group);
        line1s = findViewById(R.id.line1s);
        textSettingLangIcon = findViewById(R.id.text_setting_lang_icon);
        textSettingLangTag = findViewById(R.id.text_setting_lang_tag);
        textSettingLang = findViewById(R.id.text_setting_lang);
        textSettingLangArrow = findViewById(R.id.text_setting_lang_arrow);
        constraintSettingLang = findViewById(R.id.constraint_setting_lang);
        line2s = findViewById(R.id.line2s);
        textSettingUnitIcon = findViewById(R.id.text_setting_unit_icon);
        textSettingUnitTag = findViewById(R.id.text_setting_unit_tag);
        textSettingUnit = findViewById(R.id.text_setting_unit);
        textSettingUnitArrow = findViewById(R.id.text_setting_unit_arrow);
        constraintSettingUnit = findViewById(R.id.constraint_setting_unit);
        constraintSetting = findViewById(R.id.constraint_setting);

        textSettingGroup.setText(AppData.getSettingStr(context, AppData.getGroup()));
        textSettingLang.setText(AppData.getSettingStr(context, AppData.getLang()));
        textSettingUnit.setText(AppData.getSettingStr(context, AppData.getUnit()));

        btnBackSetting.setOnClickListener(v -> finish());
        constraintSettingGroup.setOnClickListener(v -> showSelectDialog(MODE_GROUP));
        constraintSettingUnit.setOnClickListener(v -> showSelectDialog(MODE_UNIT));
        constraintSettingLang.setOnClickListener(v -> showSelectDialog(MODE_LANG));

    }

    /**
     * 弹出选择对话框
     */
    private void showSelectDialog(int mode) {
        String[] items = null;//数据源
        String title = null;
        TypedArray ar = null;
        switch (mode) {
            case MODE_GROUP:
                ar = context.getResources().obtainTypedArray(R.array.group);
                items = getResources().getStringArray(R.array.group);
                title = getResources().getString(R.string.select_group);
                break;
            case MODE_LANG:
                ar = context.getResources().obtainTypedArray(R.array.lang);
                items = getResources().getStringArray(R.array.lang);
                title = getResources().getString(R.string.select_lang);
                break;
            case MODE_UNIT:
                ar = context.getResources().obtainTypedArray(R.array.unit);
                items = getResources().getStringArray(R.array.unit);
                title = getResources().getString(R.string.select_unit);
                break;
        }
        String[] finalItems = items;
        TypedArray finalAr = ar;
        dialog = new AlertDialog.Builder(this)
                .setTitle(title)//标题
                .setItems(items, (dialog1, which) -> {
                            switch (mode) {
                                case MODE_GROUP:
                                    textSettingGroup.setText(finalItems[which]);
                                    AppData.setGroup(AppData.getSettingCode(context, finalAr.getResourceId(which, -1)));
                                    break;
                                case MODE_LANG:
                                    textSettingLang.setText(finalItems[which]);
                                    AppData.setLang(AppData.getSettingCode(context, finalAr.getResourceId(which, -1)));
                                    Resources resources = context.getResources();
                                    DisplayMetrics dm = resources.getDisplayMetrics();
                                    Configuration config = resources.getConfiguration();
                                    switch (finalItems[which]) {
                                        case ServiceInfo.Language.CN:
                                            config.locale = Locale.SIMPLIFIED_CHINESE;
                                            break;
                                        case ServiceInfo.Language.HK:
                                            config.locale = Locale.TRADITIONAL_CHINESE;
                                            break;
                                        case ServiceInfo.Language.EN:
                                            config.locale = Locale.ENGLISH;
                                            break;
                                        case ServiceInfo.Language.DE:
                                            config.locale = Locale.GERMAN;
                                            break;
                                        case ServiceInfo.Language.FR:
                                            config.locale = Locale.FRENCH;
                                            break;
                                        case ServiceInfo.Language.IT:
                                            config.locale = Locale.ITALIAN;
                                            break;
                                        case ServiceInfo.Language.JP:
                                            config.locale = Locale.JAPANESE;
                                            break;
                                        case ServiceInfo.Language.KR:
                                            config.locale = Locale.KOREAN;
                                            break;
                                    }
                                    resources.updateConfiguration(config, dm);
                                    initView();
                                    break;
                                case MODE_UNIT:
                                    textSettingUnit.setText(finalItems[which]);
                                    AppData.setUnit(AppData.getSettingCode(context, finalAr.getResourceId(which, -1)));
                                    break;
                            }
                            dialog.cancel();
                        }
                )
                .create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        AppData.saveData();
        super.onDestroy();
    }
}
