package com.example.macpac.tangibledata;

import android.app.Application;
import android.content.Context;

/**
 * Created by cabeywickra on 05/02/2018.
 */

public class TangibleData extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}