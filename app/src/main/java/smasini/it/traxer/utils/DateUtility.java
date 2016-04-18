package smasini.it.traxer.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import smasini.it.traxer.R;
import smasini.it.traxer.app.Application;

/**
 * Project: Traxer
 * Package: smasini.it.traxer.utils
 * Created by Simone Masini on 07/02/2016 at 13.19.
 */
public class DateUtility {

    public static String formatDate(String date){
        if(date == null || date.equals("")){
            return "-";
        }
        String[] arrays = date.split("-");
        int year = Integer.parseInt(arrays[0]);
        int month = Integer.parseInt(arrays[1]) - 1;
        int day = Integer.parseInt(arrays[2]);

        Calendar cal = new GregorianCalendar(year,month,day);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Application.getStaticApplicationContext());
        String format = sp.getString(Application.getStaticApplicationContext().getString(R.string.prefs_date_format_key), Application.getStaticApplicationContext().getString(R.string.default_date_format));

        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Application.getStaticApplicationContext().getResources().getConfiguration().locale);
        dateFormat.setTimeZone(cal.getTimeZone());

        return dateFormat.format(cal.getTime());
    }
}
