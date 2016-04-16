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
import smasini.it.traxer.adapters.CastAdapter;
import smasini.it.traxer.app.activities.ActorDetailActivity;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.database.contract.ActorContract;
import smasini.it.traxer.database.contract.CastContract;
import smasini.it.traxer.viewmodels.CastViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CastFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{


    private final int CAST_LOADER_ID = 1;
    private CastAdapter adapter;
    private  String serieid;
    private RecyclerView recyclerView;

    public CastFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cast, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_cast);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.cast_columns), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);

        adapter = new CastAdapter(getActivity(), new BaseAdapter.OnClickHandler() {
            @Override
            public void onClick(Object model) {
                CastViewModel viewModel = (CastViewModel) model;
                Intent intent = new Intent(getActivity(), ActorDetailActivity.class);
                intent.putExtra(getString(R.string.actor_id_key), viewModel.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        serieid = getArguments().getString(getString(R.string.serie_id_key));

        return rootView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.cast_columns), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(CAST_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().restartLoader(CAST_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = CastContract.buildSeasonUri(serieid);
        return new CursorLoader(getActivity(),
                uri,
                ActorContract.COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<CastViewModel> datas = DBOperation.getCastViewModel(data);
        adapter.swapData(datas);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapData(new ArrayList<CastViewModel>());
    }
}
