package smasini.it.traxer.app.activities;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import smasini.it.traxer.R;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.utils.DateUtility;
import smasini.it.traxer.utils.Utility;
import smasini.it.traxer.viewmodels.EpisodeDetailViewModel;

public class EpisodeDetailActivity extends AppCompatActivity {

    private String episodeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_detail);

        episodeid = getIntent().getStringExtra(getString(R.string.episode_id_key));

        EpisodeDetailViewModel edvm = DBOperation.getDetailEpisodes(episodeid);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        if(collapsingToolbarLayout!=null){
            collapsingToolbarLayout.setTitle(edvm.getName());
        }

        ImageView photo = (ImageView) findViewById(R.id.photo_action_bar);
        Picasso.with(this).load(edvm.getUrlImage()).into(photo);

        Picasso.with(this).load(edvm.getUrlImage()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Palette p = Palette.from(bitmap).generate();
                int color = p.getMutedColor(Color.WHITE);
                int colordark = p.getDarkMutedColor(Color.WHITE);
                int colorLightVibrant = p.getLightVibrantColor(Color.WHITE);

                CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                if(collapsingToolbarLayout!=null){
                    collapsingToolbarLayout.setContentScrimColor(color);
                    collapsingToolbarLayout.setStatusBarScrimColor(colordark);
                    collapsingToolbarLayout.setCollapsedTitleTextColor(colorLightVibrant);
                    collapsingToolbarLayout.setExpandedTitleColor(colorLightVibrant);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        ((TextView)findViewById(R.id.textview_name_episode)).setText(edvm.getName());
        ((TextView)findViewById(R.id.textview_season_episode)).setText(Utility.formatEpisode(edvm.getNumber(), edvm.getSeasonNumber()));
        ((TextView)findViewById(R.id.textview_first_aired)).setText(DateUtility.formatDate(edvm.getFirstAired()));
        ((TextView)findViewById(R.id.episode_overview)).setText(edvm.getOverview());

        ((TextView)findViewById(R.id.episode_rating)).setText(String.format("%.1f/10", edvm.getRating()));

        checkWatch(edvm.isWatch());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_watch);
        if(fab != null){
            fab.setTag(edvm.isWatch());
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean newWatch;
                    int stringres;
                    if((boolean)v.getTag()){
                        newWatch = false;
                        stringres = R.string.snap_episode_unwatch;
                    }else{
                        newWatch = true;
                        stringres = R.string.snap_episode_watch;
                    }
                    DBOperation.updateWatch(episodeid, newWatch);
                    v.setTag(newWatch);
                    checkWatch(newWatch);
                    Snackbar.make(findViewById(android.R.id.content), stringres, Snackbar.LENGTH_LONG).show();
                }
            });
        }
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        ratingBar.setRating((float) edvm.getRating());
    }

    private void checkWatch(boolean isWatch){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_watch);
        if(fab!=null) {
            if (isWatch) {
                fab.setBackgroundTintList(ColorStateList.valueOf(Utility.getColor(R.color.green_500)));
            } else {
                fab.setBackgroundTintList(ColorStateList.valueOf(Utility.getColor(R.color.grey_600)));
            }
        }
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
