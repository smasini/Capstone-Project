package smasini.it.traxer.utils;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import smasini.it.traxer.R;
import smasini.it.traxer.app.Application;
import smasini.it.traxer.viewmodels.TimeViewModel;

/**
 * Project: Traxer
 * Package: smasini.it.traxer.utils
 * Created by Simone Masini on 14/02/2016 at 09.26.
 */
public class Utility {

    public static final String DEFAULT_LANGUAGE = Application.getStaticApplicationContext().getString(R.string.default_language);

    public static String formatEpisode(int episode, int season){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Application.getStaticApplicationContext());
        String format = sp.getString(Application.getStaticApplicationContext().getString(R.string.prefs_episode_style_key), Application.getStaticApplicationContext().getString(R.string.default_episode_style));
        String txt = "";
        switch (format){
            case "S01E01":
                txt = String.format("S%02dE%02d", season, episode);
                break;
            case "s01e01":
                txt = String.format("s%02de%02d", season, episode);
                break;
            case "S1E1":
                txt = String.format("S%dE%d", season, episode);
                break;
            case "s1e1":
                txt = String.format("s%de%d", season, episode);
                break;
            case "01x01":
                txt = String.format("%02dx%02d", season, episode);
                break;
            case "1x01":
                txt = String.format("%dx%02d", season, episode);
                break;
            case "1x1":
                txt = String.format("%dx%d", season, episode);
                break;
        }
        return txt;
    }

    public static String getLanguage(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Application.getStaticApplicationContext());
        return sp.getString(Application.getStaticApplicationContext().getString(R.string.language_key), DEFAULT_LANGUAGE);
    }

    public static TimeViewModel formatTime(int time){
        int min = time % 60;
        int hourT = time / 60;
        int hour = hourT % 24;
        int dayT = hourT / 24;
        int day = dayT % 30;

        String days = "";
        if(day<10){
            days += "0";
        }
        days += day;

        String hours = "";
        if(hour<10){
            hours += "0";
        }
        hours += hour;

        String mins = "";
        if(min<10){
            mins += "0";
        }
        mins += min;
        return new TimeViewModel(days, mins, hours);
    }

    public static String formatSeasonName(int seasonNumber) {
        if(seasonNumber == 0){
            return Application.getStaticApplicationContext().getString(R.string.special_season_name);
        }
        return String.format(Application.getStaticApplicationContext().getString(R.string.season_number_name), seasonNumber);
    }

    public static int getColor(int res){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Application.getStaticApplicationContext().getColor(res);
        }else{
            return Application.getStaticApplicationContext().getResources().getColor(res);
        }
    }

    public static boolean includeExtra(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Application.getStaticApplicationContext());
        return sp.getBoolean(Application.getStaticApplicationContext().getString(R.string.prefs_special_episode_key), false);
    }
}
