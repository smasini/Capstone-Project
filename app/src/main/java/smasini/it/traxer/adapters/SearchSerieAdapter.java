package smasini.it.traxer.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import smasini.it.thetvdb.TheTVDB;
import smasini.it.thetvdb.support.Actor;
import smasini.it.thetvdb.support.Banner;
import smasini.it.thetvdb.support.Serie;
import smasini.it.thetvdb.task.callbacks.CallbackSerieData;
import smasini.it.traxer.R;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.app.Application;
import smasini.it.traxer.utils.DateUtility;
import smasini.it.traxer.utils.UIUtility;

/**
 * Project: Traxer
 * Package: smasini.it.traxer.adapters
 * Created by Simone Masini on 07/02/2016 at 11.48.
 */
public class SearchSerieAdapter extends BaseAdapter<Serie>{

    public SearchSerieAdapter(Context context, View emptyView){
        super(context, emptyView, R.layout.search_serie_item);
    }

    @Override
    public void onBindCustomViewHolder(ViewHolder viewHolder, int position, final Serie viewModel) {
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.serieName.setText(viewModel.getSeriesName());
        myViewHolder.serieName.setSelected(true);
        myViewHolder.serieDate.setText(DateUtility.formatDate(viewModel.getFirstAired()));
        myViewHolder.serieNetwotk.setText(viewModel.getNetwork());
        Picasso.with(mContext)
                .load("http://thetvdb.com/banners/posters/" + viewModel.getSeriesid() + "-1.jpg")
                .into(myViewHolder.serieImage);
        myViewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //avvio il task per tirare giù i dati della serie e la aggiungo al db
                UIUtility.showProgressDialog(mContext, R.string.label_loading);
                TheTVDB.getInstance().getSerieData(viewModel.getSeriesid(), Application.sdDirectory, new CallbackSerieData() {
                    @Override
                    public void onSerieDataLoad(Serie serie, List<Actor> actors,  List<Banner> banners) {
                        DBOperation.insertSerie(serie);
                        DBOperation.insertEpisodes(serie.getSeasons());
                        DBOperation.insertActors(actors, serie.getId());
                        DBOperation.insertBanners(banners, serie.getId());

                        myViewHolder.serieAdded.setVisibility(View.VISIBLE);
                        myViewHolder.addButton.setEnabled(false);

                        UIUtility.hideProgressDialog();
                    }
                });
            }
        });
        if(DBOperation.existSerieWithId(viewModel.getSeriesid())){
            myViewHolder.serieAdded.setVisibility(View.VISIBLE);
            myViewHolder.addButton.setEnabled(false);
        }else{
            myViewHolder.serieAdded.setVisibility(View.GONE);
            myViewHolder.addButton.setEnabled(true);
        }
        myViewHolder.serieImage.setContentDescription(mContext.getString(R.string.accessibility_serie_photo));
        myViewHolder.addButton.setContentDescription(mContext.getString(R.string.add_serie_btn));
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new MyViewHolder(view);
    }

    private class MyViewHolder extends BaseAdapter.ViewHolder {

        public TextView serieName;
        public TextView serieDate;
        public TextView serieNetwotk;
        public TextView serieAdded;
        public ImageView serieImage;
        public Button addButton;

        public MyViewHolder(View view) {
            super(view);
            serieName = (TextView) view.findViewById(R.id.textview_serie_name);
            serieDate = (TextView) view.findViewById(R.id.textview_serie_date);
            serieNetwotk = (TextView) view.findViewById(R.id.textview_serie_network);
            serieAdded = (TextView) view.findViewById(R.id.textview_already_add);
            serieImage = (ImageView) view.findViewById(R.id.imageview_serie);
            addButton = (Button) view.findViewById(R.id.button_add);
        }
    }
}
