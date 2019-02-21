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


public class SharpnessOptionsFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private SeekBar smoothness, sharpness, dominance;
    private SharpnessListener callback;

    public SharpnessOptionsFragment() {

    }

    public interface SharpnessListener {
        void onSharpnessFragmentChange (View v, int i);
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

        View rootView = inflater.inflate(R.layout.fragment_sharpness_options, container, false);

        smoothness = (SeekBar) rootView.findViewById(R.id.slider_smoothness_sharpness);
        sharpness = (SeekBar) rootView.findViewById(R.id.slider_sharpness);
        dominance = (SeekBar) rootView.findViewById(R.id.slider_dominance_sharpness);

        smoothness.setOnSeekBarChangeListener(this);
        sharpness.setOnSeekBarChangeListener(this);
        dominance.setOnSeekBarChangeListener(this);

        return rootView;

    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);

        try {
            callback = (SharpnessListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement SharpnessListener");
        }

    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);

        try {
            callback = (SharpnessListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SharpnessListener");
        }

    }


    /* The following are methods to handle events
     */
    public void onClick (View view) {

    }

    public void onProgressChanged (SeekBar s, int i, boolean b) {
        if (b) callback.onSharpnessFragmentChange(s, i);
    }

    public void onStartTrackingTouch (SeekBar s) {

    }

    public void onStopTrackingTouch (SeekBar s) {

    }
}
