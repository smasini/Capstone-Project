package smasini.it.thetvdb.task;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import smasini.it.thetvdb.TheTVDB;
import smasini.it.thetvdb.support.Actor;
import smasini.it.thetvdb.support.XMLHelper;
import smasini.it.thetvdb.task.callbacks.CallbackActor;
import smasini.it.thetvdb.utils.WikipediaApi;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.task
 * Created by Simone Masini on 04/02/2016 at 22.38.
 */
public class TaskActor extends AsyncTask<Void, Void, List<Actor>> {

    private String url;
    private CallbackActor callback;

    public TaskActor(String path, CallbackActor callback){
        this.url = String.format("%sactors.xml", path);
        this.callback = callback;
    }

    @Override
    protected List<Actor> doInBackground(Void... params) {
        List<Actor> actorList = new ArrayList<>();
        WikipediaApi wikipediaApi = new WikipediaApi();
        Document document = XMLHelper.openXML(url, false);
        try{
            NodeList actors = document.getElementsByTagName("Actor");
            for(int i=0; i<actors.getLength(); i++){
                Node nodo = actors.item(i);
                if(nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element actor = (Element)nodo;
                    String idActor = XMLHelper.getElement(actor, "id");
                    String image = TheTVDB.baseUrlImage + XMLHelper.getElement(actor,"Image");
                    String name = XMLHelper.getElement(actor,"Name");
                    String role = XMLHelper.getElement(actor,"Role");
                    String sortOrder = XMLHelper.getElement(actor,"SortOrder");
                    Actor a = new Actor(idActor, image, name, role, sortOrder);
                    a.setWikiBio(wikipediaApi.searchActorOnWikipidia(a.getName()));
                    actorList.add(a);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return actorList;
    }

    @Override
    protected void onPostExecute(List<Actor> actors) {
        super.onPostExecute(actors);
        callback.doAfterTask(actors);
    }
}
