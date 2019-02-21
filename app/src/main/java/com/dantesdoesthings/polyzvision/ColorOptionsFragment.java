package com.dantesdoesthings.polyzvision;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;

import pzv_math.ColorInfo;


public class ColorOptionsFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener{

    private SeekBar redSeekbar, blueSeekbar, greenSeekbar;
    private Spinner spinner;
    private ColorListener callback;

    public ColorOptionsFragment () {

    }

    public interface ColorListener {
        void onColorFragmentChange (int i, float[] c);
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

        View rootView = inflater.inflate(R.layout.fragment_color_options, container, false);

        redSeekbar = (SeekBar) rootView.findViewById(R.id.slider_red);
        blueSeekbar = (SeekBar) rootView.findViewById(R.id.slider_blue);
        greenSeekbar = (SeekBar) rootView.findViewById(R.id.slider_green);

        spinner = (Spinner) rootView.findViewById(R.id.advanced_method_spinner);

        redSeekbar.setOnSeekBarChangeListener(this);
        blueSeekbar.setOnSeekBarChangeListener(this);
        greenSeekbar.setOnSeekBarChangeListener(this);

        spinner.setOnItemSelectedListener(this);

        return rootView;

    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);

        try {
            callback = (ColorListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ColorListener");
        }

    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);

        try {
            callback = (ColorListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ColorListener");
        }

    }


    /* The following are methods to handle events
     */
    public void onClick (View view) {

    }

    public void onProgressChanged (SeekBar s, int i, boolean b) {
        //callback.onColorFragmentChange(s, i);
        if (b) {
            float temp[] = new float[3];
            temp[0] = redSeekbar.getProgress() / 255f;
            temp[1] = greenSeekbar.getProgress() / 255f;
            temp[2] = blueSeekbar.getProgress() / 255f;
            callback.onColorFragmentChange(spinner.getSelectedItemPosition(), temp);
        }
    }

    public void onStartTrackingTouch (SeekBar s) {

    }

    public void onStopTrackingTouch (SeekBar s) {

    }

    public void onItemSelected (AdapterView<?> parent, View view,int pos, long id) {
        float temp[];

        switch (pos) {
            case 0:
                temp= ColorInfo.background();
                break;
            case 1:
                temp= ColorInfo.failure();
                break;
            default:
                temp= ColorInfo.getColor(pos-2);
                break;
        }
        redSeekbar.setProgress(Math.round(temp[0]*255f));
        greenSeekbar.setProgress(Math.round(temp[1]*255f));
        blueSeekbar.setProgress(Math.round(temp[2]*255f));
    }

    public void onNothingSelected (AdapterView<?> parent) {

    }
}
