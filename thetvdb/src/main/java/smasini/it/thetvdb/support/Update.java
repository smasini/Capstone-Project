package smasini.it.thetvdb.support;

/**
 * Created by Simone Masini on 17/04/2016.
 */
public class Update {
    private String id;
    private UpdateType type;

    public Update(String id, UpdateType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UpdateType getType() {
        return type;
    }

    public void setType(UpdateType type) {
        this.type = type;
    }
}
