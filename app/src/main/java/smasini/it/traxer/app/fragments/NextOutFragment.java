package smasini.it.traxer.app.fragments;


import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import smasini.it.traxer.R;
import smasini.it.traxer.adapters.BaseAdapter;
import smasini.it.traxer.adapters.NextOutAdapter;
import smasini.it.traxer.app.activities.EpisodeDetailActivity;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.database.contract.EpisodeContract;
import smasini.it.traxer.enums.UriQueryType;
import smasini.it.traxer.utils.UIUtility;
import smasini.it.traxer.viewmodels.EpisodeItemViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NextOutFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private final int EPISODES_LOADER_ID = 1;
    private NextOutAdapter adapter;
    private boolean showNext;

    @Bind(R.id.recyclerview_episodes)
    RecyclerView recyclerView;
    @Bind(R.id.recyclerview_episodes_empty)
    View emptyView;

    public NextOutFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_next_out, container, false);

        ButterKnife.bind(this, rootView);
        showNext = getArguments().getBoolean(getString(R.string.next_key), false);

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.episode_columns), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
        adapter = new NextOutAdapter(getActivity(), emptyView, new BaseAdapter.OnClickHandler() {
            @Override
            public void onClick(Object model) {
                EpisodeItemViewModel vm = (EpisodeItemViewModel) model;
                Intent intent = new Intent(getActivity(), EpisodeDetailActivity.class);
                intent.putExtra(getString(R.string.episode_id_key), vm.getId());
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle();
                ActivityCompat.startActivity(getActivity(), intent, bundle);
            }
        });
        adapter.setShowNext(showNext);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.episode_columns), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(EPISODES_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().restartLoader(EPISODES_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        UIUtility.showProgressDialog(getActivity(), R.string.label_loading);
        String sortOrder;
        Uri uri;
        if(showNext){
            uri = EpisodeContract.buildUri(UriQueryType.EPISODE_NEXT_OUT);
            sortOrder = EpisodeContract.TABLE_NAME + "." + EpisodeContract.COL_FIRST_AIRED + " ASC";
        }else{
            uri = EpisodeContract.buildUri(UriQueryType.EPISODE_MISS);
            sortOrder = EpisodeContract.TABLE_NAME + "." + EpisodeContract.COL_FIRST_AIRED + " DESC";
        }
        return new CursorLoader(getActivity(),
                uri,
                EpisodeContract.COLUMNS_WHIT_SERIE,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<EpisodeItemViewModel> episodes = DBOperation.getEpisodes(data, false);
        adapter.swapData(episodes);
        UIUtility.hideProgressDialog();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapData(new ArrayList<EpisodeItemViewModel>());
    }
}
