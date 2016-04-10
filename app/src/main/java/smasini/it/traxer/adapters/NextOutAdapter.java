package smasini.it.traxer.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import smasini.it.traxer.R;
import smasini.it.traxer.utils.DateUtility;
import smasini.it.traxer.utils.Utility;
import smasini.it.traxer.viewmodels.EpisodeItemViewModel;

/**
 * Created by Simone Masini on 09/04/2016.
 */
public class NextOutAdapter extends BaseAdapter<EpisodeItemViewModel>{

    public NextOutAdapter(Context context, View emptyView) {
        super(context, emptyView, R.layout.episode_item);
    }

    @Override
    public void onBindCustomViewHolder(ViewHolder viewHolder, int position, EpisodeItemViewModel viewModel) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        myViewHolder.episodeName.setText(viewModel.getSerieName());
        myViewHolder.episodeDate.setText(DateUtility.formatDate(viewModel.getDate()));
        myViewHolder.episodeSeason.setText(String.format("%s - %s", Utility.formatEpisode(viewModel.getNumber(), viewModel.getSeasonNumber()), viewModel.getName()));
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

        public MyViewHolder(View view) {
            super(view);
            episodeName = (TextView) view.findViewById(R.id.textview_episode_name);
            episodeSeason = (TextView) view.findViewById(R.id.textview_season_info);
            episodeDate = (TextView) view.findViewById(R.id.textview_date_episode);
            episodeImage = (ImageView) view.findViewById(R.id.imageview_episode);
        }
    }

}
