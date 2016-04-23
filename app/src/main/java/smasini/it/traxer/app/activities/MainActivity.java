package smasini.it.traxer.app.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import smasini.it.traxer.R;
import smasini.it.traxer.app.Application;
import smasini.it.traxer.app.fragments.CollectionFragment;
import smasini.it.traxer.app.fragments.NextOutFragment;
import smasini.it.traxer.app.fragments.SettingsFragment;
import smasini.it.traxer.app.fragments.StatisticFragment;
import smasini.it.traxer.utils.UIUtility;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final int MY_PERMISSIONS_WRITE_EXTERNAL = 0;


    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        boolean isFromNotification = getIntent().getBooleanExtra(Application.getStaticApplicationContext().getString(R.string.main_activity_notificaiton_key), false);

        if(isFromNotification){
            createAndInsertFragment("missing_fragment", R.string.missing_nav_label);
        }else {
            createAndInsertFragment("collection_fragment", R.string.collection_nav_label);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment f = getSupportFragmentManager().findFragmentByTag("collection_fragment");
            if(f == null || !f.isAdded()){
                createAndInsertFragment("collection_fragment", R.string.collection_nav_label);
            }else{
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        int titleRes = -1;
        String tag = "";
        if (id == R.id.nav_collection) {
            tag = "collection_fragment";
            titleRes = R.string.collection_nav_label;
        } else if (id == R.id.nav_calendar) {
            tag = "calendar_fragment";
            titleRes = R.string.calendar_nav_label;
        }else if(id == R.id.nav_missing){
            tag = "missing_fragment";
            titleRes = R.string.missing_nav_label;
        }else if (id == R.id.nav_statistic) {
            tag = "statistic_fragment";
            titleRes = R.string.statistics_nav_label;
        } else if (id == R.id.nav_next) {
            tag = "next_out_fragment";
            titleRes = R.string.next_out_label;
        }else if (id == R.id.nav_settings) {
            tag = "settings_fragment";
            titleRes = R.string.settings_nav_label;
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
            startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
        } else if (id == R.id.nav_about) {
            UIUtility.showInfoDialog(this);
        }
        createAndInsertFragment(tag, titleRes);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void createAndInsertFragment(String tag, int string){
        if(tag == null || tag.equals("")){
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(tag);
        if(fragment == null){
            switch (tag) {
                case "collection_fragment":
                    fragment = new CollectionFragment();
                    break;
                case "statistic_fragment":
                    fragment = new StatisticFragment();
                    break;
                case "next_out_fragment":
                    fragment = new NextOutFragment();
                    Bundle args = new Bundle();
                    args.putBoolean(getString(R.string.next_key), true);
                    fragment.setArguments(args);
                    break;
                case "missing_fragment":
                    fragment = new NextOutFragment();
                    args = new Bundle();
                    args.putBoolean(getString(R.string.next_key), false);
                    fragment.setArguments(args);
                    break;
                case "settings_fragment":
                    fragment = new SettingsFragment();
                    break;
            }
        }

        fm.beginTransaction()
                .replace(R.id.main_container, fragment, tag)
                .commit();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle(string);
        }
    }
}
