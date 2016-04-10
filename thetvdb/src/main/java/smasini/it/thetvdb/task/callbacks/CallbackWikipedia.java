package smasini.it.thetvdb.task.callbacks;

/**
 * Created by Utente on 02/04/2016.
 */
public interface CallbackWikipedia {
    void onActorBiographyFound(String bioHTML);
    void onActorBiographyError();
}
