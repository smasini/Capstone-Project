package smasini.it.traxer.app.activities;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.util.List;
import smasini.it.thetvdb.TheTVDB;
import smasini.it.thetvdb.support.Serie;
import smasini.it.thetvdb.task.callbacks.CallbackSerie;
import smasini.it.traxer.R;
import smasini.it.traxer.adapters.SearchSerieAdapter;
import smasini.it.traxer.utils.UIUtility;

public class SearchActivity extends AppCompatActivity {

    private SearchSerieAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        handleIntent(getIntent());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_result_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        View emptyView = findViewById(R.id.recyclerview_search_empty);
        adapter = new SearchSerieAdapter(this, emptyView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        menuItem.expandActionView();

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            UIUtility.showProgressDialog(this, "Loading...");
            TheTVDB theTVDB = new TheTVDB(this, getString(R.string.the_tvdb_api_key));
            theTVDB.findSeriesByName(query, new CallbackSerie() {
                @Override
                public void doAfterTask(List<Serie> series) {
                    UIUtility.hideProgressDialog();
                    adapter.swapData(series);
                }
            });
        }
    }
}
