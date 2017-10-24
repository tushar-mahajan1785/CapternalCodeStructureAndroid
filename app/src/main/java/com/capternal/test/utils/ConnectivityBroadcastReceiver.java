package com.capternal.test.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.capternal.test.base.AppController;


public class ConnectivityBroadcastReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener objConnectivity;

    public ConnectivityBroadcastReceiver() {
        super();
    }

    public static boolean isConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) AppController.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            System.out.println("IN CONNECTIVITY BROADCAST");
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            objConnectivity.onNetworkConnectionChanged(isConnected);
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}