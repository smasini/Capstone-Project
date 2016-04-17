package smasini.it.thetvdb.task;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import smasini.it.thetvdb.support.Update;
import smasini.it.thetvdb.support.UpdateType;
import smasini.it.thetvdb.support.XMLHelper;
import smasini.it.thetvdb.task.callbacks.CallbackUpdate;

/**
 * Created by Simone Masini on 17/04/2016.
 */
public class TaskUpdate extends AsyncTask<Void, Void, List<Update>> {

    private String url = "";
    private CallbackUpdate callback;

    public TaskUpdate(String mirror, String serverTime){
        this.url = String.format("%s/api/Updates.php?type=all&time=%s", mirror, serverTime);
    }

    public void setCallback(CallbackUpdate callback) {
        this.callback = callback;
    }

    @Override
    protected List<Update> doInBackground(Void... params) {
        List<Update> updates = new ArrayList<>();
        Document document = XMLHelper.openXML(url, true);
        try {
            NodeList series = document.getElementsByTagName("Series");
            for(int i=0; i<series.getLength(); i++){
                Node nodo = series.item(i);
                Element serie = (Element)nodo;
                String idSerie = serie.getTextContent();
                updates.add(new Update(idSerie, UpdateType.SERIE));
            }
            NodeList episodes = document.getElementsByTagName("Episode");
            for(int i=0; i<episodes.getLength(); i++) {
                Node nodo = episodes.item(i);
                Element episode = (Element) nodo;
                String idEp = episode.getTextContent();
                updates.add(new Update(idEp, UpdateType.EPISODE));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return updates;
    }

    @Override
    protected void onPostExecute(List<Update> updates) {
        super.onPostExecute(updates);
        if(callback!=null){
            callback.doAfterTask(updates);
        }

    }
}
