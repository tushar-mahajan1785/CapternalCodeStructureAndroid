package com.capternal.test.base;

import android.app.Application;

import com.capternal.test.utils.ConnectivityBroadcastReceiver;


/**
 * Created by student on 14/06/17.
 */

public class AppController extends Application {
    private static AppController mInstance;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityBroadcastReceiver.ConnectivityReceiverListener listener) {
        ConnectivityBroadcastReceiver.objConnectivity = listener;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Create a object of database and put it into hashmap and access it from any
//        with AppController instance.
        mInstance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
