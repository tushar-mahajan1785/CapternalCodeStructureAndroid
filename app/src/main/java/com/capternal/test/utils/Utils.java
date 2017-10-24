package com.capternal.test.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capternal.test.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Capternal on 05/10/16.
 */

public class Utils {

    public static Bitmap getCircularImage(Bitmap imageBitmap) {
        final Bitmap output = Bitmap.createBitmap(imageBitmap.getWidth(),
                imageBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, imageBitmap.getWidth(), imageBitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(imageBitmap, rect, rect, paint);
        return output;
    }

    public static void showGPSDisabledAlertToUser(final ActionBarActivity actionBarActivity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actionBarActivity);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Please enable it.")
                .setCancelable(false)
                .setPositiveButton("Location Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                actionBarActivity.startActivity(callGPSSettingIntent);
                            }
                        });
//        alertDialogBuilder.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static void d(String TAG, String message) {
        int maxLogSize = 2000;
        if (message != null) {
            for (int i = 0; i <= message.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                android.util.Log.d(TAG, message.substring(start, end));
            }
        }
    }

    public static void out(String message) {
        /*int maxLogSize = 2000;
        if (message!=null) {
            for(int i = 0; i <= message.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i+1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                android.util.Log.d(TAG, message.substring(start, end));
            }
        }*/
        System.out.println(message);
    }

    public static String getIMEINumber(Context context) {
        TelephonyManager objTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return objTelephonyManager.getDeviceId();
    }

    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static Bitmap convertBitmap(String path) {

        Bitmap bitmap = null;
        BitmapFactory.Options bfOptions = new BitmapFactory.Options();
        bfOptions.inDither = false;                     //Disable Dithering mode
        bfOptions.inPurgeable = true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
        bfOptions.inInputShareable = true;              //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
        bfOptions.inTempStorage = new byte[32 * 1024];


        File file = new File(path);
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (fs != null) {
                bitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
            }
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }

    public static String formatDate(String date, String fromFormat, String toFormat) throws ParseException {
        Date initDate = null;
        try {
            initDate = new SimpleDateFormat(fromFormat).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(toFormat);
        String parsedDate = formatter.format(initDate);
        return parsedDate;
    }

    /**
     * CHECKS EMAIL ID IS VALID OR NOT
     **/
    public static boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static ArrayList<ActivityInfo> getAllRunningActivities(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);

            Utils.out("ARRAY LIST OF ACTIVITIES:" + Arrays.asList(pi.activities));
            return new ArrayList<>(Arrays.asList(pi.activities));

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;
        Utils.out("YOGA APP VERSION :" + version);
        return version;
    }

    public static boolean hasImage(ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
        }

        return hasImage;
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        DisplayMetrics objDisplayMetrics = new DisplayMetrics();
//        final int initialWidth = v.getMeasuredWidth();
        final int initialWidth = objDisplayMetrics.widthPixels;
        System.out.println("SCREEN WIDTH " + initialWidth);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.INVISIBLE);
                } else {
                   /* v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();*/
                    System.out.println("TIME : " + (initialWidth - (int) (initialWidth * interpolatedTime)));
                    v.getLayoutParams().width = initialWidth - (int) (initialWidth * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        // 1dp/ms
        a.setDuration((int) (initialWidth / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public static void expand(final View v) {
        v.setVisibility(View.VISIBLE);
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        final int targetWidth = v.getMeasuredWidth();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().width = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().width = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetWidth * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        // 1dp/ms
        a.setDuration((int) (targetWidth / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void hideKeyboard(Context context, View v) {
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static int getAge(int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a = 0;
        try {
            y = cal.get(Calendar.YEAR);
            m = cal.get(Calendar.MONTH);
            d = cal.get(Calendar.DAY_OF_MONTH);
            cal.set(_year, _month, _day);
            a = y - cal.get(Calendar.YEAR);
            if ((m < cal.get(Calendar.MONTH))
                    || ((m == cal.get(Calendar.MONTH)) && (d < cal
                    .get(Calendar.DAY_OF_MONTH)))) {
                --a;
            }
            if (a < 0) {
//            throw new IllegalArgumentException("Age < 0");
            } else {
                return a;
            }
        } catch (IllegalArgumentException ae) {
            ae.printStackTrace();
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    /*public static void setFont(CustomTextView textViewDescription, Context context) {
        textViewDescription.setTypeface(Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.muliRegular)));
    }*/


    public static AlertDialog prepareAlert(Activity actionBarActivity, String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actionBarActivity);
        alertDialogBuilder.setMessage(message)
                .setTitle(title)
                .setCancelable(true);

        AlertDialog alert = alertDialogBuilder.create();
        return alert;
    }

    /**
     * This will prepare the AlertDialog object to be used to show when user leaves the activity, by pressing the <b>back arrow (Placed of upper left corner)</b> or <b>onBackPressed().</b>
     * <p>
     * <p>Here we added the color of those two button i.d POSITIVE AND NEGATIVE BUTTON of alert dialog.</p>
     * <p>Positive button color : colorAccent
     * Negative button color : color_grey.
     * </p>
     *
     * @return android.support.v7.app.AlertDialog object for chain action.
     */

    public static AlertDialog prepareAlertForBack(final Activity actionBarActivity, String title, String message) {
//        1. Create alert dialog object Using AlertDialog.Builder.
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actionBarActivity);
        alertDialogBuilder.setMessage(message)
                .setTitle(title)
                .setCancelable(true);


        final AlertDialog alert = alertDialogBuilder.create();

        //2. now setup to change color of the button
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(actionBarActivity.getResources().getColor(R.color.colorAccent));
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(actionBarActivity.getResources().getColor(R.color.colorGrey));
            }
        });
        return alert;
    }


    public static long getFileSizeFromUri(Context activity, Uri objUri) {
        /** Get the file's content URI from the incoming Intent,
         * then query the server app to get the file's display name
         * and size.
         */
        Cursor returnCursor = activity.getContentResolver().query(objUri, null, null, null, null);
        /** Get the column indexes of the data in the Cursor,
         * move to the first row in the Cursor, get the data,
         * and display it.
         */
        long fileSize = 0;
        if (returnCursor != null) {
            returnCursor.moveToFirst();
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            fileSize = returnCursor.getLong(sizeIndex);

        }
        Utils.out("FILE SIZE IN BYTES :" + (fileSize) + " bytes");
        Utils.out("FILE SIZE IN KB    :" + (fileSize / 1024) + " KB");
        Utils.out("FILE SIZE IN MB    :" + ((fileSize / 1024) / 1024) + " MB");
        return fileSize;
    }

    public static EditText traverseEditTexts(ViewGroup v) {
//        Not null :: means we got the editText with data
//        Null      :: means all editText are empty.
        EditText invalid = null;
        for (int i = 0; i < v.getChildCount(); i++) {
            Object child = v.getChildAt(i);
            if (child instanceof EditText) {
                EditText e = (EditText) child;
                if (e.getText().length() != 0)    // Whatever logic here to determine if valid.
                {
                    return e;   // Stops at first invalid one. But you could add this to a list.
                }
            } else if (child instanceof ViewGroup) {
                invalid = traverseEditTexts((ViewGroup) child);  // Recursive call.
                if (invalid != null) {
                    break;
                }
            }
        }
        return invalid;
    }

    public static boolean isEditTextEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    //    Prepare progress dialog.
    public static ProgressDialog prepareDialog(Activity activity, ProgressDialog progressDialog, boolean cancellable) {

        progressDialog = new ProgressDialog(activity, R.style.custom_progress_dialog_style);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        progressDialog.setCancelable(cancellable);
        progressDialog.setContentView(R.layout.custom_progress_layout);
        return progressDialog;
    }

    public static String getFullGenderFromShort(String shortGenderName) {
        switch (shortGenderName) {
            case "M":
                return "Male";
            case "F":
                return "Female";

        }
        return shortGenderName;
    }


    public static String getMaritalStatusFromShortName(String shortMaritalStatus) {
        switch (shortMaritalStatus) {
            case "U":
                return "Single";
            case "M":
                return "Married";
            case "D":
                return "Divorced";
            case "W":
                return "Widowed";
            case "S":
                return "Separated";

        }
        return shortMaritalStatus;
    }

    public static int getCharCount(String mainString, char charToBeFind) {
        String s = mainString;
        int counter = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == charToBeFind) {
                counter++;
            }
        }
        return counter;
    }

    public static int getMonth(String s) {
        switch (s) {
            case "Jan":
                return 0;
            case "Feb":
                return 1;
            case "Mar":
                return 2;
            case "Apr":
                return 3;
            case "May":
                return 4;
            case "Jun":
                return 5;
            case "Jul":
                return 6;
            case "Aug":
                return 7;
            case "Sep":
                return 8;
            case "Oct":
                return 9;
            case "Nov":
                return 10;
            case "Dec":
                return 11;
        }
        return 0;
    }


    public static boolean isEditTextEmpty(TextView objTextView) {
        return objTextView.getText().toString().isEmpty();
    }

    public static String getStringMonthName(int monthOfYear) {
        String monthName = "";
        switch (monthOfYear) {
            case 0:
                monthName = "Jan";
                break;
            case 1:
                monthName = "Feb";
                break;
            case 2:
                monthName = "Mar";
                break;
            case 3:
                monthName = "Apr";
                break;
            case 4:
                monthName = "May";
                break;
            case 5:
                monthName = "Jun";
                break;
            case 6:
                monthName = "Jul";
                break;
            case 7:
                monthName = "Aug";
                break;
            case 8:
                monthName = "Sep";
                break;
            case 9:
                monthName = "Oct";
                break;
            case 10:
                monthName = "Nov";
                break;
            case 11:
                monthName = "Dec";
                break;
        }
        return monthName;

    }

    public static String getStringFullMonthName(int monthOfYear) {
        String monthName = "";
        switch (monthOfYear) {
            case 0:
                monthName = "January";
                break;
            case 1:
                monthName = "February";
                break;
            case 2:
                monthName = "March";
                break;
            case 3:
                monthName = "April";
                break;
            case 4:
                monthName = "May";
                break;
            case 5:
                monthName = "June";
                break;
            case 6:
                monthName = "July";
                break;
            case 7:
                monthName = "August";
                break;
            case 8:
                monthName = "September";
                break;
            case 9:
                monthName = "October";
                break;
            case 10:
                monthName = "November";
                break;
            case 11:
                monthName = "December";
                break;
        }
        return monthName;

    }

    /**
     * Method accept the original json array, position, New JsonObject.
     * JsonObject will be put on the provided position and method will return the updated jsonArray.
     *
     * @return Updated JSONArray.
     */
    public static JSONArray updateJsonArray(JSONArray jsonArrayOriginal, int position, JSONObject newJsonObject) {
        JSONArray jsonArrayDummy = new JSONArray();
        try {
            for (int i = 0; i < jsonArrayOriginal.length(); i++) {
                if (i == position) {
                    jsonArrayDummy.put(newJsonObject);
                } else {
                    jsonArrayDummy.put(jsonArrayOriginal.getJSONObject(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArrayDummy;
    }

    public static void showSnackBar(View view, String message) {
        /*TSnackbar snackbar = TSnackbar.make(view, message, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#99000000"));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();*/

        //        Snackbar.make(view, message).show();
        if (!message.equals(Constants.NO_INTERNET_CONNECTION)) {
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public static void setFontItalic(Context context, EditText editText) {
        Typeface objTypeFace = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/Franklin Gothic Book Italic.ttf");
        editText.setTypeface(objTypeFace);
    }

    public static void setFontBold(Context context, EditText editText) {
        Typeface objTypeFace = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/Franklin Gothic Bold.ttf");
        editText.setTypeface(objTypeFace);
    }

    public static void setFontRegular(Context context, EditText editText) {
        Typeface objTypeFace = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/Franklin Gothic Book Regular.ttf");
        editText.setTypeface(objTypeFace);
    }

    public static void createCircleShapeWithBorder(View v, int backgroundColor, int strockColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setCornerRadii(new float[]{8, 8, 8, 8, 0, 0, 0, 0});
        shape.setColor(backgroundColor);
        shape.setStroke(1, strockColor);
        v.setBackgroundDrawable(shape);
    }

    /**
     * ACTIVITY TRANSITION ANIMATION
     * */

    /**
     * Push to next activity with transition effect.
     */
    public static void pushToNext(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    /**
     * Push to next activity with transition effect.
     */
    public static void pushToNextWithTransition(Activity activity, Intent intent, ActivityOptionsCompat options) {
        activity.startActivity(intent, options.toBundle());
//        activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public static void pushToBack(Activity activity, Intent intent) {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((FragmentActivity) activity).supportFinishAfterTransition();
        } else {*/
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//        }
    }

    /**
     * Push to back activity with transition effect.
     */
    public static void pushToBack(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    /**
     * Push to back activity with transition effect.
     */
    public static void pushToBackWithTransition(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((FragmentActivity) activity).supportFinishAfterTransition();
        } else {
            activity.finish();
            activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        }
    }

    /**
     * Close activity with slide to bottom animation.
     */
    public static void closeActivityToBottom(Activity activity) {
        activity.finish();
//        activity.overridePendingTransition(R.anim.pull_from_top, R.anim.push_out_bottom);
        activity.overridePendingTransition(R.anim.stay_animation, R.anim.push_out_bottom);
    }

    /**
     * Open activity with "Bottom come animation".
     */
    public static void openActivityForResultFromBottom(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.pull_from_bottom, R.anim.stay_animation);
    }

    /**
     * Start Activity with clear top.
     */
    public static void pushWithClearTop(Activity activity, Intent objIntent) {
        objIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(objIntent);
        activity.finish();
        activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

}