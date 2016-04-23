package smasini.it.traxer.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import smasini.it.traxer.sync.TraxerSyncAdapter;

/**
 * Created by Simone Masini on 11/04/2016.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TraxerSyncAdapter.startSyncPeriodically(this);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}