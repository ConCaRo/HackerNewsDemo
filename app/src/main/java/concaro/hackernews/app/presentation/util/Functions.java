package concaro.hackernews.app.presentation.util;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import concaro.hackernews.app.R;
import concaro.hackernews.app.app.MyApplication;
import concaro.hackernews.app.presentation.util.*;


/**
 * Created by CONCARO on 10/27/2015.
 */
public class Functions {

    public static void showLogMessage(String tag, String message) {
        if (concaro.hackernews.app.presentation.util.Constants.DEBUG && tag != null && message != null) {
            Log.d(tag, message);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
//        activity.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Check if no view has focus:
        try {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
//            Crashlytics.log(e.getMessage());
        }
    }

    public static String getKeHash(Activity activity) {
        // Add code to print out the key hash
        String keyHash = "";
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    "concaro.hackernews.app", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", android.util.Base64.encodeToString(
                        md.digest(), android.util.Base64.DEFAULT));
                keyHash = android.util.Base64.encodeToString(md.digest(),
                        android.util.Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        return keyHash;
    }

    public static void showToastLongMessage(Context context, String message) {
        if (context != null && !TextUtils.isEmpty(message))
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToastShortMessage(Context context, String message) {
        if (context != null && !TextUtils.isEmpty(message))
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackbarLongMessage(View layout, String message) {
        if (layout != null && !TextUtils.isEmpty(message)) {
            if ((Activity) layout.getContext() != null) {
                hideSoftKeyboard((Activity) layout.getContext());
            }
            Functions.showLogMessage("Trong", message);
            Snackbar snackbar = Snackbar
                    .make(layout, message, Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
            View snackbarView = snackbar.getView();
            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            tv.setMaxLines(3);
            snackbar.show();
        }
    }

    public static void showSnackbarShortMessage(View layout, String message) {
        if (layout != null && !TextUtils.isEmpty(message)) {
            if ((Activity) layout.getContext() != null) {
                hideSoftKeyboard((Activity) layout.getContext());
            }
            Snackbar snackbar = Snackbar
                    .make(layout, message, Snackbar.LENGTH_SHORT)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
            View snackbarView = snackbar.getView();
            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            tv.setMaxLines(3);
            snackbar.show();
        }
    }

//    public static void showLogMessage(String tag, String message) {
//        if (Constants.DEBUG && tag != null && message != null) {
//            Log.d(tag, message);
//        }
//    }

    public static void showAlertDialog(Context context, String title, String message) {
        if (context != null && !TextUtils.isEmpty(message)) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            hideSoftKeyboard((Activity) context);

            if (!TextUtils.isEmpty(title)) {
                alertDialogBuilder.setTitle(title);
            }
            alertDialogBuilder
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
//                .setNegativeButton("No",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        // if this button is clicked, just close
//                        // the dialog box and do nothing
//                        dialog.cancel();
//                    }
//                });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public static void showAlertDialog(Context context, String title, String message, final CallbackAlertDialogListener callback) {
        if (context != null && !TextUtils.isEmpty(message)) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            hideSoftKeyboard((Activity) context);

            if (!TextUtils.isEmpty(title)) {
                alertDialogBuilder.setTitle(title);
            }
            alertDialogBuilder
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (callback != null) {
                                callback.onClickOk();
                            }
                            dialog.dismiss();
                        }
                    });
//                .setNegativeButton("No",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        // if this button is clicked, just close
//                        // the dialog box and do nothing
//                        dialog.cancel();
//                    }
//                });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public static void showAlertDialogYesNo(Context context, String title, String message, final CallbackAlertDialogListener callback) {
        if (context != null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            hideSoftKeyboard((Activity) context);

            if (!TextUtils.isEmpty(title)) {
                alertDialogBuilder.setTitle(title);
            }
            alertDialogBuilder
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (callback != null) {
                                callback.onClickOk();
                            }
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            if (callback != null) {
                                callback.onCickCancel();
                            }
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public static void showSystemAlertDialog(Context context, String title, String message) {
        if (context != null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context, R.style.MyAlertDialogStyle);
//        if ((Activity) context != null) {
//            hideSoftKeyboard((Activity) context);
//        }
            if (!TextUtils.isEmpty(title)) {
                alertDialogBuilder.setTitle(title);
            }
            alertDialogBuilder
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
//                .setNegativeButton("No",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        // if this button is clicked, just close
//                        // the dialog box and do nothing
//                        dialog.cancel();
//                    }
//                });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            alertDialog.show();
        }
    }


    public static Location getLocation(Context context) {
        Location location = null;
        if (context != null) {
            final MyApplication applicationContext = (MyApplication) context.getApplicationContext();
            if (applicationContext != null) {
                if (applicationContext.getLocation() != null) {
                    location = applicationContext.getLocation();
                }
            }
        }
        return location;
    }

    public static void setLocation(Context context, Location location) {
        if (context != null) {
            final MyApplication applicationContext = (MyApplication) context.getApplicationContext();
            if (applicationContext != null) {
                applicationContext.setLocation(location);
            }
        }
    }

    public static long convertDateStrToLong_UTC(String text)
            throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(concaro.hackernews.app.presentation.util.Constants.SERVER_FORMAT,
                Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.parse(text).getTime();
    }

    public static Long convertDateStrToLong_ASIA(String strReferenceTime) {
        long milliseconds = 0;
        try {
            if (strReferenceTime == null) {
                return null;
            }
            SimpleDateFormat f = new SimpleDateFormat(concaro.hackernews.app.presentation.util.Constants.SERVER_FORMAT);
            f.setTimeZone(TimeZone.getTimeZone(concaro.hackernews.app.presentation.util.Constants.SINGAPORE_TIMEZONE));
            Date d = new Date();
            d = f.parse(strReferenceTime);
            milliseconds = d.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return milliseconds;
    }

    @SuppressWarnings("ResourceType")
    public static Location getLastBestLocation(LocationManager locationManager) {
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) {
            GPSLocationTime = locationGPS.getTime();
        }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if (0 < GPSLocationTime - NetLocationTime) {
            return locationGPS;
        } else {
            return locationNet;
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    //Animation for devices with kitkat and below
    public static void animationDown(final LinearLayout layoutAdded, int originalHeight) {

        // Declare a ValueAnimator object
        ValueAnimator valueAnimator;
        if (!layoutAdded.isShown()) {
            layoutAdded.setVisibility(View.VISIBLE);
            layoutAdded.setEnabled(true);
            valueAnimator = ValueAnimator.ofInt(0, originalHeight + originalHeight); // These values in this method can be changed to expand however much you like
        } else {
            valueAnimator = ValueAnimator.ofInt(originalHeight + originalHeight, 0);

            Animation a = new AlphaAnimation(1.00f, 0.00f); // Fade out

            a.setDuration(200);
            // Set a listener to the animation and configure onAnimationEnd
            a.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    layoutAdded.setVisibility(View.INVISIBLE);
                    layoutAdded.setEnabled(false);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            // Set the animation on the custom view
            layoutAdded.startAnimation(a);
        }
        valueAnimator.setDuration(200);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                layoutAdded.getLayoutParams().height = value.intValue();
                layoutAdded.requestLayout();
            }
        });


        valueAnimator.start();
    }


    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }


    public static int getDistance(String start_lat, String start_lng, String des_lat, String des_lng) {
        float result1[] = new float[3];
        Location.distanceBetween(Double.parseDouble(start_lat),
                Double.parseDouble(start_lng), Double.parseDouble(des_lat), Double.parseDouble(des_lng), result1);
        return (int) result1[0];
    }


    public static void reset(Context context) {
        final MyApplication applicationContext = (MyApplication) context.getApplicationContext();
        if (applicationContext != null) {
            CustomSharedPreferences.init(context);
            CustomSharedPreferences.setPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_ACCOUNT, "");
            CustomSharedPreferences.setPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_BLOCK, "");
            CustomSharedPreferences.setPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_MAJOR, "");
            CustomSharedPreferences.setPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_ANSWERED, "");
            CustomSharedPreferences.setPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_NOTI, true);
            CustomSharedPreferences.setPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_ACCOUNT, "");
            CustomSharedPreferences.setPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_LOAD_THUMBNAIL, true);
            CustomSharedPreferences.setPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_MESSAGE_FB, 0);
        }
    }


    public static void saveBlockToSharedPref(Context activity, String block_id) {
        if (!TextUtils.isEmpty(block_id)) {
            CustomSharedPreferences.init(activity);
            CustomSharedPreferences.setPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_BLOCK, block_id);
        }
    }

    public static String getBlockFromSharedPref(Context activity) {
        String result = "";
        CustomSharedPreferences.init(activity);
        result = CustomSharedPreferences.getPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_BLOCK, "");
        return result;
    }

    public static void saveMajorToSharedPref(Context activity, String majors_list) {
        if (!TextUtils.isEmpty(majors_list)) {
            CustomSharedPreferences.init(activity);
            CustomSharedPreferences.setPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_MAJOR, majors_list);
        }
    }

    public static String getMajorFromSharedPref(Context activity) {
        String result = "";
        CustomSharedPreferences.init(activity);
        result = CustomSharedPreferences.getPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_MAJOR, "");
        return result;
    }

    public static void saveAnsweredTypeToSharedPref(Context activity, String answered) {
        if (!TextUtils.isEmpty(answered)) {
            CustomSharedPreferences.init(activity);
            CustomSharedPreferences.setPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_ANSWERED, answered);
        }
    }


    public static String getRealPathFromURI(Activity activity, Uri contentURI) {
        String result = null;
        if (contentURI != null) {
            try {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = activity.getContentResolver().query(contentURI, proj, null, null,
                        null);
                if (cursor == null) { // Source is Dropbox or other similar
                    // local
                    // file
                    // path
                    result = contentURI.getPath();
                } else {
                    cursor.moveToFirst();
                    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    result = cursor.getString(idx);
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * get bytes array from Uri.
     *
     * @param context current context.
     * @param uri     uri fo the file to read.
     * @return a bytes array.
     * @throws IOException
     */
    public static byte[] getBytes(Context context, Uri uri) throws IOException {
        InputStream iStream = context.getContentResolver().openInputStream(uri);
        try {
            return getBytes(iStream);
        } finally {
            // close the stream
            try {
                iStream.close();
            } catch (IOException ignored) { /* do nothing */ }
        }
    }


    /**
     * get bytes from input stream.
     *
     * @param inputStream inputStream.
     * @return byte array read from the inputStream.
     * @throws IOException
     */
    public static byte[] getBytes(InputStream inputStream) throws IOException {

        byte[] bytesResult = null;
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        try {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            bytesResult = byteBuffer.toByteArray();
        } finally {
            // close the stream
            try {
                byteBuffer.close();
            } catch (IOException ignored) { /* do nothing */ }
        }
        return bytesResult;
    }

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static RequestBody toRequestBody(String value) {
        if (value != null) {
            RequestBody body = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), value);
            return body;
        }
        return null;
    }

    public static MultipartBody.Part prepareFilePart(Context context, String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = null;
// = FileUtils.getFile(context, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static MultipartBody.Part prepareFilePart(Context context, String partName, final byte[] bimap) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), bimap);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, partName, requestFile);
    }

    public static String capitalizeFirstLetter(String s) {
        if (s == null) return null;
        if (s.length() == 1) {
            return s.toUpperCase();
        }
        if (s.length() > 1) {
            s = s.toLowerCase();
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
        return "";
    }


    public static void setSquareViewpager(ViewPager viewPager, Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = width;
        android.widget.RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) viewPager
                .getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        viewPager.setLayoutParams(layoutParams);
    }

    public static void setSquareImageView(ImageView viewPager, Activity activity, int _height) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = _height;
        int height = _height;
        android.widget.RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) viewPager
                .getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        viewPager.setLayoutParams(layoutParams);
    }

    public static String formatTextMerchant(String number) {
        String result = "";
        if (number != null) {
            result = convertToShortNumberFromNumber(number);
            if (number.equals("1")) {
                result += " merchant";
            } else {
                result += " merchants";
            }
        }

        return result;
    }

    public static String convertToShortNumberFromNumber(String strNumber) {
        int index = 0;
        String result = "";
        try {
            double number = Double.parseDouble(strNumber);

            double backup = number;
            result = String.format("%.0f", number);
            String[] suffix = {"", "k", "M", "G", "T", "P", "E"};

            while (number >= 1000) {
                number /= 1000;
                index++;
            }

            if (index > 0) {
                int firstNum = (int) number;
                int secondNum = (int) (backup / Math.pow(10, 3 * index - 1) - firstNum * 10);
                result = String.format("%d.%d%s", firstNum, secondNum, suffix[index]);
            }

        } catch (Exception e) {

        }

        return result;
    }

    public static String getSerialNo(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String deviceID = telephonyManager.getDeviceId();

            if (deviceID != null && !deviceID.equals("")) {
                return deviceID;
            }
            /**
             * if there's no imei then use mac address of wifi
             */
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wInfo = wifiManager.getConnectionInfo();
            String macAddress = wInfo.getMacAddress();

            if (macAddress != null && !macAddress.equals("")) {
                return macAddress;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
        // return "355136056633517";
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }


    public static void openPhoneCall(Context context, String phoneNumnber) {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumnber));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void openMessage(Context context, String phoneNumnber) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"));
        context.startActivity(sendIntent);
    }

    public static void openMap(Context context, float latitude, float longitude) {
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(intent);
    }

    public static void openMap(Context context, String _latlng, String label) {
        String[] latlng = _latlng.replace("{", "").replace("}", "").split(",");
        if (latlng.length == 2) {
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=15&q=%f,%f(%s)", Float.valueOf(latlng[0]),
                    Float.valueOf(latlng[1]), Float.valueOf(latlng[0]), Float.valueOf(latlng[1]), label);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            context.startActivity(intent);
        }
    }

    public static void openLink(Context context, String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        context.startActivity(intent);
    }


    private static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }



    public static void setBackgroundColorAndRetainShape(final int color, final Drawable background) {

        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background.mutate()).getPaint().setColor(color);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background.mutate()).setColor(color);
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background.mutate()).setColor(color);
        } else {
            Log.w("Trong", "Not a valid background type");
        }
    }


    public static String getDomainName(String url) {
        URI uri;
        try {
            uri = new URI(url);
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch (Exception e) {
            return url;
        }
    }

    private static final String ABBR_YEAR = "y";
    private static final String ABBR_WEEK = "w";
    private static final String ABBR_DAY = "d";
    private static final String ABBR_HOUR = "h";
    private static final String ABBR_MINUTE = "m";

    public static String getAbbreviatedTimeSpan(long timeMillis) {
        long span = Math.max(System.currentTimeMillis() - (timeMillis * 1000), 0);
        if (span >= DateUtils.YEAR_IN_MILLIS) {
            return (span / DateUtils.YEAR_IN_MILLIS) + ABBR_YEAR;
        }
        if (span >= DateUtils.WEEK_IN_MILLIS) {
            return (span / DateUtils.WEEK_IN_MILLIS) + ABBR_WEEK;
        }
        if (span >= DateUtils.DAY_IN_MILLIS) {
            return (span / DateUtils.DAY_IN_MILLIS) + ABBR_DAY;
        }
        if (span >= DateUtils.HOUR_IN_MILLIS) {
            return (span / DateUtils.HOUR_IN_MILLIS) + ABBR_HOUR;
        }
        return (span / DateUtils.MINUTE_IN_MILLIS) + ABBR_MINUTE;
    }
}
