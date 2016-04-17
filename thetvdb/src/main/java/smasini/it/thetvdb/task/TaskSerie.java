package smasini.it.thetvdb.task;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import smasini.it.thetvdb.TheTVDB;
import smasini.it.thetvdb.support.Episode;
import smasini.it.thetvdb.support.Serie;
import smasini.it.thetvdb.support.XMLHelper;
import smasini.it.thetvdb.task.callbacks.CallbackSerie;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.task
 * Created by Simone Masini on 03/02/2016 at 22.22.
 */
public class TaskSerie extends AsyncTask<Void, Void, List<Serie>> {

    private String url;
    private boolean isOnline;
    private CallbackSerie callback;
    private boolean update = false;
    private List<String> idSeries = new ArrayList<>();
    private String language;

    public TaskSerie(String mirror, String name, String language){
        this.url = String.format("%s/api/GetSeries.php?seriesname=%s&language=%s", mirror, name, language);
        this.isOnline = true;
        this.update = false;
        this.language = language;
    }

    public TaskSerie(String path, String language){
        this.url = String.format("%s%s.xml", path, language);
        this.isOnline = false;
        this.update = false;
        this.language = language;
    }

    public TaskSerie(String mirror, String apikey, String language, List<String> idSeries){
        this.url = String.format("%s/api/%s/series/%s/all/%s.xml", mirror, apikey, "ID_SERIE_TO_REPLACE", language);
        this.isOnline = true;
        this.update = true;
        this.idSeries = idSeries;
        this.language = language;
    }

    @Override
    protected List<Serie> doInBackground(Void... params) {
        if(update){
            List<Serie> serieList = new ArrayList<>();
            for(String id : idSeries){
                String url = this.url.replace("ID_SERIE_TO_REPLACE", id);
                List<Serie> serieTemp = readXML(url);
                if(serieTemp!=null && serieTemp.size()>0) {
                    serieList.add(serieTemp.get(0));
                }
            }
            return serieList;
        }else{
            return readXML(this.url);
        }
    }

    private List<Serie> readXML(String url){
        List<Serie> serieList = new ArrayList<>();
        Document document = XMLHelper.openXML(url, isOnline);
        try {
            NodeList series = document.getElementsByTagName("Series");
            for(int i=0; i<series.getLength(); i++) {
                Serie s = null;
                Node nodo = series.item(i);
                if(nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element serie = (Element)nodo;
                    s = parseSerie(serie);
                }
                NodeList episodes = document.getElementsByTagName("Episode");
                if(s!=null && episodes!=null){
                    for(int k=0; k<episodes.getLength(); k++){
                        Node nodoEp = episodes.item(k);
                        if(nodoEp.getNodeType() == Node.ELEMENT_NODE){
                            Element episode = (Element)nodoEp;
                            Episode e = parseEpisode(episode);
                            s.getSeasons().add(e);
                        }
                    }
                }
                serieList.add(s);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return serieList;
    }

    @Override
    protected void onPostExecute(List<Serie> series) {
        super.onPostExecute(series);
        if(!update && series.size() > 1){
            List<Serie> newSeries = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            for(int i = 0; i < series.size(); i++){
                if(!ids.contains(series.get(i).getSeriesid())){
                    ids.add(series.get(i).getSeriesid());
                    newSeries.add(series.get(i));
                }
            }
            if(callback!=null)
                callback.doAfterTask(newSeries);
        }else{
            if(callback!=null)
                callback.doAfterTask(series);
        }
    }

    public void setCallback(CallbackSerie callback) {
        this.callback = callback;
    }

    public Episode parseEpisode(Element episode){
        String id = XMLHelper.getElement(episode, "id");
        String combined_episodenumber =  XMLHelper.getElement(episode, "Combined_episodenumber");
        String combined_season =  XMLHelper.getElement(episode, "Combined_season");
        String dVD_chapter =  XMLHelper.getElement(episode, "DVD_chapter");
        String dVD_discid =  XMLHelper.getElement(episode, "DVD_discid");
        String dVD_episodenumber =  XMLHelper.getElement(episode, "DVD_episodenumber");
        String dVD_season =  XMLHelper.getElement(episode, "DVD_season");
        String director =  XMLHelper.getElement(episode, "Director");
        String epImgFlag =  XMLHelper.getElement(episode, "EpImgFlag");
        String episodeName =  XMLHelper.getElement(episode, "EpisodeName");
        String episodeNumber =  XMLHelper.getElement(episode, "EpisodeNumber");
        String firstAired =  XMLHelper.getElement(episode, "FirstAired");
        String guestStars =  XMLHelper.getElement(episode, "GuestStars");
        String iMDB_ID =  XMLHelper.getElement(episode, "IMDB_ID");
        String language = XMLHelper.getElement(episode, "Language");
        String overview = XMLHelper.getElement(episode,"Overview");
        String productionCode =  XMLHelper.getElement(episode, "ProductionCode");
        String rating =  XMLHelper.getElement(episode, "Rating");
        String ratingCount =  XMLHelper.getElement(episode, "RatingCount");
        String seasonNumber= XMLHelper.getElement(episode, "SeasonNumber");
        String writer =  XMLHelper.getElement(episode, "Writer");
        String absolute_number =  XMLHelper.getElement(episode, "absolute_number");
        String airsafter_season =  XMLHelper.getElement(episode, "airsafter_season");
        String airsbefore_episode =  XMLHelper.getElement(episode, "airsbefore_episode");
        String airsbefore_season =  XMLHelper.getElement(episode, "airsbefore_season");
        String filename =  XMLHelper.getElement(episode, "filename");
        String lastupdated =  XMLHelper.getElement(episode, "lastupdated");
        String seasonid = XMLHelper.getElement(episode,"seasonid");
        String seriesid =  XMLHelper.getElement(episode, "seriesid");
        String thumb_added =  XMLHelper.getElement(episode, "thumb_added");
        String thumb_height =  XMLHelper.getElement(episode, "thumb_height");
        String thumb_width = XMLHelper.getElement(episode, "thumb_width");

        Episode e = new Episode();
        e.setId(id);
        e.setCombined_episodenumber(combined_episodenumber);
        e.setCombined_season(combined_season);
        e.setdVD_chapter(dVD_chapter);
        e.setdVD_discid(dVD_discid);
        e.setdVD_episodenumber(dVD_episodenumber);
        e.setdVD_season(dVD_season);
        e.setDirector(director);
        e.setEpImgFlag(epImgFlag);
        e.setEpisodeName(episodeName);
        e.setEpisodeNumber(episodeNumber);
        e.setFirstAired(firstAired);
        e.setGuestStars(guestStars);
        e.setiMDB_ID(iMDB_ID);
        e.setLanguage(language);
        e.setOverview(overview);
        e.setProductionCode(productionCode);
        e.setRating(rating);
        e.setRatingCount(ratingCount);
        e.setSeasonNumber(seasonNumber);
        e.setWriter(writer);
        e.setAbsolute_number(absolute_number);
        e.setAirsafter_season(airsafter_season);
        e.setAirsbefore_episode(airsbefore_episode);
        e.setAirsbefore_season(airsbefore_season);
        e.setFilename(TheTVDB.baseUrlImage + filename);
        e.setLastupdated(lastupdated);
        e.setSeasonid(seasonid);
        e.setSeriesid(seriesid);
        e.setThumb_added(thumb_added);
        e.setThumb_height(thumb_height);
        e.setThumb_width(thumb_width);

        if(!isOnline){
            //download image
        }

        return e;
    }

    public Serie parseSerie(Element serie){
        Serie s = new Serie();

        String seriesid = XMLHelper.getElement(serie, "SeriesID", "seriesid");
        String language = XMLHelper.getElement(serie, "Language");
        String seriesName = XMLHelper.getElement(serie,"SeriesName");
        String banner = XMLHelper.getElement(serie, "banner");
        String airs_DayOfWeek = XMLHelper.getElement(serie, "Airs_DayOfWeek");
        String overview = XMLHelper.getElement(serie,"Overview");
        String firstAired = XMLHelper.getElement(serie, "FirstAired");
        String network = XMLHelper.getElement(serie, "Network");
        String imdb_ID = XMLHelper.getElement(serie, "IMDB_ID");
        String zap2it_id = XMLHelper.getElement(serie, "zap2it_id");
        String airs_Time = XMLHelper.getElement(serie, "Airs_Time");
        String contentRating = XMLHelper.getElement(serie, "ContentRating");
        String genre = XMLHelper.getElement(serie, "Genre");
        String networkID = XMLHelper.getElement(serie, "NetworkID");
        String rating = XMLHelper.getElement(serie, "Rating");
        String ratingCount = XMLHelper.getElement(serie, "RatingCount");
        String runtime = XMLHelper.getElement(serie, "Runtime");
        String status = XMLHelper.getElement(serie, "Status");
        String added = XMLHelper.getElement(serie, "added");
        String addedBy = XMLHelper.getElement(serie, "addedBy");
        String fanart = XMLHelper.getElement(serie, "fanart");
        String lastupdated = XMLHelper.getElement(serie, "lastupdated");
        String poster = XMLHelper.getElement(serie, "poster");
        String tms_wanted_old = XMLHelper.getElement(serie, "tms_wanted_old");
        String id = XMLHelper.getElement(serie, "id");

        s.setSeriesid(seriesid);
        s.setLanguage(language);
        s.setSeriesName(seriesName);
        s.setBanner(TheTVDB.baseUrlImage + banner);
        s.setAirs_DayOfWeek(airs_DayOfWeek);
        s.setOverview(overview);
        s.setFirstAired(firstAired);
        s.setNetwork(network);
        s.setImdb_ID(imdb_ID);
        s.setZap2it_id(zap2it_id);
        s.setAirs_Time(airs_Time);
        s.setContentRating(contentRating);
        s.setGenre(genre);
        s.setNetworkID(networkID);
        s.setRating(rating);
        s.setRatingCount(ratingCount);
        s.setRuntime(runtime);
        s.setStatus(status);
        s.setAdded(added);
        s.setAddedBy(addedBy);
        s.setFanart(fanart);
        s.setLastupdated(lastupdated);
        s.setPoster(TheTVDB.baseUrlImage + poster);
        s.setTms_wanted_old(tms_wanted_old);
        s.setId(id);

        if(!isOnline){
            //download image
        }

        return s;

    }
}
