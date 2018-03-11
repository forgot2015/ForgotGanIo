package com.linzongfu.forgotgankio;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by forgot on 2018/3/11.
 * Email: forgot2015@gmail.com
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
        instance = this;
        Utils.init(this);
    }

    public static MyApplication getInstance() {
        return instance;
    }

}
