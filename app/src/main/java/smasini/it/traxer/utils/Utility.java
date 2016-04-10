package smasini.it.traxer.utils;

import smasini.it.traxer.viewmodels.TimeViewModel;

/**
 * Project: Traxer
 * Package: smasini.it.traxer.utils
 * Created by Simone Masini on 14/02/2016 at 09.26.
 */
public class Utility {

    public static final String DEFAULT_LANGUAGE = "en";

    public static String formatEpisode(int episode, int season){
        String txt = "S";
        if(season < 10){
            txt += "0";
        }
        txt += season;
        txt += "E";
        if(episode < 10){
            txt += "0";
        }
        txt += episode;
        return txt;
    }

    public static String getLanguage(){
        return "it";
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
            return "Extra";
        }
        return "Season " + seasonNumber;
    }
}
