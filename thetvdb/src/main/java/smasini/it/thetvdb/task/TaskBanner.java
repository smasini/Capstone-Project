package smasini.it.thetvdb.task;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import smasini.it.thetvdb.TheTVDB;
import smasini.it.thetvdb.support.Banner;
import smasini.it.thetvdb.support.XMLHelper;
import smasini.it.thetvdb.task.callbacks.CallbackBanner;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.task
 * Created by Simone Masini on 04/02/2016 at 22.45.
 */
public class TaskBanner extends AsyncTask<Void,Void,List<Banner>> {

    private String url;
    private CallbackBanner callback;

    public TaskBanner(String path, CallbackBanner callback){
        this.url = String.format("%sbanners.xml", path);
        this.callback = callback;
    }

    @Override
    protected List<Banner> doInBackground(Void... params) {
        Document document = XMLHelper.openXML(url, false);
        List<Banner> bannerList = new ArrayList<>();
        try{
            NodeList banners = document.getElementsByTagName("Banner");
            for(int i=0; i<banners.getLength(); i++){
                Node nodo = banners.item(i);
                if(nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element banner = (Element)nodo;
                    String bannerType = XMLHelper.getElement(banner,"BannerType");
                    String id = XMLHelper.getElement(banner, "id");
                    String bannerPath = TheTVDB.baseUrlImage + XMLHelper.getElement(banner,"BannerPath");
                    String bannerType2 = XMLHelper.getElement(banner,"BannerType2");
                    String language = XMLHelper.getElement(banner,"Language");
                    String rating = XMLHelper.getElement(banner,"Rating");
                    String ratingCount = XMLHelper.getElement(banner,"RatingCount");
                    String season = XMLHelper.getElement(banner,"Season");
                    Banner b = new Banner(id, bannerPath, bannerType, bannerType2, language, rating, ratingCount, season);
                    bannerList.add(b);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return bannerList;
    }


    @Override
    protected void onPostExecute(List<Banner> banners) {
        super.onPostExecute(banners);
        callback.doAfterTask(banners);
    }
}
