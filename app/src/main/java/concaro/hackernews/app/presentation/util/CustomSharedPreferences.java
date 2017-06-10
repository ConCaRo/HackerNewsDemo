package concaro.hackernews.app.presentation.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import concaro.hackernews.app.presentation.util.*;

public class CustomSharedPreferences {
    /** KEY SHARE PREFERENCS **/

    /**
     * FEILD OF CLASS *
     */
    private static SharedPreferences mSharedPre;
    private static SharedPreferences.Editor mEditor;
    private static Context mContext;

    @SuppressLint("CommitPrefEdits")
    public static void init(final Context context) {
        mContext = context;
        if (null != context) {
            if (mSharedPre == null) {
                mSharedPre = context.getSharedPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_NAME,
                        0);
            }

            mEditor = mSharedPre.edit();
        } else {
            mEditor = null;
            mSharedPre = null;
        }
    }

    @SuppressLint("CommitPrefEdits")
    private static void refresh() {
        if (null != mContext) {
            mSharedPre = mContext.getSharedPreferences(concaro.hackernews.app.presentation.util.Constants.PREFS_NAME, 0);
            mEditor = mSharedPre.edit();
        } else {
            mEditor = null;
            mSharedPre = null;
        }
    }

    /**
     * Set data for String
     *
     * @param preName Preferences name
     * @param value   String input
     */
    public synchronized static void setPreferences(final String preName,
                                                   final String value) {
        refresh();
        if (null != mEditor) {
            mEditor.putString(preName, value);
            mEditor.commit();
        }
    }

    /**
     * Get data for String
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return String or 0 if Name not existed
     */
    public static String getPreferences(final String preName,
                                        final String defaultValue) {
        refresh();
        if (null != mSharedPre) {

            return mSharedPre.getString(preName, defaultValue);
        } else {

            return null;
        }
    }

    /**
     * Set data for boolean
     *
     * @param preName Preferences name
     * @param value   boolean input
     */
    public synchronized static void setPreferences(final String preName,
                                                   final boolean value) {
        if (null != mEditor) {
            mEditor.putBoolean(preName, value);
            mEditor.commit();
        }
    }

    /**
     * Get data for boolean
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return boolean or 0 if Name not existed
     */
    public static boolean getPreferences(final String preName,
                                         final boolean defaultValue) {
        if (null != mSharedPre) {
            return mSharedPre.getBoolean(preName, defaultValue);
        }

        return defaultValue;
    }

    /**
     * Set data for Integer
     *
     * @param preName Preferences name
     * @param value   Integer input
     */
    public synchronized static void setPreferences(final String preName,
                                                   final int value) {
        if (null != mEditor) {
            mEditor.putInt(preName, value);
            mEditor.commit();
        }
    }

    /**
     * Get data for Integer
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return Integer or 0 if Name not existed
     */
    public static int getPreferences(final String preName,
                                     final int defaultValue) {
        if (null != mSharedPre) {
            return mSharedPre.getInt(preName, defaultValue);
        } else {
            return 0;
        }
    }

    /**
     * Set data for Long
     *
     * @param preName Preferences name
     * @param value   Long input
     */
    public synchronized static void setPreferences(final String preName,
                                                   final long value) {
        if (null != mEditor) {
            mEditor.putLong(preName, value);
            mEditor.commit();
        }
    }

    /**
     * Get data for Long
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return Long or 0 if Name not existed
     */
    public static long getPreferences(final String preName,
                                      final long defaultValue) {
        if (null != mSharedPre) {
            return mSharedPre.getLong(preName, defaultValue);
        } else {
            return 0;
        }
    }

    /**
     * Set data for Float
     *
     * @param preName Preferences name
     * @param value   Float input
     */
    public synchronized static void setPreferences(final String preName,
                                                   final float value) {
        if (null != mEditor) {
            mEditor.putFloat(preName, value);
            mEditor.commit();
        }
    }

    /**
     * Get data for Float
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return Float or 0 if Name not existed
     */
    public static float getPreferences(final String preName,
                                       final float defaultValue) {
        if (null != mSharedPre) {
            return mSharedPre.getFloat(preName, defaultValue);
        } else {
            return 0;
        }

    }
}
