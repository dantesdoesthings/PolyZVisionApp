package com.dantesdoesthings.polyzvision;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.ToggleButton;


public class AdvancedOptionsFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener{

    private ToggleButton spiralToggle;
    private Button spiralQuery, speedMem, speedRec, speedDef, swirlMem, swirlRec, swirlDef;
    private SeekBar speedSeekbar, swirlSeekbar;
    private Spinner methodSpinner;
    private RadioButton radioNewton, radioHalley, radioMRBF;
    private AdvancedOptionListener callback;


    public AdvancedOptionsFragment() {

    }

    public interface AdvancedOptionListener {
        void onAdvancedFragmentChange (View v);
        void onAdvancedFragmentChange (View v, int i);
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

        View rootView = inflater.inflate(R.layout.fragment_advanced_options, container, false);

        spiralToggle = (ToggleButton) rootView.findViewById(R.id.toggle_advanced_spiraling);
        speedSeekbar = (SeekBar) rootView.findViewById(R.id.slider_advanced_speed);
        swirlSeekbar = (SeekBar) rootView.findViewById(R.id.slider_advanced_swirl);
        radioNewton = (RadioButton) rootView.findViewById(R.id.advanced_radio_newton);
        radioHalley = (RadioButton) rootView.findViewById(R.id.advanced_radio_halley);
        radioMRBF = (RadioButton) rootView.findViewById(R.id.advanced_radio_mrbf);

        spiralToggle.setOnCheckedChangeListener(this);
        speedSeekbar.setOnSeekBarChangeListener(this);
        swirlSeekbar.setOnSeekBarChangeListener(this);
        radioNewton.setOnClickListener(this);
        radioHalley.setOnClickListener(this);
        radioMRBF.setOnClickListener(this);

        return rootView;

    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);

        try {
            callback = (AdvancedOptionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement AdvancedOptionListener");
        }

    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);

        try {
            callback = (AdvancedOptionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AdvancedOptionListener");
        }

    }

    /* The following are methods to handle events
     */
    public void onClick (View view) {
        callback.onAdvancedFragmentChange(view);
    }

    public void onProgressChanged (SeekBar s, int i, boolean b) {
        if (b) callback.onAdvancedFragmentChange(s, i);
    }

    public void onStartTrackingTouch (SeekBar s) {

    }

    public void onStopTrackingTouch (SeekBar s) {

    }

    public void onItemSelected (AdapterView<?> parent, View view, int pos, long id) {
        callback.onAdvancedFragmentChange(parent, pos);
    }

    public void onNothingSelected (AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        callback.onAdvancedFragmentChange(buttonView, isChecked?1:0);
    }
}
