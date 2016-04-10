package smasini.it.traxer.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import smasini.it.traxer.R;
import smasini.it.traxer.app.fragments.CastFragment;
import smasini.it.traxer.app.fragments.InfoDetailFragment;
import smasini.it.traxer.app.fragments.NextWatchDetailFragment;
import smasini.it.traxer.app.fragments.SeasonsFragment;

/**
 * Created by Simone Masini on 03/04/2016.
 */
public class DetailFragmentPagerAdapter extends FragmentPagerAdapter {

    private String serieID;
    private Context context;

    public DetailFragmentPagerAdapter(FragmentManager fm, Context context, String serieID) {
        super(fm);
        this.context = context;
        this.serieID = serieID;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new NextWatchDetailFragment();
                break;
            case 1:
                fragment = new InfoDetailFragment();
                break;
            case 2:
                fragment = new SeasonsFragment();
                break;
            case 3:
                fragment = new CastFragment();
                break;
        }

        if(fragment!=null){
            Bundle args = new Bundle();
            args.putString(context.getString(R.string.serie_id_key), serieID);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int res = 0;
        switch (position){
            case 0:
                res = R.string.tab_1_title;
                break;
            case 1:
                res = R.string.tab_2_title;
                break;
            case 2:
                res = R.string.tab_3_title;
                break;
            case 3:
                res = R.string.tab_4_title;
                break;
        }
        return context.getString(res);
    }
}
