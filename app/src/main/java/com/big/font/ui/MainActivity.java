package com.big.font.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.provider.Settings.System;
import android.widget.Button;

import com.big.font.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
Button btnAddSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.activity_main);
        float scale = getResources().getConfiguration().fontScale;
        init();
//        adjustFontScale1(getResources().getConfiguration());
//        Settings.System.putFloat(getBaseContext().getContentResolver(),
//                Settings.System.FONT_SCALE, (float) 1.0);
    }

    public void init() {
        btnAddSize = findViewById(R.id.btn_add_font);
        btnAddSize.setOnClickListener(this);
    }

    public void adjustFontScale(Configuration configuration)
    {
        configuration.fontScale = (float) 1.0;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }

    public void adjustFontScale1(Configuration configuration) {
        Log.d("1","adjustFontScale1" + configuration.fontScale);
//        if (configuration.fontScale > 1.30) {
            configuration.fontScale = (float) 1.30;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            getBaseContext().getResources().updateConfiguration(configuration, metrics);
//        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_font:
                Intent activity = new Intent(MainActivity.this, AddSizeActivity.class);
                startActivity(activity);
                break;
        }
    }
//    public static void getConfigurationForUser(ContentResolver cr,
//                                               Configuration outConfig, int userHandle) {
//        outConfig.fontScale = Settings.System.getFloatForUser(
//                cr, System.FONT_SCALE, outConfig.fontScale, userHandle);
//        if (outConfig.fontScale < 0) {
//            outConfig.fontScale = 1;
//        }
//    }

}
