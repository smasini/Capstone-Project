package smasini.it.thetvdb.task;

import android.os.AsyncTask;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;
import smasini.it.thetvdb.support.Mirror;
import smasini.it.thetvdb.support.XMLHelper;
import smasini.it.thetvdb.task.callbacks.CallbackMirror;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.task.callbacks
 * Created by Simone Masini on 03/02/2016 at 22.10.
 */
public class TaskMirror extends AsyncTask<Void,Void,List<Mirror>> {

    private String url = "";
    private CallbackMirror callback;

    public TaskMirror(String apikey, CallbackMirror callback){
        this.url = String.format("http://thetvdb.com/api/%s/mirrors.xml", apikey);
        this.callback = callback;
    }

    @Override
    protected List<Mirror> doInBackground(Void... params) {
        List<Mirror> mirrorList = new ArrayList<>();
        Document document = XMLHelper.openXML(url, true);
        try {
            NodeList mirrors = document.getElementsByTagName("Mirror");
            for(int i=0; i<mirrors.getLength(); i++) {
                Node nodo = mirrors.item(i);
                if(nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element mirror = (Element)nodo;
                    String mirrorpath = XMLHelper.getElement(mirror, "mirrorpath");
                    String typemask = XMLHelper.getElement(mirror, "typemask");
                    String id = XMLHelper.getElement(mirror, "id");
                    Mirror m = new Mirror(mirrorpath, id, typemask);
                    mirrorList.add(m);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return mirrorList;
    }

    @Override
    protected void onPostExecute(List<Mirror> mirrors) {
        super.onPostExecute(mirrors);
        callback.doAfterTask(mirrors);
    }
}
