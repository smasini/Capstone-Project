package smasini.it.traxer.app.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import smasini.it.traxer.R;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.utils.DateUtility;
import smasini.it.traxer.utils.Utility;
import smasini.it.traxer.viewmodels.EpisodeDetailViewModel;

public class EpisodeDetailActivity extends AppCompatActivity {

    private String episodeid;
    private String shareText = "";

    @Bind(R.id.photo_action_bar)
    ImageView photo;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.textview_name_episode)
    TextView episodeName;

    @Bind(R.id.textview_season_episode)
    TextView episodeSeason;

    @Bind(R.id.textview_first_aired)
    TextView firstAired;

    @Bind(R.id.episode_rating)
    TextView episodeRate;

    @Bind(R.id.episode_overview)
    TextView overview;

    @Bind(R.id.fab_watch)
    FloatingActionButton fab;

    @Bind(R.id.ratingbar)
    RatingBar ratingBar;

    @Bind(android.R.id.content)
    View mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_detail);

        ButterKnife.bind(this);

        episodeid = getIntent().getStringExtra(getString(R.string.episode_id_key));
        String seriedid = getIntent().getStringExtra(getString(R.string.serie_id_key));
        EpisodeDetailViewModel edvm = DBOperation.getDetailEpisodes(episodeid, seriedid);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        collapsingToolbarLayout.setTitle(edvm.getName());

        Picasso.with(this).load(edvm.getUrlImage()).into(photo);
        Picasso.with(this).load(edvm.getUrlImage()).into(new Target() {
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

        shareText = String.format("%s %s\n%s", edvm.getSerieName(), Utility.formatEpisode(edvm.getNumber(), edvm.getSeasonNumber()), edvm.getOverview());

        episodeName.setText(edvm.getName());
        episodeSeason.setText(Utility.formatEpisode(edvm.getNumber(), edvm.getSeasonNumber()));
        firstAired.setText(DateUtility.formatDate(edvm.getFirstAired()));
        overview.setText(edvm.getOverview());
        episodeRate.setText(String.format("%.1f/10", edvm.getRating()));
        checkWatch(edvm.isWatch());
        fab.setTag(edvm.isWatch());
        ratingBar.setRating((float) edvm.getRating());
        ratingBar.setContentDescription(String.format(getString(R.string.accessibility_rating), edvm.getRating()));
    }

    @OnClick(R.id.fab_watch)
    public void click(View view){
        boolean newWatch;
        int stringres;
        if((boolean)view.getTag()){
            newWatch = false;
            stringres = R.string.snap_episode_unwatch;
        }else{
            newWatch = true;
            stringres = R.string.snap_episode_watch;
        }
        DBOperation.updateWatch(episodeid, newWatch);
        view.setTag(newWatch);
        checkWatch(newWatch);
        Snackbar.make(mainContainer, stringres, Snackbar.LENGTH_LONG).show();
    }

    private void checkWatch(boolean isWatch){
        if (isWatch) {
            fab.setBackgroundTintList(ColorStateList.valueOf(Utility.getColor(R.color.green_500)));
        } else {
            fab.setBackgroundTintList(ColorStateList.valueOf(Utility.getColor(R.color.grey_600)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        menuItem.setIntent(shareIntent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
