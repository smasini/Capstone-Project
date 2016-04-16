package smasini.it.traxer.viewmodels;

/**
 * Created by Simone Masini on 03/04/2016.
 */
public class SeasonViewModel {
    private String imageUrl;
    private int number, totalEpisodes, totalEpisodeWatched;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    public int getTotalEpisodeWatched() {
        return totalEpisodeWatched;
    }

    public void setTotalEpisodeWatched(int totalEpisodeWatched) {
        this.totalEpisodeWatched = totalEpisodeWatched;
    }
}
