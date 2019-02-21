package com.dantesdoesthings.polyzvision;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class NavigationOptionsFragment extends Fragment implements View.OnClickListener{

    private Button memorizeButton, recallButton, defaultButton;
    private ImageButton leftButton, upButton, downButton, rightButton, zoomIn, zoomOut;
    private NavigationListener callback;

    public NavigationOptionsFragment() {

    }

    public interface NavigationListener {
        void onNavigationChange (View v);
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

        View rootView = inflater.inflate(R.layout.fragment_navigation, container, false);

        leftButton = (ImageButton) rootView.findViewById(R.id.nav_left_button);
        upButton = (ImageButton) rootView.findViewById(R.id.nav_up_button);
        downButton = (ImageButton) rootView.findViewById(R.id.nav_down_button);
        rightButton = (ImageButton) rootView.findViewById(R.id.nav_right_button);
        zoomIn = (ImageButton) rootView.findViewById(R.id.nav_zoom_in_button);
        zoomOut = (ImageButton) rootView.findViewById(R.id.nav_zoom_out_button);
        memorizeButton = (Button) rootView.findViewById(R.id.nav_memorize_button);
        recallButton = (Button) rootView.findViewById(R.id.nav_recall_button);
        defaultButton = (Button) rootView.findViewById(R.id.nav_default_button);

        leftButton.setOnClickListener(this);
        upButton.setOnClickListener(this);
        downButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
        zoomIn.setOnClickListener(this);
        zoomOut.setOnClickListener(this);
        memorizeButton.setOnClickListener(this);
        recallButton.setOnClickListener(this);
        defaultButton.setOnClickListener(this);

        return rootView;

    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);

        try {
            callback = (NavigationListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NavigationListener");
        }

    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);

        try {
            callback = (NavigationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NavigationListener");
        }

    }


    /* The following are methods to handle events
     */
    public void onClick (View view) {

        callback.onNavigationChange(view);

    }
}
