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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import smasini.it.traxer.R;
import smasini.it.traxer.adapters.DetailFragmentPagerAdapter;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.viewmodels.DetailSerieViewModel;

public class DetailSerieActivity extends AppCompatActivity {

    private String serieID = "";
    private DetailSerieViewModel dsvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_serie);

        serieID = getIntent().getStringExtra(getString(R.string.serie_id_key));

        dsvm = DBOperation.getSerieDetails(serieID);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        ImageView banner = (ImageView) findViewById(R.id.banner);
        Picasso.with(this).load(dsvm.getBannerUrl()).into(banner);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if(viewPager!=null)
            viewPager.setAdapter(new DetailFragmentPagerAdapter(getSupportFragmentManager(), DetailSerieActivity.this, serieID));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        if(tabLayout!=null)
            tabLayout.setupWithViewPager(viewPager);
        changeColor();
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
                DBOperation.updateAllWatch(serieID, true);
                Snackbar.make(findViewById(android.R.id.content), R.string.snackbar_all_watch, Snackbar.LENGTH_LONG).show();
                return true;
            case R.id.action_all_unwatch:
                DBOperation.updateAllWatch(serieID, false);
                Snackbar.make(findViewById(android.R.id.content), R.string.snackbar_all_unwatch, Snackbar.LENGTH_LONG).show();
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

                AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
                if(appBarLayout!=null)
                    appBarLayout.setBackgroundColor(color);

                Window window = getWindow();
                // clear FLAG_TRANSLUCENT_STATUS flag:
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colordark);

                TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
                if(tabLayout!=null)
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
