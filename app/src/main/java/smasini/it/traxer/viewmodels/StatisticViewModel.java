package smasini.it.traxer.viewmodels;

/**
 * Created by Utente on 31/03/2016.
 */
public class StatisticViewModel {

    private int totalSerie, totalEpisode;
    private String days, hours, minutes;
    private int watch, unwatch;

    public int getTotalSerie() {
        return totalSerie;
    }

    public void setTotalSerie(int totalSerie) {
        this.totalSerie = totalSerie;
    }

    public int getTotalEpisode() {
        return totalEpisode;
    }

    public void setTotalEpisode(int totalEpisode) {
        this.totalEpisode = totalEpisode;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public int getWatch() {
        return watch;
    }

    public void setWatch(int watch) {
        this.watch = watch;
    }

    public int getUnwatch() {
        return unwatch;
    }

    public void setUnwatch(int unwatch) {
        this.unwatch = unwatch;
    }
}
