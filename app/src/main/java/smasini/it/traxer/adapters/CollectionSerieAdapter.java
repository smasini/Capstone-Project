package smasini.it.traxer.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import smasini.it.traxer.R;
import smasini.it.traxer.viewmodels.SerieCollectionViewModel;

/**
 * Project: Traxer
 * Package: smasini.it.traxer.adapters
 * Created by Simone Masini on 14/02/2016 at 10.48.
 */
public class CollectionSerieAdapter extends BaseAdapter<SerieCollectionViewModel> {

    public CollectionSerieAdapter(Context context, View emptyView, OnClickHandler handler) {
        super(context, emptyView, handler, R.layout.collection_serie_item);
    }

    @Override
    public void onBindCustomViewHolder(ViewHolder viewHolder, int position, SerieCollectionViewModel viewModel) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.serieName.setText(viewModel.getName());
        myViewHolder.serieName.setSelected(true);
        Picasso.with(mContext)
                .load(viewModel.getImageUrl())
                .into(myViewHolder.serieImage);
        myViewHolder.serieImage.setContentDescription(mContext.getString(R.string.accessibility_serie_photo));
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends ViewHolder{

        @Bind(R.id.textview_serie_name)
        public TextView serieName;
        @Bind(R.id.imageview_serie)
        public ImageView serieImage;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
