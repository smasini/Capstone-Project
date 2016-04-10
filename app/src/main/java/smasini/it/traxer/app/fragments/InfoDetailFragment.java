package smasini.it.traxer.app.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import smasini.it.traxer.R;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.utils.DateUtility;
import smasini.it.traxer.viewmodels.InfoSerieViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoDetailFragment extends Fragment {


    public InfoDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_info_detail, container, false);

        String serieid = getArguments().getString(getString(R.string.serie_id_key));
        InfoSerieViewModel isvm = DBOperation.getInfoSerie(serieid);

        TextView status = (TextView)rootView.findViewById(R.id.info_serie_status);
        status.setText(isvm.getStatus());
        if(isvm.getStatus().equals("Continuing")){
            status.setTextColor(Color.GREEN);
        }else if(isvm.getStatus().equals("Ended")){
            status.setTextColor(Color.RED);
        }

        ((TextView)rootView.findViewById(R.id.info_serie_network)).setText(isvm.getNetwork());
        ((TextView)rootView.findViewById(R.id.info_serie_first_aired)).setText(DateUtility.formatDate(isvm.getFirstAired()));
        ((TextView)rootView.findViewById(R.id.info_serie_time)).setText(isvm.getTime());
        ((TextView)rootView.findViewById(R.id.info_serie_overview)).setText(isvm.getOverview());
        ((TextView)rootView.findViewById(R.id.info_serie_genre)).setText(isvm.getGenre());
        ((TextView)rootView.findViewById(R.id.info_serie_rating)).setText(String.format("%.1f/10", isvm.getRate()));
        ((TextView)rootView.findViewById(R.id.info_serie_tot_episode)).setText(String.format("%d", isvm.getEpisodes()));
        ((TextView)rootView.findViewById(R.id.info_serie_tot_season)).setText(String.format("%d", isvm.getSeasons()));

        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.ratingbar);
        ratingBar.setRating((float)isvm.getRate());
        return rootView;
    }

}
