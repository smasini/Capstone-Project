package smasini.it.thetvdb.task.callbacks;

import java.util.List;

import smasini.it.thetvdb.support.Language;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.task.callbacks
 * Created by Simone Masini on 03/02/2016 at 22.00.
 */
public interface CallbackLanguage {
    void doAfterTask(List<Language> languages);
}
