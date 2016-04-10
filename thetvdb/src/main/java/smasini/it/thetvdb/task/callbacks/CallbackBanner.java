package smasini.it.thetvdb.task.callbacks;

import java.util.List;

import smasini.it.thetvdb.support.Banner;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.task.callbacks
 * Created by Simone Masini on 04/02/2016 at 22.46.
 */
public interface CallbackBanner {
    void doAfterTask(List<Banner> banners);
}
