package smasini.it.thetvdb.support;

import java.io.Serializable;

/**
 * Created by Simone on 02/11/2014.
 */
public class Cast implements Serializable {

    private Serie serie;
    private String role;

    public Cast(String role, Serie serie) {
        this.role = role;
        this.serie = serie;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
