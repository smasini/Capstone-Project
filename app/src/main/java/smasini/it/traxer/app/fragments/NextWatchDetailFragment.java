package smasini.it.traxer.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import smasini.it.traxer.R;
import smasini.it.traxer.utils.DateUtility;
import smasini.it.traxer.utils.Utility;
import smasini.it.traxer.viewmodels.EpisodeDetailViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NextWatchDetailFragment extends Fragment {


    public NextWatchDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_next_watch_detail, container, false);

        String serieid = getArguments().getString(getString(R.string.serie_id_key));

        EpisodeDetailViewModel edvm = null;//DBOperation.getDetailNextEpisodes(serieid);

        if(edvm!=null){
            ImageView photo = (ImageView) rootView.findViewById(R.id.imageview_episode_photo);
            Picasso.with(getActivity()).load(edvm.getUrlImage()).into(photo);

            ((TextView)rootView.findViewById(R.id.textview_name_episode)).setText(edvm.getName());
            ((TextView)rootView.findViewById(R.id.textview_season_episode)).setText(Utility.formatEpisode(edvm.getNumber(), edvm.getSeasonNumber()));
            ((TextView)rootView.findViewById(R.id.textview_first_aired)).setText(DateUtility.formatDate(edvm.getFirstAired()));
            ((TextView)rootView.findViewById(R.id.episode_overview)).setText(edvm.getOverview());
        }

        return rootView;
    }

}
