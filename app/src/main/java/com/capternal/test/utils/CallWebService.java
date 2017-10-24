package com.capternal.test.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;

import com.capternal.test.R;
import com.capternal.test.base.AppController;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

/**
 * Created by CapternalSystems on 8/30/2016.
 */
public class CallWebService extends AsyncTask<String, Void, String> {

    private OnGetUrlResponse onGetUrlResponse;
    private JSONArray objImages = null;
    private JSONArray objFiles = null;
    String URL = "";
    JSONObject objJsonObject = null;
    Activity objActivity;
    WebserviceResponse objWebserviceResponse;
    public boolean showLoader = false;
    public static ProgressDialog progressDialog;
    boolean isMultiPart = false;

    //    Variable to be used in GET METHOD response.
    boolean isGet = false;
    private String urlId = "";

    //    Database.
    private AppController appController;
    private Activity activity;

    public CallWebService(String URL, JSONObject objJsonObject, Activity objActivity, WebserviceResponse objWebserviceResponse, boolean showLoader) {
        this.URL = URL;
        this.objJsonObject = objJsonObject;
        this.objActivity = objActivity;
        this.objWebserviceResponse = objWebserviceResponse;
        this.showLoader = showLoader;
        appController = (AppController) objActivity.getApplication();
    }

    public CallWebService(String URL, JSONObject objJsonObject, Activity objActivity, WebserviceResponse objWebserviceResponse) {
        this.URL = URL;
        this.objJsonObject = objJsonObject;
        this.objActivity = objActivity;
        this.objWebserviceResponse = objWebserviceResponse;
        this.showLoader = false;
    }

    public CallWebService(String URL, JSONObject objJsonObject, WebserviceResponse objWebserviceResponse) {
        this.URL = URL;
        this.objJsonObject = objJsonObject;
        this.objActivity = objActivity;
        this.objWebserviceResponse = objWebserviceResponse;
        this.showLoader = false;
    }

    public CallWebService(String URL, JSONArray objImages, JSONArray objFiles, JSONObject objJsonObject, Activity objActivity, WebserviceResponse objWebserviceResponse, boolean showLoader, boolean isMultiPart) {
        this.URL = URL;
        this.objImages = objImages;
        this.objFiles = objFiles;
        this.objJsonObject = objJsonObject;
        this.objActivity = objActivity;
        this.objWebserviceResponse = objWebserviceResponse;
        this.showLoader = showLoader;
        this.isMultiPart = isMultiPart;
        appController = (AppController) objActivity.getApplication();
    }

    public CallWebService(String URL, Activity objActivity, OnGetUrlResponse onGetUrlResponse, boolean showLoader, boolean isGet, String urlId) {
        this.URL = URL;
        this.objActivity = objActivity;
        this.onGetUrlResponse = onGetUrlResponse;
        this.showLoader = showLoader;
        this.isGet = isGet;
        this.urlId = urlId;
        appController = (AppController) objActivity.getApplication();
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        if (showLoader) {
            progressDialog = new ProgressDialog(objActivity, R.style.custom_progress_dialog_style);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progress_layout);
        }
    }

    public static void dismissDialog() {
        try {
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String strResult = "";
        JSONObject objJsonObjectResult = new JSONObject();
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            if (exitValue == 0) {
                System.out.println("CONNECTIVITY SUCCESS");
            } else {
                System.out.println("CONNECTIVITY FAIL");
                try {
                    objJsonObjectResult.put("result", false);
                    objJsonObjectResult.put("message", Constants.NO_INTERNET_CONNECTION);
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    return objJsonObjectResult.toString();
                } catch (NullPointerException ne) {
                    ne.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            if (isMultiPart) {
                Utils.d("API INPUT : MULTIPART : IMAGES : ", String.valueOf(objImages));
                Utils.d("API INPUT : MULTIPART : FILES : ", " " + String.valueOf(objFiles));
                Utils.d("API INPUT : MULTIPART : DATA : ", " " + String.valueOf(objJsonObject));
                Utils.d("API INPUT : MULTIPART : URL : ", " " + String.valueOf(URL));

                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(URL);
                   /* post.setHeader("Accept", "application/json");
                    post.setHeader("Content-type", "application/json");*/
//                    post.setHeader(Constants.KEY_AUTH_TOKEN, database.getUserModel().getToken());
                    HttpResponse response = null;
                    MultipartEntity entityBuilder = new MultipartEntity();

                    Iterator<String> iter = objJsonObject.keys();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        try {
                            Object value = objJsonObject.get(key);
                            Utils.out("DATA : KEY " + key + " VALUE : " + value);
                            entityBuilder.addPart(key, new StringBody(String.valueOf(value)));
                        } catch (JSONException e) {
                            // Something went wrong!
                        }
                    }
                    if (objFiles != null) {
                        for (int fileIndex = 0; fileIndex < objFiles.length(); fileIndex++) {
                            File file = new File((objFiles.getJSONObject(fileIndex).getString("Files")));
                            FileBody objFile = new FileBody(file);
                            entityBuilder.addPart("file" + fileIndex, objFile);
                        }
                    }
                    if (objImages != null) {
                        for (int imageIndex = 0; imageIndex < objImages.length(); imageIndex++) {
                            File file = new File((objImages.getJSONObject(imageIndex).getString("Images")));
                            FileBody objFile = new FileBody(file);
                            entityBuilder.addPart("Image" + imageIndex, objFile);
                        }
                    }
                    post.setEntity(entityBuilder);
                    response = client.execute(post);
                    HttpEntity httpEntity = response.getEntity();
                    strResult = EntityUtils.toString(httpEntity);
                    Utils.d("API OUTPUT : JSON OBJECT : ", strResult);
                    return strResult;
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                } catch (ClientProtocolException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return null;
            } else if (isGet) {
                strResult = NetworkUtils.getData(this.URL, objActivity, "");
            } else {
                strResult = NetworkUtils.postData(this.URL, this.objJsonObject.toString(), "", objActivity);
            }
        } catch (NullPointerException ne) {
            ne.printStackTrace();
            dismissDialog();
        } catch (Exception e) {
            e.printStackTrace();
            dismissDialog();
        }
        return strResult;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dismissDialog();
        /**
         * If Session expired then clear the preferences and take the user outside of the app to the login screen.
         * */

        try {
            JSONObject jsonObject = new JSONObject(s);
            if ((jsonObject.has(Constants.API_RESULT) && !jsonObject.getBoolean(Constants.API_RESULT))
                    && (jsonObject.has(Constants.API_MESSAGE) && jsonObject.getString(Constants.API_MESSAGE).equals(Constants.API_UNAUTHORIZED))) {
                AlertDialog alertDialog = Utils.prepareAlert(objActivity, "Error", jsonObject.getString(Constants.API_MESSAGE));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*Intent intent = new Intent(objActivity, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        database.deleteTableData(Database.TABLE_USER);
                        Utils.pushToBack(objActivity, intent);
                        objActivity.finishAffinity();*/
                    }
                });
                alertDialog.show();
            } else {
                if (isGet) {
                    onGetUrlResponse.onGetUrlResponse(this.urlId, this.URL, s);
                } else {
                    objWebserviceResponse.onWebserviceResponse(this.URL, s);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        if (isGet) {
            onGetUrlResponse.onGetUrlResponse(this.urlId, this.URL, s);
        } else {
            objWebserviceResponse.onWebserviceResponse(this.URL, s);
        }
    }

    public interface WebserviceResponse {
        public void onWebserviceResponse(String strUrl, String strResult);
    }

    public interface OnGetUrlResponse {
        public void onGetUrlResponse(String urlId, String strUrl, String strResult);

        public void onGetUrlCancelled(String urlId, String strUrl, String cancelledResult);
    }
}
