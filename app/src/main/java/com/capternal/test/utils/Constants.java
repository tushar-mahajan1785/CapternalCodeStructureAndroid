package com.capternal.test.utils;

import android.net.Uri;
import android.os.Environment;

import com.capternal.test.R;
import com.capternal.test.base.AppController;

/**
 * Created by jupitor on 07/06/17.
 */

public class Constants {

    /**
     * API BASE
     */
    public static final String BASE = "https://api.myjson.com/";
    public static final String API_BASE = BASE + "bins/";
    /**
     * API CONSTANTS
     */
    public static final String API_RESULT = "result";
    public static final String API_RESPONSE = "response";
    public static final String API_MESSAGE = "message";
    public static final String KEY_AUTH_TOKEN = "authtoken";
    public static final String API_UNAUTHORIZED = "Unauthorized.";

    /**
     * ACTUAL APIS
     */
    public static final String API_DEMO = API_BASE + "demoListApi";
    //    public static final String API_DEMO_LIST = "https://api.myjson.com/bins/9rnrz";
    public static final String API_DEMO_LIST = API_BASE + "10sqn3";
    /**
     * APP SHARED PREFERENCES
     */
    public static final String APP_SHARED_PREFERENCE = "shared_preference";
    /**
     * APP CONSTANTS
     */
    public static final String SESSION_EXPIRE_MESSAGE = "Sorry..! Your account has been de-activated. Please contact Konecrane Admin";
    public static Uri uri;
    public static final int REQUEST_MADE_FOR_CAMERA = 100;
    public static final int REQUEST_MADE_FOR_GALLERY = 200;
    public static final int REQUEST_MADE_FOR_DOCUMENT = 300;
    public static final int REQUEST_MADE_FOR_LOCATION_ACCESS = 400;
    public static final String NO_INTERNET_CONNECTION = "No Internet connectivity. Please check your internet connection and try again.";
    public static final String INTERNET_CONNECTIVITY_SHORT_MESSAGE = "Please check your internet connection.";
    public static final String BACK_PRESS_EXIT_MESSAGE = "Press again to Exit";
    public static final String APPLICATION_PATH = Environment.getExternalStorageDirectory() + "/" + AppController.getInstance().getApplicationContext().getResources().getString(R.string.app_name) + "/";

}
