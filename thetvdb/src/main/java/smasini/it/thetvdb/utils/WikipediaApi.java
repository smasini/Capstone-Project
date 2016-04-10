package smasini.it.thetvdb.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by Utente on 02/04/2016.
 */
public class WikipediaApi {

    private String language;
    private String wikipediaBaseUrl = "https://%s.wikipedia.org/w/api.php?action=query&prop=extracts&rvprop=content&format=json&titles=";

    public WikipediaApi(String language){
        this.language = language;
    }

    public WikipediaApi(){
        this.language = "en";
    }

    private JSONObject search(String urlJson){
        JSONObject jsonObject;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlJson);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.connect();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            inputStream.close();

            try {
                jsonObject = new JSONObject(sb.toString());
            }
            catch (JSONException e){
                jsonObject = new JSONObject();
            }
        } catch (Throwable t) {
            jsonObject = new JSONObject();
        } finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }
        return jsonObject;
    }

    public String searchActorOnWikipidia(String name){
        String url = String.format(wikipediaBaseUrl, language) + encodeName(name);
        JSONObject jsonObject = search(url);
        JSONObject query = jsonObject.optJSONObject("query");
        if(query!=null){
            JSONObject pages = query.optJSONObject("pages");
            if(pages!=null){
                Iterator<?> keys = pages.keys();
                while(keys.hasNext()) {
                    String key = (String)keys.next();
                    JSONObject item = pages.optJSONObject(key);
                    if(item!=null){
                        String extract = item.optString("extract");
                        if(extract!=null && !extract.equals("")){
                            return parseHTMLWiki(extract);
                        }
                    }
                }
            }
        }
        return "";
    }

    private String parseHTMLWiki(String text){
     /*   text = text.replace("<ul>","<p>");
        text = text.replace("</ul>","</p>");
        text = text.replace("<li>","- ");
        text = text.replace("</li>","<br />");*/
        return text;
    }

    private String encodeName(String s){
        String searchs = s;
        if(s.indexOf(" ")>0){
            searchs = s.replace(" ", "%20");
        }
        if(s.indexOf("!")>0){
            searchs = s.replace("!", "%21");
        }
        if(s.indexOf("?")>0){
            searchs = s.replace("?", "%3F");
        }
        if(s.indexOf("=")>0){
            searchs = s.replace("=", "%3D");
        }
        if(s.indexOf("#")>0){
            searchs = s.replace("#", "%23");
        }
        if(s.indexOf("$")>0){
            searchs = s.replace("$", "%24");
        }
        if(s.indexOf(".")>0){
            searchs = s.replace(".", "%2E");
        }
        return searchs;
    }
}
