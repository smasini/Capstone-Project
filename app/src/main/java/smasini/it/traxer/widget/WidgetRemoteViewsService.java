package smasini.it.traxer.widget;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import smasini.it.traxer.R;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.utils.DateUtility;
import smasini.it.traxer.utils.Utility;
import smasini.it.traxer.viewmodels.EpisodeItemViewModel;

/**
 * Created by Simone Masini on 23/04/2016.
 */
public class WidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            List<EpisodeItemViewModel> episodes = new ArrayList<>();
            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {

                final long identityToken = Binder.clearCallingIdentity();
                episodes = DBOperation.getComingOutToday();
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                episodes = new ArrayList<>();
            }

            @Override
            public int getCount() {
                return episodes.size();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                EpisodeItemViewModel item = episodes.get(position);
                if(item==null){
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.episode_item);
                try {
                    Bitmap bmp = Picasso.with(WidgetRemoteViewsService.this).load(item.getImageUrl()).get();
                    views.setImageViewBitmap(R.id.imageview_episode, bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                views.setTextViewText(R.id.textview_episode_name, item.getSerieName());
                views.setTextViewText(R.id.textview_season_info, String.format("%s - %s", Utility.formatEpisode(item.getNumber(), item.getSeasonNumber()), item.getName()));
                views.setTextViewText(R.id.textview_date_episode, DateUtility.formatDate(item.getDate()));

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.episode_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                EpisodeItemViewModel eivm = episodes.get(position);
                if(eivm!=null){
                    return Long.parseLong(eivm.getId());
                }
                return -1;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
