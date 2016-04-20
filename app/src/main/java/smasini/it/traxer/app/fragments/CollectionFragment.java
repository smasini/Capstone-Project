package smasini.it.traxer.app.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import smasini.it.traxer.R;
import smasini.it.traxer.adapters.BaseAdapter;
import smasini.it.traxer.adapters.CollectionSerieAdapter;
import smasini.it.traxer.app.activities.DetailSerieActivity;
import smasini.it.traxer.app.activities.SearchActivity;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.database.contract.SerieContract;
import smasini.it.traxer.utils.UIUtility;
import smasini.it.traxer.viewmodels.SerieCollectionViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private final int COLLECTION_LOADER_ID = 1;
    private CollectionSerieAdapter adapter;

    @Bind(R.id.recyclerview_collection_serie)
    RecyclerView recyclerView;

    @Bind(R.id.recyclerview_collection_empty)
    View emptyView;

    public CollectionFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_collection, container, false);

        ButterKnife.bind(this, rootView);

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.collection_column), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
        adapter = new CollectionSerieAdapter(getActivity(), emptyView, new BaseAdapter.OnClickHandler() {
            @Override
            public void onClick(Object model) {
                SerieCollectionViewModel viewModel = (SerieCollectionViewModel) model;
                Intent intent = new Intent(getActivity(), DetailSerieActivity.class);
                intent.putExtra(getString(R.string.serie_id_key), viewModel.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @OnClick(R.id.fab_add)
    public void click(){
        startActivity(new Intent(getActivity(), SearchActivity.class));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.collection_column), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(COLLECTION_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().restartLoader(COLLECTION_LOADER_ID, null, this);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        UIUtility.showProgressDialog(getActivity(), R.string.label_loading);
        String sortOrder = SerieContract.COL_NAME + " ASC";
        Uri serieUri = SerieContract.CONTENT_URI;
        return new CursorLoader(getActivity(),
                serieUri,
                SerieContract.COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        List<SerieCollectionViewModel> datas = DBOperation.getSerieViewModel(data);
        adapter.swapData(datas);
        UIUtility.hideProgressDialog();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        adapter.swapData(new ArrayList<SerieCollectionViewModel>());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
