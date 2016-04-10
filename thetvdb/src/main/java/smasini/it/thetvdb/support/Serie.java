package smasini.it.thetvdb.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Serie implements Serializable {

	private String seriesid;
	private String language;
	private String seriesName;
	private String banner;
	private String overview;
	private String firstAired;
	private String network;
	private String imdb_ID;
	private String zap2it_id;
	private String id;
	private String airs_DayOfWeek;
	private String airs_Time;
	private String contentRating;
	private String genre;
	private String networkID;
	private String rating;
	private String ratingCount;
	private String runtime;
	private String status;
	private String added;
	private String addedBy;
	private String fanart;
	private String lastupdated;
	private String poster;
	private String tms_wanted_old;
	private ArrayList<Episode> seasons = new ArrayList<Episode>();
    private ArrayList<Actor> cast = new ArrayList<Actor>();
    private String rate;

	public Serie(){
		super();
	}

	public Serie(String seriesid, String language, String seriesName,
			String banner, String overview, String firstAired, String network,
			String imdb_ID, String zap2it_id, String id, String airs_DayOfWeek,
			String airs_Time, String contentRating, String genre,
			String networkID, String rating, String ratingCount,
			String runtime, String status, String added,
			String addedBy, String fanart, String lastupdated, String poster,
			String tms_wanted_old) {
		this();
		this.seriesid = seriesid;
		this.language = language;
		this.seriesName = seriesName;
		this.banner = banner;
		this.overview = overview;
		this.firstAired = firstAired;
		this.network = network;
		this.imdb_ID = imdb_ID;
		this.zap2it_id = zap2it_id;
		this.id = id;
		this.airs_DayOfWeek = airs_DayOfWeek;
		this.airs_Time = airs_Time;
		this.contentRating = contentRating;
		this.genre = genre;
		this.networkID = networkID;
		this.rating = rating;
		this.ratingCount = ratingCount;
		this.runtime = runtime;
		this.status = status;
		this.added = added;
		this.addedBy = addedBy;
		this.fanart = fanart;
		this.lastupdated = lastupdated;
		this.poster = poster;
		this.tms_wanted_old = tms_wanted_old;
	}

    public ArrayList<Actor> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Actor> cast) {
        this.cast = cast;
    }

    public ArrayList<Episode> getSeasons() {
		return seasons;
	}

	public void setSeasons(ArrayList<Episode> seasons) {
		this.seasons = seasons;
	}
	
	public String getAirs_DayOfWeek() {
		return airs_DayOfWeek;
	}

	public void setAirs_DayOfWeek(String airs_DayOfWeek) {
		this.airs_DayOfWeek = airs_DayOfWeek;
	}

	public String getAirs_Time() {
		return airs_Time;
	}

	public void setAirs_Time(String airs_Time) {
		this.airs_Time = airs_Time;
	}

	public String getContentRating() {
		return contentRating;
	}

	public void setContentRating(String contentRating) {
		this.contentRating = contentRating;
	}

	public String getGenre() {
        Scanner scan = new Scanner(genre.replace("|", "--"));
        scan.useDelimiter("--");
        String genres = "";
        while(scan.hasNext()){
            genres += scan.next() + "\n";
        }
		return genres;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getNetworkID() {
		return networkID;
	}

	public void setNetworkID(String networkID) {
		this.networkID = networkID;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(String ratingCount) {
		this.ratingCount = ratingCount;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAdded() {
		return added;
	}

	public void setAdded(String added) {
		this.added = added;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getFanart() {
		return fanart;
	}

	public void setFanart(String fanart) {
		this.fanart = fanart;
	}

	public String getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getTms_wanted_old() {
		return tms_wanted_old;
	}

	public void setTms_wanted_old(String tms_wanted_old) {
		this.tms_wanted_old = tms_wanted_old;
	}

	public String getSeriesid() {
		return seriesid;
	}


	public void setSeriesid(String seriesid) {
		this.seriesid = seriesid;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getSeriesName() {
		return seriesName;
	}


	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}


	public String getBanner() {
		return banner;
	}


	public void setBanner(String banner) {
		this.banner = banner;
	}


	public String getOverview() {
		return overview;
	}


	public void setOverview(String overview) {
		this.overview = overview;
	}


	public String getFirstAired() {
		return firstAired;
	}


	public void setFirstAired(String firstAired) {
		this.firstAired = firstAired;
	}


	public String getNetwork() {
		return network;
	}


	public void setNetwork(String network) {
		this.network = network;
	}


	public String getImdb_ID() {
		return imdb_ID;
	}


	public void setImdb_ID(String imdb_ID) {
		this.imdb_ID = imdb_ID;
	}


	public String getZap2it_id() {
		return zap2it_id;
	}


	public void setZap2it_id(String zap2it_id) {
		this.zap2it_id = zap2it_id;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
