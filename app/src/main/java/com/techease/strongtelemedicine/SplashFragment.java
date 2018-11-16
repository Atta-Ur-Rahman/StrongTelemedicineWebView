package com.techease.strongtelemedicine;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {


    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                boolean boleanOnPause = Utilities.getSharedPreferences(getActivity()).getBoolean("onPause", false);
                if (boleanOnPause) {
                    Utilities.withOutBackStackConnectFragment(getActivity(), new MainFragment());

                }
            }
        };
        handler.postDelayed(runnable, 3000);


        return inflater.inflate(R.layout.fragment_splash, container, false);
    }


}
