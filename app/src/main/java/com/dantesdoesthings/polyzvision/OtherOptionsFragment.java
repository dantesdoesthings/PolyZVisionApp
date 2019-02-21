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
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.ToggleButton;


public class OtherOptionsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private ToggleButton showPointsToggle;
    private ImageButton optionMinus, optionPlus;
    private OptionListener callback;
    private RadioButton polygon, square, circle;


    public OtherOptionsFragment() {

    }

    public interface OptionListener {
        void onOptionFragmentChange (View v);
        void onOptionFragmentChange (View v, int i);
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

        View rootView = inflater.inflate(R.layout.fragment_options, container, false);

        showPointsToggle = (ToggleButton) rootView.findViewById(R.id.option_toggle_points);
        optionMinus = (ImageButton) rootView.findViewById(R.id.options_button_minus);
        optionPlus = (ImageButton) rootView.findViewById(R.id.options_button_plus);
        //shapeSpinner = (Spinner) rootView.findViewById(R.id.options_shape_spinner);
        polygon = (RadioButton) rootView.findViewById(R.id.option_radio_polygon);
        square = (RadioButton) rootView.findViewById(R.id.option_radio_square);
        circle = (RadioButton) rootView.findViewById(R.id.option_radio_circle);

        showPointsToggle.setOnCheckedChangeListener(this);
        optionMinus.setOnClickListener(this);
        optionPlus.setOnClickListener(this);
        //shapeSpinner.setOnItemSelectedListener(this);
        polygon.setOnClickListener(this);
        square.setOnClickListener(this);
        circle.setOnClickListener(this);

        return rootView;

    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);

        try {
            callback = (OptionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OptionListener");
        }

    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);

        try {
            callback = (OptionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OptionListener");
        }

    }

    /* The following are methods to handle events
     */
    @Override
    public void onClick (View view) {
        callback.onOptionFragmentChange(view);
    }

    @Override
    public void onItemSelected (AdapterView<?> parent, View view,int pos, long id) {
        callback.onOptionFragmentChange(parent,pos);
    }

    @Override
    public void onNothingSelected (AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        callback.onOptionFragmentChange(buttonView, isChecked?1:0);
    }
}
