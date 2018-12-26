package com.dubhe.broken.dubheweather.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.dubhe.broken.dubheweather.R;
import com.dubhe.broken.dubheweather.constant.ObjectInfo;

import java.net.URI;

/**
 * 作者：DubheBroken
 * 时间：2018/12/26 13:02:50
 * 邮箱：z1574507001@gmail.com
 * 说明：
 */
public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout constraintAboutToolbar;
    private TextView btnBackAbout;
    private TextView textAbout;
    private CardView cardAbout1;
    private ConstraintLayout constraintAboutDeveloper;
    private TextView textAboutDeveloperIcon;
    private TextView textAboutDeveloperTag;
    private TextView textAboutDeveloper;
    private TextView textAboutDeveloperArrow;
    private View line1s;
    private ConstraintLayout constraintAboutNowversion;
    private TextView textAboutNowversionIcon;
    private TextView textAboutNowversionTag;
    private TextView textAboutNowversion;
    private TextView textAboutNowversionArrow;
    private View line2s;
    private ConstraintLayout constraintAboutBugReport;
    private TextView textAboutBugReportIcon;
    private TextView textAboutBugReportTag;
    private TextView textAboutBugReport;
    private TextView textAboutBugReportArrow;
    private CardView cardAbout2;
    private ConstraintLayout constraintAboutDataSupport;
    private TextView textAboutDataSupportIcon;
    private TextView textAboutDataSupportTag;
    private TextView textAboutDataSupport;
    private TextView textAboutDataSupportArrow;
    private View lines1;
    private ConstraintLayout constraintAboutImageSupport;
    private TextView textAboutImageSupportIcon;
    private TextView textAboutImageSupportTag;
    private TextView textAboutImageSupport;
    private TextView textAboutImageSupportArrow;
    private View lines2;
    private ConstraintLayout constraintAboutObjectAdd;
    private TextView textAboutObjectAddIcon;
    private TextView textAboutObjectAddTag;
    private TextView textAboutObjectAdd;
    private TextView textAboutObjectAddArrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        constraintAboutToolbar = findViewById(R.id.constraint_about_toolbar);
        btnBackAbout = findViewById(R.id.btn_back_about);
        textAbout = findViewById(R.id.text_about);
        cardAbout1 = findViewById(R.id.card_about1);
        constraintAboutDeveloper = findViewById(R.id.constraint_about_developer);
        textAboutDeveloperIcon = findViewById(R.id.text_about_developer_icon);
        textAboutDeveloperTag = findViewById(R.id.text_about_developer_tag);
        textAboutDeveloper = findViewById(R.id.text_about_developer);
        textAboutDeveloperArrow = findViewById(R.id.text_about_developer_arrow);
        line1s = findViewById(R.id.line1s);
        constraintAboutNowversion = findViewById(R.id.constraint_about_nowversion);
        textAboutNowversionIcon = findViewById(R.id.text_about_nowversion_icon);
        textAboutNowversionTag = findViewById(R.id.text_about_nowversion_tag);
        textAboutNowversion = findViewById(R.id.text_about_nowversion);
        textAboutNowversionArrow = findViewById(R.id.text_about_nowversion_arrow);
        line2s = findViewById(R.id.line2s);
        constraintAboutBugReport = findViewById(R.id.constraint_about_bug_report);
        textAboutBugReportIcon = findViewById(R.id.text_about_bug_report_icon);
        textAboutBugReportTag = findViewById(R.id.text_about_bug_report_tag);
        textAboutBugReport = findViewById(R.id.text_about_bug_report);
        textAboutBugReportArrow = findViewById(R.id.text_about_bug_report_arrow);
        cardAbout2 = findViewById(R.id.card_about2);
        constraintAboutDataSupport = findViewById(R.id.constraint_about_data_support);
        textAboutDataSupportIcon = findViewById(R.id.text_about_data_support_icon);
        textAboutDataSupportTag = findViewById(R.id.text_about_data_support_tag);
        textAboutDataSupport = findViewById(R.id.text_about_data_support);
        textAboutDataSupportArrow = findViewById(R.id.text_about_data_support_arrow);
        lines1 = findViewById(R.id.lines1);
        constraintAboutImageSupport = findViewById(R.id.constraint_about_image_support);
        textAboutImageSupportIcon = findViewById(R.id.text_about_image_support_icon);
        textAboutImageSupportTag = findViewById(R.id.text_about_image_support_tag);
        textAboutImageSupport = findViewById(R.id.text_about_image_support);
        textAboutImageSupportArrow = findViewById(R.id.text_about_image_support_arrow);
        lines2 = findViewById(R.id.lines2);
        constraintAboutObjectAdd = findViewById(R.id.constraint_about_object_add);
        textAboutObjectAddIcon = findViewById(R.id.text_about_object_add_icon);
        textAboutObjectAddTag = findViewById(R.id.text_about_object_add_tag);
        textAboutObjectAdd = findViewById(R.id.text_about_object_add);
        textAboutObjectAddArrow = findViewById(R.id.text_about_object_add_arrow);

        try {
            textAboutNowversion.setText(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        btnBackAbout.setOnClickListener(v -> finish());

        constraintAboutBugReport.setOnClickListener(this);
        constraintAboutDataSupport.setOnClickListener(this);
        constraintAboutImageSupport.setOnClickListener(this);
        constraintAboutObjectAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        switch (v.getId()) {
            case R.id.constraint_about_bug_report:
                intent.setData(Uri.parse(ObjectInfo.BUG_REPORT_URL));
                break;
            case R.id.constraint_about_data_support:
                intent.setData(Uri.parse(ObjectInfo.HEWEATHER_URL));
                break;
            case R.id.constraint_about_image_support:
                intent.setData(Uri.parse(ObjectInfo.GOOGLE_IMAGE_URL));
                break;
            case R.id.constraint_about_object_add:
                intent.setData(Uri.parse(ObjectInfo.OBJ_URL));
                break;
            default:
                return;
        }
        startActivity(intent);
    }
}
