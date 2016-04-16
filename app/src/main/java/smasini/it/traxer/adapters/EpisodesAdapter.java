package smasini.it.traxer.adapters;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import smasini.it.traxer.R;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.utils.DateUtility;
import smasini.it.traxer.utils.Utility;
import smasini.it.traxer.viewmodels.EpisodeItemViewModel;

/**
 * Created by Simone Masini on 10/04/2016.
 */
public class EpisodesAdapter extends BaseAdapter<EpisodeItemViewModel> {

    public EpisodesAdapter(Context context, View emptyView, OnClickHandler listener) {
        super(context, emptyView, listener, R.layout.episode_season_item);
    }

    @Override
    public void onBindCustomViewHolder(ViewHolder viewHolder, int position, final EpisodeItemViewModel viewModel) {
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        myViewHolder.episodeName.setText(viewModel.getName());
        myViewHolder.episodeDate.setText(DateUtility.formatDate(viewModel.getDate()));
        myViewHolder.episodeSeason.setText(Utility.formatEpisode(viewModel.getNumber(), viewModel.getSeasonNumber()));
        if(viewModel.isWatch()){
            myViewHolder.episodeWatch.setBackgroundColor(Utility.getColor(R.color.green_500));
        }else{
            myViewHolder.episodeWatch.setBackgroundColor(Utility.getColor(R.color.grey_500));
        }
        myViewHolder.episodeWatch.setTag(viewModel.isWatch());
        myViewHolder.episodeWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newWatch = !(boolean)v.getTag();
                if(newWatch){
                    myViewHolder.episodeWatch.setBackgroundColor(Utility.getColor(R.color.green_500));
                }else{
                    myViewHolder.episodeWatch.setBackgroundColor(Utility.getColor(R.color.grey_500));
                }
                DBOperation.updateWatch(viewModel.getId(), newWatch);
                myViewHolder.episodeWatch.setTag(newWatch);
            }
        });
        Picasso.with(mContext)
                .load(viewModel.getImageUrl())
                .into(myViewHolder.episodeImage);
    }
    @Override
    public ViewHolder getViewHolder(View view) {
        return new MyViewHolder(view);
    }

    private class MyViewHolder extends ViewHolder{

        public final TextView episodeName;
        public final TextView episodeDate;
        public final TextView episodeSeason;
        public final ImageView episodeImage;
        public final FrameLayout episodeWatch;

        public MyViewHolder(View view) {
            super(view);
            episodeName = (TextView) view.findViewById(R.id.textview_episode_name);
            episodeSeason = (TextView) view.findViewById(R.id.textview_season_info);
            episodeDate = (TextView) view.findViewById(R.id.textview_date_episode);
            episodeImage = (ImageView) view.findViewById(R.id.imageview_episode);
            episodeWatch = (FrameLayout) view.findViewById(R.id.btn_watch);
        }
    }
}