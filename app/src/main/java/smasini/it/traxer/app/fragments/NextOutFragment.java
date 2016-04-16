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
import smasini.it.traxer.adapters.NextOutAdapter;
import smasini.it.traxer.app.activities.EpisodeDetailActivity;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.database.contract.EpisodeContract;
import smasini.it.traxer.viewmodels.EpisodeItemViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NextOutFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private final int EPISODES_LOADER_ID = 1;
    private NextOutAdapter adapter;
    private boolean showNext;
    private RecyclerView recyclerView;

    public NextOutFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragmentext_out, container, false);

        showNext = getArguments().getBoolean(getString(R.string.next_key), false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_episodes);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.episode_columns), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
        View emptyView = rootView.findViewById(R.id.recyclerview_episodes_empty);
        adapter = new NextOutAdapter(getActivity(), emptyView, new BaseAdapter.OnClickHandler() {
            @Override
            public void onClick(Object model) {
                EpisodeItemViewModel vm = (EpisodeItemViewModel) model;
                Intent intent = new Intent(getActivity(), EpisodeDetailActivity.class);
                intent.putExtra(getString(R.string.episode_id_key), vm.getId());
                startActivity(intent);
            }
        });
        adapter.setShowNext(showNext);
        recyclerView.setAdapter(adapter);

        return rootView;
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
        String sortOrder;
        Uri uri;
        if(showNext){
            uri = EpisodeContract.buildNextOutUri();
            sortOrder = EpisodeContract.TABLE_NAME + "." + EpisodeContract.COL_FIRST_AIRED + " ASC";
        }else{
            uri = EpisodeContract.buildMissUri();
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
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapData(new ArrayList<EpisodeItemViewModel>());
    }
}
