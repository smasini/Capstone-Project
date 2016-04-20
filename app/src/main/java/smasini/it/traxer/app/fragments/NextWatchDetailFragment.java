package smasini.it.traxer.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import smasini.it.traxer.R;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.utils.DateUtility;
import smasini.it.traxer.utils.Utility;
import smasini.it.traxer.viewmodels.EpisodeDetailViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NextWatchDetailFragment extends Fragment {

    private String serieid;
    private EpisodeDetailViewModel edvm;

    @Bind(R.id.imageview_episode_photo)
    ImageView photo;

    @Bind(R.id.textview_name_episode)
    TextView nameEpisode;
    @Bind(R.id.textview_season_episode)
    TextView seasonEpisode;
    @Bind(R.id.textview_first_aired)
    TextView firstAired;
    @Bind(R.id.episode_overview)
    TextView overview;
    @Bind(R.id.empty)
    View emptyView;
    @Bind(R.id.scrollView)
    View scrollView;


    public NextWatchDetailFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_next_watch_detail, container, false);
        ButterKnife.bind(this, rootView);
        serieid = getArguments().getString(getString(R.string.serie_id_key));
        reloadData();
        return rootView;
    }

    @OnClick(R.id.fab_watch)
    public void click(){
        DBOperation.updateWatch(edvm.getEpisodeID(), true);
        reloadData();
    }

    private void reloadData(){
        edvm = DBOperation.getDetailNextEpisodes(serieid);
        loadData();
    }

    private void loadData(){
        if(edvm!=null){

            Picasso.with(getActivity()).load(edvm.getUrlImage()).into(photo);
            nameEpisode.setText(edvm.getName());
            seasonEpisode.setText(Utility.formatEpisode(edvm.getNumber(), edvm.getSeasonNumber()));
            firstAired.setText(DateUtility.formatDate(edvm.getFirstAired()));
            overview.setText(edvm.getOverview());
            emptyView.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }else {
            emptyView.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
