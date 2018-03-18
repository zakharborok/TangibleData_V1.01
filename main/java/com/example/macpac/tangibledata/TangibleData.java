package com.example.macpac.tangibledata;

import android.app.Application;
import android.content.Context;

/**
 * \class TangibleData
 * Used to provide context for Speech class.
 */
public class TangibleData extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    /**
     * Getter method for context.
     * @return context.
     */
    public static Context getContext() {
        return mContext;
    }
}