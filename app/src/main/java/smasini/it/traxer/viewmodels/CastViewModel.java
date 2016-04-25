package smasini.it.traxer.viewmodels;

import android.widget.ImageView;

/**
 * Created by Simone Masini on 03/04/2016.
 */
public class CastViewModel {

    private String imageUrl, name, role;
    private String id;
    private ImageView sharedImageView;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ImageView getSharedImageView() {
        return sharedImageView;
    }

    public void setSharedImageView(ImageView sharedImageView) {
        this.sharedImageView = sharedImageView;
    }
}
