package concaro.hackernews.app.presentation.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import concaro.hackernews.app.presentation.util.*;

/**
 * Created by Concaro on 2/15/2017.
 */

public class DateTimeUtils {

    public static final String SERVER_FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SERVER_FORMAT = "yyyy-MM-dd";
    public static final String DISPLAY_FORMAT = "d MMM yyy";

    public static final String DATE_DISPLAY_FORMAT = "dd-MM-yyyy";
    public static final String SERVER_POST_FORMAT = "yyyy-MM-dd";

    public static final String DISPLAY_FORMAT_FULL = "d MMM yyy '-' h:mm aa";


    public static final String MONTH_FORMAT = "MM";

    public static String format(String date) {
        if (!TextUtils.isEmpty(date)) {
            SimpleDateFormat readFormat = new SimpleDateFormat(SERVER_FORMAT);
            SimpleDateFormat format2 = new SimpleDateFormat(DISPLAY_FORMAT);
            readFormat.setTimeZone(TimeZone.getTimeZone(concaro.hackernews.app.presentation.util.Constants.SINGAPORE_TIMEZONE));
            try {
                Date d = readFormat.parse(date);
                return format2.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String formatFullDate(String date) {
        if (!TextUtils.isEmpty(date)) {
            SimpleDateFormat readFormat = new SimpleDateFormat(SERVER_FULL_FORMAT);
            SimpleDateFormat format2 = new SimpleDateFormat(DISPLAY_FORMAT);
            readFormat.setTimeZone(TimeZone.getTimeZone(concaro.hackernews.app.presentation.util.Constants.SINGAPORE_TIMEZONE));
            try {
                Date d = readFormat.parse(date);
                return format2.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String formatFullDateTime(String date) {
        if (!TextUtils.isEmpty(date)) {
            SimpleDateFormat readFormat = new SimpleDateFormat(SERVER_FULL_FORMAT);
            SimpleDateFormat format2 = new SimpleDateFormat(DISPLAY_FORMAT_FULL);
            readFormat.setTimeZone(TimeZone.getTimeZone(concaro.hackernews.app.presentation.util.Constants.SINGAPORE_TIMEZONE));
            try {
                Date d = readFormat.parse(date);
                return format2.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static boolean compareMonth(String date1, String date2) {
        if (!TextUtils.isEmpty(date1) && !TextUtils.isEmpty(date2)) {
            SimpleDateFormat format1 = new SimpleDateFormat(SERVER_FULL_FORMAT);
            SimpleDateFormat format2 = new SimpleDateFormat(MONTH_FORMAT);
            try {
                Date d1 = format1.parse(date1);
                Date d2 = format1.parse(date2);
                return format2.format(d1).equalsIgnoreCase(format2.format(d2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean compareDayMonth(String date1, String date2) {
        if (!TextUtils.isEmpty(date1) && !TextUtils.isEmpty(date2)) {
            SimpleDateFormat format1 = new SimpleDateFormat(SERVER_FULL_FORMAT);
            SimpleDateFormat format2 = new SimpleDateFormat("d MM");
            try {
                Date d1 = format1.parse(date1);
                Date d2 = format1.parse(date2);
                return format2.format(d1).equalsIgnoreCase(format2.format(d2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String getDayMonth(String date1, boolean displayMonth) {
        if (!TextUtils.isEmpty(date1)) {
            SimpleDateFormat format1 = new SimpleDateFormat(SERVER_FULL_FORMAT);
            SimpleDateFormat format2 = null;
            if (displayMonth) {
                format2 = new SimpleDateFormat("d MMM");
            } else {
                format2 = new SimpleDateFormat("d");
            }
            try {
                Date d1 = format1.parse(date1);
                return format2.format(d1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getTime(String date1, String date2) {
        if (!TextUtils.isEmpty(date1) && !TextUtils.isEmpty(date2)) {
            SimpleDateFormat readFormat = new SimpleDateFormat(SERVER_FULL_FORMAT);
            SimpleDateFormat format2 = new SimpleDateFormat("ha");
            readFormat.setTimeZone(TimeZone.getTimeZone(concaro.hackernews.app.presentation.util.Constants.SINGAPORE_TIMEZONE));
            try {
                Date d1 = readFormat.parse(date1);
                Date d2 = readFormat.parse(date2);
                return format2.format(d1) + " - " + format2.format(d2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String displayDate(String date1, String date2) {
        String display = "";
        boolean inAMonth = DateTimeUtils.compareMonth(date1, date2);
        boolean inDayMonth = DateTimeUtils.compareDayMonth(date1, date2);
        String dayMonth = "";
        if (inDayMonth) {
            dayMonth = DateTimeUtils.formatFullDate(date2);
        } else {
            dayMonth = DateTimeUtils.getDayMonth(date1, !inAMonth) + " - " + DateTimeUtils.formatFullDate(date2);
        }
        display += dayMonth +
                ", " + DateTimeUtils.getTime(date1, date2);

        return display;
    }


    public interface DateSetListener {
        void onDateSet(String date);
    }

    public static void showDatePicker(Context context, final DateSetListener lisntener) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (lisntener != null) {
                            lisntener.onDateSet(getDay(dayOfMonth) + "-" + getDay((monthOfYear + 1)) + "-" + year);
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public static void showDatePicker(String date, Context context, final DateSetListener lisntener) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        if (!TextUtils.isEmpty(date)) {
            SimpleDateFormat format1 = new SimpleDateFormat(SERVER_FORMAT);
            try {
                Date d = format1.parse(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                mYear = cal.get(Calendar.YEAR);
                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (lisntener != null) {
                            lisntener.onDateSet(getDay(dayOfMonth) + "-" + getDay((monthOfYear + 1)) + "-" + year);
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private static String getDay(int day) {
        if (day < 10) {
            return "0" + day;
        }
        return String.valueOf(day);
    }

    public static String formatDisplayToServerPost(String date) {
        if (!TextUtils.isEmpty(date)) {
            SimpleDateFormat readFormat = new SimpleDateFormat(DATE_DISPLAY_FORMAT);
            SimpleDateFormat format2 = new SimpleDateFormat(SERVER_POST_FORMAT);
            readFormat.setTimeZone(TimeZone.getTimeZone(concaro.hackernews.app.presentation.util.Constants.SINGAPORE_TIMEZONE));
            try {
                Date d = readFormat.parse(date);
                return format2.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String formatServerPostToDisplay(String date) {
        if (!TextUtils.isEmpty(date)) {
            SimpleDateFormat readFormat = new SimpleDateFormat(SERVER_POST_FORMAT);
            SimpleDateFormat format2 = new SimpleDateFormat(DATE_DISPLAY_FORMAT);
            readFormat.setTimeZone(TimeZone.getTimeZone(concaro.hackernews.app.presentation.util.Constants.SINGAPORE_TIMEZONE));
            try {
                Date d = readFormat.parse(date);
                return format2.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    public static int getAge(int DOByear, int DOBmonth, int DOBday) {

        int age;

        final Calendar calenderToday = Calendar.getInstance();
        int currentYear = calenderToday.get(Calendar.YEAR);
        int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
        int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);

        age = currentYear - DOByear;

        if (DOBmonth > currentMonth) {
            --age;
        } else if (DOBmonth == currentMonth) {
            if (DOBday > todayDay) {
                --age;
            }
        }
        return age;
    }


    public static Date formatDateRealm(String dateString) {
        Date date = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = formatter.parse(dateString);

        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return date;
    }

    public static boolean isNewTime(String date1, String date2) {
        if (!TextUtils.isEmpty(date1) && !TextUtils.isEmpty(date2)) {
            SimpleDateFormat format = new SimpleDateFormat(SERVER_FULL_FORMAT);
            format.setTimeZone(TimeZone.getTimeZone(concaro.hackernews.app.presentation.util.Constants.SINGAPORE_TIMEZONE));
            try {
                Date today = new Date();
                Date d1 = format.parse(date1);
                Date d2 = format.parse(date2);
                if (today.after(d2)) {
                    return true;
                } else {
                    return false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
