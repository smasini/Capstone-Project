package smasini.it.thetvdb.task.callbacks;

import java.util.List;

import smasini.it.thetvdb.support.Actor;
import smasini.it.thetvdb.support.Banner;
import smasini.it.thetvdb.support.Serie;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.task.callbacks
 * Created by Simone Masini on 06/02/2016 at 15.42.
 */
public interface CallbackSerieData {
    void onSerieDataLoad(Serie serie, List<Actor> actors, List<Banner> banners);
}
