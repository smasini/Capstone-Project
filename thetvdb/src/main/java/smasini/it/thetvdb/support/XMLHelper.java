package smasini.it.thetvdb.support;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class XMLHelper {

    public static Document openXML(String path, boolean isOnline){
        URL url;
        URLConnection conn;
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = documentFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e2) {
            e2.printStackTrace();
        }
        Document document = null;
        if(isOnline){
            try {
                url = new URL(path);
                conn = url.openConnection();
                document = builder.parse(conn.getInputStream());
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }else{
            try {
                document = builder.parse(new File(path));
            } catch ( SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return document;
    }

    public static String getElement(Element element, String tagName){
        try{
            if(element.getElementsByTagName(tagName).item(0)!=null){
                return element.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
            }
        } catch(NullPointerException e){
            return "";
        }
        return "";
    }

    public static String getElement(Element element, String tagName1, String tagName2){
        String s = getElement(element, tagName1);
        if(!s.equals("")){
            return s;
        }
        s = getElement(element, tagName2);
        if(!s.equals("")){
            return s;
        }
        return "";
    }
}
