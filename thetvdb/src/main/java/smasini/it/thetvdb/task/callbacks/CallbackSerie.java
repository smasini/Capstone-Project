package smasini.it.thetvdb.task.callbacks;

import java.util.List;

import smasini.it.thetvdb.support.Serie;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.task.callbacks
 * Created by Simone Masini on 03/02/2016 at 22.22.
 */
public interface CallbackSerie {
    void doAfterTask(List<Serie> series);
}
