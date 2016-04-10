package smasini.it.traxer.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        if(viewModel.getImageUrl() != null && !viewModel.getImageUrl().equals("")){
            Picasso.with(mContext)
                    .load(viewModel.getImageUrl())
                    .into(myViewHolder.seasonImage);
        }
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new MyViewHolder(view);
    }

    private class MyViewHolder extends ViewHolder{

        public final TextView seasonName;
        public final ImageView seasonImage;

        public MyViewHolder(View view) {
            super(view);
            seasonName = (TextView) view.findViewById(R.id.textview_season_name);
            seasonImage = (ImageView) view.findViewById(R.id.imageview_season);
        }
    }
}
