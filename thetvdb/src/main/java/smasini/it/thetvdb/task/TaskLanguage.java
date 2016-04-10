package smasini.it.thetvdb.task;

import android.os.AsyncTask;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;
import smasini.it.thetvdb.support.Language;
import smasini.it.thetvdb.support.XMLHelper;
import smasini.it.thetvdb.task.callbacks.CallbackLanguage;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.task
 * Created by Simone Masini on 03/02/2016 at 21.53.
 */
public class TaskLanguage extends AsyncTask<Void, Void, List<Language>> {

    private String url = "";
    private CallbackLanguage callback;

    public TaskLanguage(String mirrorPath, String apikey, CallbackLanguage callback){
        this.url = String.format("%s/api/%s/languages.xml", mirrorPath, apikey);
        this.callback = callback;
    }

    @Override
    protected List<Language> doInBackground(Void... params) {
        List<Language> languageArrayList = new ArrayList<>();
        Document document = XMLHelper.openXML(url, true);
        try {
            NodeList languages = document.getElementsByTagName("Language");
            for(int i=0; i<languages.getLength(); i++) {
                Node nodo = languages.item(i);
                if(nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element language = (Element)nodo;
                    String nomeLingua =  XMLHelper.getElement(language, "name");
                    String sigla = XMLHelper.getElement(language,"abbreviation");
                    String id = XMLHelper.getElement(language,"id");
                    Language l = new Language(nomeLingua, sigla, id);
                    languageArrayList.add(l);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return languageArrayList;
    }

    @Override
    protected void onPostExecute(List<Language> languages) {
        super.onPostExecute(languages);
        callback.doAfterTask(languages);
    }
}
