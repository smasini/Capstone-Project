package smasini.it.traxer.app.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import smasini.it.traxer.R;
import smasini.it.traxer.adapters.BaseAdapter;
import smasini.it.traxer.adapters.EpisodesAdapter;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.database.contract.EpisodeContract;
import smasini.it.traxer.enums.UriQueryType;
import smasini.it.traxer.utils.UIUtility;
import smasini.it.traxer.utils.Utility;
import smasini.it.traxer.viewmodels.EpisodeItemViewModel;

public class EpisodesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private String serieid;
    private int seasonNumber;
    private EpisodesAdapter adapter;
    private final int EPISODE_LOADER_ID = 1;

    @Bind(R.id.photo_action_bar)
    ImageView photo;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recyclerview_empty)
    View emptyView;

    @Bind(R.id.recyclerview_episodes)
    RecyclerView recyclerView;

    @Bind(android.R.id.content)
    View mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes_activity);

        ButterKnife.bind(this);

        serieid = getIntent().getStringExtra(getString(R.string.serie_id_key));
        String urlSeasonImage = getIntent().getStringExtra(getString(R.string.url_season_key));
        seasonNumber = getIntent().getIntExtra(getString(R.string.season_number_key), -1);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        collapsingToolbarLayout.setTitle(Utility.formatSeasonName(seasonNumber));


        Picasso.with(this).load(urlSeasonImage).into(photo);

        Picasso.with(this).load(urlSeasonImage).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Palette p = Palette.from(bitmap).generate();
                int color = p.getMutedColor(Color.WHITE);
                int colordark = p.getDarkMutedColor(Color.WHITE);
                int colorLightVibrant = p.getLightVibrantColor(Color.WHITE);

                collapsingToolbarLayout.setContentScrimColor(color);
                collapsingToolbarLayout.setStatusBarScrimColor(colordark);
                collapsingToolbarLayout.setCollapsedTitleTextColor(colorLightVibrant);
                collapsingToolbarLayout.setExpandedTitleColor(colorLightVibrant);

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.episode_columns), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
        adapter = new EpisodesAdapter(this, emptyView, new BaseAdapter.OnClickHandler() {
            @Override
            public void onClick(Object model) {
                EpisodeItemViewModel eivm = (EpisodeItemViewModel) model;
                Intent intent = new Intent(EpisodesActivity.this, EpisodeDetailActivity.class);
                intent.putExtra(getString(R.string.episode_id_key), eivm.getId());
                intent.putExtra(getString(R.string.serie_id_key), serieid);
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(EpisodesActivity.this, eivm.getSharedImageView(), eivm.getSharedImageView().getTransitionName()).toBundle();
                ActivityCompat.startActivity(EpisodesActivity.this, intent, bundle);
            }
        });
        recyclerView.setAdapter(adapter);

        getSupportLoaderManager().initLoader(EPISODE_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.episodes_activity_menu, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportLoaderManager().restartLoader(EPISODE_LOADER_ID, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_all_watch:
                UIUtility.showProgressDialog(this, R.string.label_loading);
                DBOperation.updateAllWatch(serieid, seasonNumber, true);
                Snackbar.make(mainContainer, R.string.snackbar_all_watch, Snackbar.LENGTH_LONG).show();
                UIUtility.hideProgressDialog();
                getSupportLoaderManager().restartLoader(EPISODE_LOADER_ID, null, this);
                return true;
            case R.id.action_all_unwatch:
                UIUtility.showProgressDialog(this, R.string.label_loading);
                DBOperation.updateAllWatch(serieid, seasonNumber, false);
                Snackbar.make(mainContainer, R.string.snackbar_all_unwatch, Snackbar.LENGTH_LONG).show();
                UIUtility.hideProgressDialog();
                getSupportLoaderManager().restartLoader(EPISODE_LOADER_ID, null, this);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.episode_columns), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        UIUtility.showProgressDialog(this, R.string.label_loading);
        String sortOrder = EpisodeContract.COL_NUMBER + " ASC";
        Uri uri = EpisodeContract.buildUri(UriQueryType.EPISODES_BY_SERIE_AND_SEASON, new String[]{serieid, ""+seasonNumber});
        return new CursorLoader(this,
                uri,
                EpisodeContract.COLUMNS_WHIT_SERIE,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<EpisodeItemViewModel> episodes = DBOperation.getEpisodes(data, true);
        adapter.swapData(episodes);
        UIUtility.hideProgressDialog();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapData(new ArrayList<EpisodeItemViewModel>());
    }
}
