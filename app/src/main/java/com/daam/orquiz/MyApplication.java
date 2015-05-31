package com.daam.orquiz;

import android.app.Application;
import android.content.Context;

/**
 * Created by johnny on 30-05-2015.
 */
public class MyApplication extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

}
