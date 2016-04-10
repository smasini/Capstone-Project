package smasini.it.traxer.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smasini.it.traxer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NextWatchDetailFragment extends Fragment {


    public NextWatchDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_watch_detail, container, false);
    }

}
