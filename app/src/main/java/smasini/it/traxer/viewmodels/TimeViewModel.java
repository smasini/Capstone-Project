package smasini.it.traxer.viewmodels;

/**
 * Created by Simone Masini on 09/04/2016.
 */
public class TimeViewModel {

    private String days, minutes, hours;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public TimeViewModel(String days, String minutes, String hours) {
        this.days = days;
        this.minutes = minutes;
        this.hours = hours;
    }
}
