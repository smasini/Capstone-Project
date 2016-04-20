package smasini.it.traxer.app.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import smasini.it.traxer.R;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.utils.DateUtility;
import smasini.it.traxer.viewmodels.InfoSerieViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoDetailFragment extends Fragment {


    @Bind(R.id.info_serie_status)
    TextView status;

    @Bind(R.id.ratingbar)
    RatingBar ratingBar;

    @Bind(R.id.info_serie_network)
    TextView network;

    @Bind(R.id.info_serie_first_aired)
    TextView firstAired;

    @Bind(R.id.info_serie_time)
    TextView time;

    @Bind(R.id.info_serie_overview)
    TextView overview;

    @Bind(R.id.info_serie_genre)
    TextView genre;

    @Bind(R.id.info_serie_rating)
    TextView rating;

    @Bind(R.id.info_serie_tot_episode)
    TextView totEp;

    @Bind(R.id.info_serie_tot_season)
    TextView totSeason;

    public InfoDetailFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_info_detail, container, false);
        ButterKnife.bind(this, rootView);
        String serieid = getArguments().getString(getString(R.string.serie_id_key));
        InfoSerieViewModel isvm = DBOperation.getInfoSerie(serieid);

        status.setText(isvm.getStatus());
        if(isvm.getStatus().equals("Continuing")){
            status.setTextColor(Color.GREEN);
        }else if(isvm.getStatus().equals("Ended")){
            status.setTextColor(Color.RED);
        }

        network.setText(isvm.getNetwork());
        firstAired.setText(DateUtility.formatDate(isvm.getFirstAired()));
        time.setText(isvm.getTime());
        overview.setText(isvm.getOverview());
        genre.setText(isvm.getGenre());
        rating.setText(String.format("%.1f/10", isvm.getRate()));
        totEp.setText(String.format("%d", isvm.getEpisodes()));
        totSeason.setText(String.format("%d", isvm.getSeasons()));


        ratingBar.setRating((float)isvm.getRate());
        ratingBar.setContentDescription(String.format(getString(R.string.accessibility_rating), isvm.getRate()));
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
