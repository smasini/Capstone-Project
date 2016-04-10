package smasini.it.thetvdb.support;

import java.io.Serializable;
import java.util.ArrayList;

public class Actor implements Serializable {

	private String idActor;
	private String image;
	private String name;
	private String role;
	private String sortOrder;
    private String wikiBio = "";

    private ArrayList<Cast> otherSerie = new ArrayList<Cast>();

    public Actor(){
        super();
    }

	public Actor(String idActor, String image, String name, String role, String sortOrder) {
		this();
		this.idActor = idActor;
		this.image = image;
		this.name = name;
		this.role = role;
		this.sortOrder = sortOrder;
	}

    public ArrayList<Cast> getOtherSerie() {
        return otherSerie;
    }

    public void setOtherSerie(ArrayList<Cast> otherSerie) {
        this.otherSerie = otherSerie;
    }

    public String getIdActor() {
		return idActor;
	}
	public void setIdActor(String idActor) {
		this.idActor = idActor;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
    public String getWikiBio() {
        return wikiBio;
    }
    public void setWikiBio(String wikiBio) {
        this.wikiBio = wikiBio;
    }
}
