package smasini.it.thetvdb.support;

public class Banner {
	
	private String id;
	private String bannerPath;
	private String bannerType;
	private String bannerType2;
	private String language;
	private String raiting;
	private String ratingCount;
	private String season;
	
	public Banner(){
		super();
	}
	
	public Banner(String id, String bannerPath, String bannerType, String bannerType2, String language, String raiting, String ratingCount, String season) {
		super();
		this.id = id;
		this.bannerPath = bannerPath;
		this.bannerType = bannerType;
		this.bannerType2 = bannerType2;
		this.language = language;
		this.raiting = raiting;
		this.ratingCount = ratingCount;
		this.season = season;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBannerPath() {
		return bannerPath;
	}
	public void setBannerPath(String bannerPath) {
		this.bannerPath = bannerPath;
	}
	public String getBannerType() {
		return bannerType;
	}
	public void setBannerType(String bannerType) {
		this.bannerType = bannerType;
	}
	public String getBannerType2() {
		return bannerType2;
	}
	public void setBannerType2(String bannerType2) {
		this.bannerType2 = bannerType2;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getRaiting() {
		return raiting;
	}
	public void setRaiting(String raiting) {
		this.raiting = raiting;
	}
	public String getRatingCount() {
		return ratingCount;
	}
	public void setRatingCount(String ratingCount) {
		this.ratingCount = ratingCount;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}
}
