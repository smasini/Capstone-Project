package smasini.it.traxer.database.contract;

import android.net.Uri;

/**
 * Project: Traxer
 * Package: smasini.it.traxer.database.contract
 * Created by Simone Masini on 13/02/2016 at 12.23.
 */
public class BaseContract {

    public static final String CONTENT_AUTHORITY = "smasini.it.traxer";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SERIE = "serie";
    public static final String PATH_EPISODE = "episode";
    public static final String PATH_ACTOR = "actor";
    public static final String PATH_CAST = "cast";
    public static final String PATH_BANNER = "banner";
}
