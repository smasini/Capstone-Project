package smasini.it.traxer.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import smasini.it.traxer.R;
import smasini.it.traxer.utils.Utility;
import smasini.it.traxer.viewmodels.SeasonViewModel;

/**
 * Created by Simone Masini on 03/04/2016.
 */
public class SeasonAdapter extends BaseAdapter<SeasonViewModel> {

    public SeasonAdapter(Context context, OnClickHandler handler) {
        super(context, handler, R.layout.season_item);
    }

    @Override
    public void onBindCustomViewHolder(ViewHolder viewHolder, int position, SeasonViewModel viewModel) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        myViewHolder.seasonName.setText(Utility.formatSeasonName(viewModel.getNumber()));
        myViewHolder.seasonName.setSelected(true);
        myViewHolder.progressBar.setMax(viewModel.getTotalEpisodes());
        myViewHolder.progressBar.setProgress(viewModel.getTotalEpisodeWatched());
        int perc = viewModel.getTotalEpisodeWatched() == 0 ? 0 : viewModel.getTotalEpisodeWatched() * 100 / viewModel.getTotalEpisodes();
        myViewHolder.seasonPercent.setText(String.format("%d", perc));
        if(viewModel.getImageUrl() != null && !viewModel.getImageUrl().equals("")){
            Picasso.with(mContext)
                    .load(viewModel.getImageUrl())
                    .into(myViewHolder.seasonImage);
        }
        myViewHolder.seasonImage.setContentDescription(mContext.getString(R.string.accessibility_season_photo));
        myViewHolder.progressBar.setContentDescription(String.format(mContext.getString(R.string.accessibility_progress), perc));
        viewModel.setSharedImageView(myViewHolder.seasonImage);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends ViewHolder{

        @Bind(R.id.textview_season_name)
        public TextView seasonName;
        @Bind(R.id.imageview_season)
        public ImageView seasonImage;
        @Bind(R.id.textview_percent)
        public TextView seasonPercent;
        @Bind(R.id.progressbar)
        public ProgressBar progressBar;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
