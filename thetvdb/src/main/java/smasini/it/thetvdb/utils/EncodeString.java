package smasini.it.thetvdb.utils;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.utils
 * Created by Simone Masini on 06/02/2016 at 15.26.
 */
public class EncodeString {

    public static String encodeString(String s){
        String s1 = s;
        if(s.indexOf(" ")>0){
            s1 = s.replace(" ", "%20");
        }
        if(s.indexOf("!")>0){
            s1 = s.replace("!", "%21");
        }
        if(s.indexOf("?")>0){
            s1 = s.replace("?", "%3F");
        }
        if(s.indexOf("=")>0){
            s1 = s.replace("=", "%3D");
        }
        if(s.indexOf("#")>0){
            s1 = s.replace("#", "%23");
        }
        if(s.indexOf("$")>0){
            s1 = s.replace("$", "%24");
        }
        if(s.indexOf(".")>0){
            s1 = s.replace(".", "%2E");
        }
        return s1;
    }
}
