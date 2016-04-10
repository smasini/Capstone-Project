package smasini.it.thetvdb.support;

import java.io.Serializable;

public class Episode implements Serializable {

	private String id;
	private String combined_episodenumber;
	private String combined_season;
	private String dVD_chapter;
	private String dVD_discid;
	private String dVD_episodenumber;
	private String dVD_season;
	private String director;
	private String epImgFlag;
	private String episodeName;
	private String episodeNumber;
	private String firstAired;
	private String guestStars;
	private String iMDB_ID;
	private String language;
	private String overview;
	private String productionCode;
	private String rating;
	private String ratingCount;
	private String seasonNumber;
	private String writer;
	private String absolute_number;
	private String airsafter_season;
	private String airsbefore_episode;
	private String airsbefore_season;
	private String filename;
	private String lastupdated;
	private String seasonid;
	private String seriesid;
	private String thumb_added;
	private String thumb_height;
	private String thumb_width;
    private boolean watch = false;
    private String rate;
    //private String settingEp = "se";//SE//se//0x//x

	public Episode(){
		super();
	}

	public Episode(String id, String combined_episodenumber,
			String combined_season, String dVD_chapter, String dVD_discid,
			String dVD_episodenumber, String dVD_season, String director,
			String epImgFlag, String episodeName, String episodeNumber,
			String firstAired, String guestStars, String iMDB_ID,
			String language, String overview, String productionCode,
			String rating, String ratingCount, String seasonNumber,
			String writer, String absolute_number, String airsafter_season,
			String airsbefore_episode, String airsbefore_season,
			String filename, String lastupdated, String seasonid,
			String seriesid, String thumb_added, String thumb_height,
			String thumb_width) {
		this();
		this.id = id;
		this.combined_episodenumber = combined_episodenumber;
		this.combined_season = combined_season;
		this.dVD_chapter = dVD_chapter;
		this.dVD_discid = dVD_discid;
		this.dVD_episodenumber = dVD_episodenumber;
		this.dVD_season = dVD_season;
		this.director = director;
		this.epImgFlag = epImgFlag;
		this.episodeName = episodeName;
		this.episodeNumber = episodeNumber;
		this.firstAired = firstAired;
		this.guestStars = guestStars;
		this.iMDB_ID = iMDB_ID;
		this.language = language;
		this.overview = overview;
		this.productionCode = productionCode;
		this.rating = rating;
		this.ratingCount = ratingCount;
		this.seasonNumber = seasonNumber;
		this.writer = writer;
		this.absolute_number = absolute_number;
		this.airsafter_season = airsafter_season;
		this.airsbefore_episode = airsbefore_episode;
		this.airsbefore_season = airsbefore_season;
		this.filename = filename;
		this.lastupdated = lastupdated;
		this.seasonid = seasonid;
		this.seriesid = seriesid;
		this.thumb_added = thumb_added;
		this.thumb_height = thumb_height;
		this.thumb_width = thumb_width;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCombined_episodenumber() {
		return combined_episodenumber;
	}

	public void setCombined_episodenumber(String combined_episodenumber) {
		this.combined_episodenumber = combined_episodenumber;
	}

	public boolean isWatch() {
		return watch;
	}

	public void setWatch(boolean watch) {
		this.watch = watch;
	}

	public String getCombined_season() {
		return combined_season;
	}

	public void setCombined_season(String combined_season) {
		this.combined_season = combined_season;
	}

	public String getdVD_chapter() {
		return dVD_chapter;
	}

	public void setdVD_chapter(String dVD_chapter) {
		this.dVD_chapter = dVD_chapter;
	}

	public String getdVD_discid() {
		return dVD_discid;
	}

	public void setdVD_discid(String dVD_discid) {
		this.dVD_discid = dVD_discid;
	}

	public String getdVD_episodenumber() {
		return dVD_episodenumber;
	}

	public void setdVD_episodenumber(String dVD_episodenumber) {
		this.dVD_episodenumber = dVD_episodenumber;
	}

	public String getdVD_season() {
		return dVD_season;
	}

	public void setdVD_season(String dVD_season) {
		this.dVD_season = dVD_season;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getEpImgFlag() {
		return epImgFlag;
	}

	public void setEpImgFlag(String epImgFlag) {
		this.epImgFlag = epImgFlag;
	}

	public String getEpisodeName() {
		return episodeName;
	}

	public void setEpisodeName(String episodeName) {
		this.episodeName = episodeName;
	}

    public int getEpisodeNumberInt(){
        return Integer.parseInt(episodeNumber);
    }
	public String getEpisodeNumber() {
        int n = Integer.parseInt(episodeNumber);
        String ret = episodeNumber;
        if(n<10){
            ret = "0" + n;
        }
		return ret;
	}

	public void setEpisodeNumber(String episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public String getFirstAired() {
		return firstAired;
	}

	public void setFirstAired(String firstAired) {
		this.firstAired = firstAired;
	}

	public String getGuestStars() {
		return guestStars;
	}

	public void setGuestStars(String guestStars) {
		this.guestStars = guestStars;
	}

	public String getiMDB_ID() {
		return iMDB_ID;
	}

	public void setiMDB_ID(String iMDB_ID) {
		this.iMDB_ID = iMDB_ID;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getProductionCode() {
		return productionCode;
	}

	public void setProductionCode(String productionCode) {
		this.productionCode = productionCode;
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

    public int getSeasonNumberInt(){
        return Integer.parseInt(seasonNumber);
    }

	public String getSeasonNumberFormatted() {
        int n = Integer.parseInt(seasonNumber);
        String ret = seasonNumber;
        if(n<10 ){
            ret = "0" + n;
        }
		return ret;
	}

	public void setSeasonNumber(String seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getAbsolute_number() {
		return absolute_number;
	}

	public void setAbsolute_number(String absolute_number) {
		this.absolute_number = absolute_number;
	}

	public String getAirsafter_season() {
		return airsafter_season;
	}

	public void setAirsafter_season(String airsafter_season) {
		this.airsafter_season = airsafter_season;
	}

	public String getAirsbefore_episode() {
		return airsbefore_episode;
	}

	public void setAirsbefore_episode(String airsbefore_episode) {
		this.airsbefore_episode = airsbefore_episode;
	}

	public String getAirsbefore_season() {
		return airsbefore_season;
	}

	public void setAirsbefore_season(String airsbefore_season) {
		this.airsbefore_season = airsbefore_season;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}

	public String getSeasonid() {
		return seasonid;
	}

	public void setSeasonid(String seasonid) {
		this.seasonid = seasonid;
	}

	public String getSeriesid() {
		return seriesid;
	}

	public void setSeriesid(String seriesid) {
		this.seriesid = seriesid;
	}

	public String getThumb_added() {
		return thumb_added;
	}

	public void setThumb_added(String thumb_added) {
		this.thumb_added = thumb_added;
	}

	public String getThumb_height() {
		return thumb_height;
	}

	public void setThumb_height(String thumb_height) {
		this.thumb_height = thumb_height;
	}

	public String getThumb_width() {
		return thumb_width;
	}

	public void setThumb_width(String thumb_width) {
		this.thumb_width = thumb_width;
	}

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
