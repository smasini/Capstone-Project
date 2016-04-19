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
 * Created by Simone Masini on 09/04/2016.
 */
public class NextOutAdapter extends BaseAdapter<EpisodeItemViewModel>{

    private boolean showNext;

    public NextOutAdapter(Context context, View emptyView, OnClickHandler clickHandler) {
        super(context, emptyView, clickHandler, R.layout.episode_item);
    }

    @Override
    public void onBindCustomViewHolder(ViewHolder viewHolder, int position, final EpisodeItemViewModel viewModel) {
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        myViewHolder.episodeName.setText(viewModel.getSerieName());
        myViewHolder.episodeDate.setText(DateUtility.formatDate(viewModel.getDate()));
        myViewHolder.episodeSeason.setText(String.format("%s - %s", Utility.formatEpisode(viewModel.getNumber(), viewModel.getSeasonNumber()), viewModel.getName()));
        if(!showNext){
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
                    myViewHolder.episodeWatch.setTag(newWatch);
                    DBOperation.updateWatch(viewModel.getId(), newWatch);
                }
            });
        }else {
            myViewHolder.episodeWatch.setVisibility(View.GONE);
        }
        Picasso.with(mContext)
                .load(viewModel.getImageUrl())
                .into(myViewHolder.episodeImage);
        myViewHolder.episodeImage.setContentDescription(mContext.getString(R.string.accessibility_episode_photo));
        myViewHolder.episodeWatch.setContentDescription(mContext.getString(R.string.accessibillity_watch_unwatch));
    }
    @Override
    public ViewHolder getViewHolder(View view) {
        return new MyViewHolder(view);
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
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
