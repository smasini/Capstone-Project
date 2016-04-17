package smasini.it.thetvdb.task.callbacks;

import java.util.List;

import smasini.it.thetvdb.support.Update;

/**
 * Created by Simone Masini on 17/04/2016.
 */
public interface CallbackUpdate {
    void doAfterTask(List<Update> updates);
}
