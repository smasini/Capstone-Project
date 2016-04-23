package smasini.it.traxer.app.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.Bind;
import butterknife.ButterKnife;
import smasini.it.traxer.R;
import smasini.it.traxer.adapters.DetailFragmentPagerAdapter;
import smasini.it.traxer.app.Application;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.utils.UIUtility;
import smasini.it.traxer.viewmodels.DetailSerieViewModel;

public class DetailSerieActivity extends AppCompatActivity {

    private String serieID = "";
    private DetailSerieViewModel dsvm;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.banner)
    ImageView banner;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;

    @Bind(R.id.appbar_layout)
    AppBarLayout appBarLayout;

    @Bind(android.R.id.content)
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_serie);

        ButterKnife.bind(this);

        serieID = getIntent().getStringExtra(getString(R.string.serie_id_key));
        dsvm = DBOperation.getSerieDetails(serieID);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        Picasso.with(this).load(dsvm.getBannerUrl()).into(banner);

        viewPager.setAdapter(new DetailFragmentPagerAdapter(getSupportFragmentManager(), DetailSerieActivity.this, serieID));

        tabLayout.setupWithViewPager(viewPager);
        changeColor();

        Application application = (Application) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("SerieOpen")
                .setAction(serieID)
                .build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.serie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_all_watch:
                UIUtility.showProgressDialog(this, R.string.label_loading);
                DBOperation.updateAllWatch(serieID, true);
                Snackbar.make(rootView, R.string.snackbar_all_watch, Snackbar.LENGTH_LONG).show();
                UIUtility.hideProgressDialog();
                return true;
            case R.id.action_all_unwatch:
                UIUtility.showProgressDialog(this, R.string.label_loading);
                DBOperation.updateAllWatch(serieID, false);
                Snackbar.make(rootView, R.string.snackbar_all_unwatch, Snackbar.LENGTH_LONG).show();
                UIUtility.hideProgressDialog();
                return true;
            case R.id.action_delete:
                DBOperation.deleteSerie(serieID);
                onBackPressed();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void changeColor(){
        Picasso.with(this).load(dsvm.getBannerUrl()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Palette p = Palette.from(bitmap).generate();
                int color = p.getMutedColor(Color.WHITE);
                int colordark = p.getDarkMutedColor(Color.WHITE);
                int colorAccent = p.getLightMutedColor(Color.WHITE);
                int colorLightVibrant = p.getLightVibrantColor(Color.WHITE);

                appBarLayout.setBackgroundColor(color);

                Window window = getWindow();
                // clear FLAG_TRANSLUCENT_STATUS flag:
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colordark);

                tabLayout.setTabTextColors(colorAccent, colorLightVibrant);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }
}
