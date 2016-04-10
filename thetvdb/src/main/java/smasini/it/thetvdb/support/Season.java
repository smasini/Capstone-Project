package smasini.it.thetvdb.support;

import java.io.Serializable;
import java.util.ArrayList;

public class Season implements Serializable {

	private ArrayList<Episode> episodes = new ArrayList<Episode>();
	private String serieName;
    private String serieID;
	private String seasonNumber;

	public Season(){
		super();
	}

	public Season(String serieName, String seasonNumber, String serieID) {
		this.serieName = serieName;
		this.seasonNumber = seasonNumber;
        this.serieID = serieID;
	}
	
	public void addEpisode(Episode e){
		episodes.add(e);
	}

    public String getSerieID() {
        return serieID;
    }

    public void setSerieID(String serieID) {
        this.serieID = serieID;
    }

    public Episode getEpisode(int i){
		return episodes.get(i);
	}
	public ArrayList<Episode> getEpisodes() {
		return episodes;
	}
	public void setEpisodes(ArrayList<Episode> episodes) {
		this.episodes = episodes;
	}
	public String getSerieName() {
		return serieName;
	}
	public void setSerieName(String serieName) {
		this.serieName = serieName;
	}
	public String getSeasonNumber() {
		return seasonNumber;
	}
	public void setSeasonNumber(String seasonNumber) {
		this.seasonNumber = seasonNumber;
	}
	
	

}
