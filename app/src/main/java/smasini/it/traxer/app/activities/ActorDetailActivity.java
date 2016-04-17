package smasini.it.traxer.app.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import smasini.it.traxer.R;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.viewmodels.ActorDetailViewModel;

public class ActorDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_detail);

        String actorid = getIntent().getStringExtra(getString(R.string.actor_id_key));

        ActorDetailViewModel advm = DBOperation.getActorDetailViewModel(actorid);

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
            collapsingToolbarLayout.setTitle(advm.getName());
        }

        WebView webView = (WebView) findViewById(R.id.webview_bio);
        if(webView!=null){
            String html = "<html><head><style type='text/css'>body{color:#FFFFFF;}</style></head><body>" + advm.getBioHTML() +"</body></html>";
            webView.setBackgroundColor(Color.TRANSPARENT);
            webView.loadData(html, "text/html", "UTF-8");
        }

        ImageView photo = (ImageView) findViewById(R.id.photo_action_bar);
        Picasso.with(this).load(advm.getUrlImage()).into(photo);

        Picasso.with(this).load(advm.getUrlImage()).into(new Target() {
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
