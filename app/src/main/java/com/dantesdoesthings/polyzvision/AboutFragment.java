package com.dantesdoesthings.polyzvision;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;


public class AboutFragment extends Fragment {

    private AboutListener callback;

    public AboutFragment() {

    }

    public interface AboutListener {
        void onAboutFragmentChange(View v, int i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        return rootView;

    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);

        try {
            callback = (AboutListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement AboutListener");
        }

    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);

        try {
            callback = (AboutListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AboutListener");
        }

    }


    /* The following are methods to handle events
     */
}
