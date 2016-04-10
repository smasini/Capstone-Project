package smasini.it.thetvdb.support;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.support
 * Created by Simone Masini on 03/02/2016 at 22.11.
 */
public class Mirror {

    private String mirrorpath;
    private String id;
    private String typemask;

    public Mirror(){}

    public Mirror(String mirrorpath, String id, String typemask) {
        this.mirrorpath = mirrorpath;
        this.id = id;
        this.typemask = typemask;
    }

    public String getMirrorpath() {
        return mirrorpath;
    }

    public void setMirrorpath(String mirrorpath) {
        this.mirrorpath = mirrorpath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypemask() {
        return typemask;
    }

    public void setTypemask(String typemask) {
        this.typemask = typemask;
    }
}
