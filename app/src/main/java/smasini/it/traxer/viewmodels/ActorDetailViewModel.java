package smasini.it.traxer.viewmodels;

/**
 * Created by Simone Masini on 06/04/2016.
 */
public class ActorDetailViewModel {

    private String name,role, urlImage, bioHTML;

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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getBioHTML() {
        return bioHTML;
    }

    public void setBioHTML(String bioHTML) {
        this.bioHTML = bioHTML;
    }
}
