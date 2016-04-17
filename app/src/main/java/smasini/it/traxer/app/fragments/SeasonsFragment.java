package smasini.it.traxer.app.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import smasini.it.traxer.R;
import smasini.it.traxer.adapters.BaseAdapter;
import smasini.it.traxer.adapters.SeasonAdapter;
import smasini.it.traxer.app.activities.EpisodesActivity;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.database.contract.EpisodeContract;
import smasini.it.traxer.enums.UriQueryType;
import smasini.it.traxer.viewmodels.SeasonViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeasonsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final int SEASONS_LOADER_ID = 2;
    private SeasonAdapter adapter;
    private  String serieid;
    private RecyclerView recyclerView;

    public SeasonsFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_seasons, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_seasons);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.season_column), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);

        adapter = new SeasonAdapter(getActivity(), new BaseAdapter.OnClickHandler() {
            @Override
            public void onClick(Object model) {
                SeasonViewModel viewModel = (SeasonViewModel) model;
                Intent intent = new Intent(getActivity(), EpisodesActivity.class);
                intent.putExtra(getString(R.string.serie_id_key), serieid);
                intent.putExtra(getString(R.string.url_season_key), viewModel.getImageUrl());
                intent.putExtra(getString(R.string.season_number_key), viewModel.getNumber());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        serieid = getArguments().getString(getString(R.string.serie_id_key));
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(SEASONS_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().restartLoader(SEASONS_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = EpisodeContract.buildUri(UriQueryType.SEASONS_BY_SERIE, new String[]{serieid});
        return new CursorLoader(getActivity(),
                uri,
                EpisodeContract.getSeasonProjection(),
                null,
                null,
                EpisodeContract.COL_SEASON_NUMBER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<SeasonViewModel> datas = DBOperation.getSeasonViewModel(data);
        adapter.swapData(datas);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapData(new ArrayList<SeasonViewModel>());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.season_column), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
    }
}
